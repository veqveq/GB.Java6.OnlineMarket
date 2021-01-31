package com.veqveq.onlinemarket.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class JwtTokenUtils {
    @Value("${jwt.secret}")
    private String secret;      //Установка ключа шифрования подписи

    public String generateToken(UserDetails userDetails) {                          //Метод генерации токена
        Map<String, Object> claims = new HashMap<>();                               //Создание мапы Claims - свойство токена
        List<String> rolesList = userDetails.getAuthorities().stream()              //Создание листа со списком ролей юзера по userDetails
                .map(GrantedAuthority::getAuthority).collect(Collectors.toList());  //Берет userDetails, вытаскивает список ролей, мапит их в GrantedAuthority
        claims.put("roles", rolesList);                                             //Добавление списка ролей в токен
        Date issueDate = new Date();                                                //Дата создание токена
        Date expiredDate = new Date(issueDate.getTime() + 20 * 60 * 1000);          //Установка времени жизни токена (в мс)
        String token = Jwts.builder()                                               //Обращение к сборщику токенов
                .setClaims(claims)                                                  //Запись свойств в токен
                .setIssuedAt(issueDate)                                             //Запись времени создания токена
                .setExpiration(expiredDate)                                         //Запись времени жизни в токен
                .setSubject(userDetails.getUsername())                              //Установка пользователя токена
                .signWith(SignatureAlgorithm.HS256, secret)                         //Установка алгоритма и ключа шифрования подписи
                .compact();                                                         //Завершение записи токена
        return token;
    }

    public Claims getBodyFromToken(String token) {                  //Метод получения тела токена
        Claims body = Jwts.parser()                                 //Обращение к парсеру токенов
                .setSigningKey(secret)                              //Установка ключа для дешифрации подписи
                .parseClaimsJws(token)                              //Парсинг токена
                .getBody();                                         //Получение тела токена
        return body;
    }

    private <T> T getClaimsFromToken(String token, Function<Claims, T> claimsResolver) {
        Claims claims = getBodyFromToken(token);
        return claimsResolver.apply(claims);

    }

    public List<String> getRoles(String token) {
        return getClaimsFromToken(
                token, (Function<Claims, List<String>>) claims ->
                        claims.get("roles", List.class));
    }

    public String getUsername(String token) {
        return getClaimsFromToken(token, Claims::getSubject);
    }
}
