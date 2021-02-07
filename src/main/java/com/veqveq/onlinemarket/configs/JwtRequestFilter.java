package com.veqveq.onlinemarket.configs;

import com.veqveq.onlinemarket.utils.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {
    private final JwtTokenUtils jwtTokenUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");          //Получение значения из хедера Authorization
        String username = null;                                             //Объявление поля username
        String jwt = null;                                                  //Объявление поля с токеном
        if (authHeader != null && authHeader.startsWith("Bearer ")) {       //Если в хедере Authorization есть знаение и оно начинается с Bearer
            jwt = authHeader.substring(7);                                  //То отпиливаем от него первые 7 символов (Bearer)
            username = jwtTokenUtils.getUsername(jwt);                      //Через утилиту вынимаем из токена имя пользователя
        }
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {   //Если имя непустое и в контектсе юзер не авторизован
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(    //Создаем токен для загрузки в контекст
                    username,                                                                       //Имя пользователя
                    null,                                                                  //Пароль
                    jwtTokenUtils.getRoles(jwt)                                                     //Список ролей (дергаем из токена утилитой)
                            .stream().map(SimpleGrantedAuthority::new)                              //Мапим к SGA
                            .collect(Collectors.toList()));                                         //Приводим к коллекции
            SecurityContextHolder.getContext().setAuthentication(token);                            //Загружаем созданный токен в контекст
        }
        filterChain.doFilter(request, response);
    }
}
