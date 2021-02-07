package com.veqveq.onlinemarket.controllers;

import com.veqveq.onlinemarket.dto.JwtRequest;
import com.veqveq.onlinemarket.dto.JwtResponse;
import com.veqveq.onlinemarket.exceptions.MarketError;
import com.veqveq.onlinemarket.exceptions.UserAlreadyRegisteredException;
import com.veqveq.onlinemarket.services.UserService;
import com.veqveq.onlinemarket.utils.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final JwtTokenUtils jwtTokenUtils;
    private final AuthenticationManager authenticationManager;
    private final BCryptPasswordEncoder passwordEncoder;

    @PostMapping("/auth")
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest request) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(new MarketError(HttpStatus.UNAUTHORIZED.value(), "Incorrect username/password"), HttpStatus.UNAUTHORIZED);
        }
        UserDetails details = userService.loadUserByUsername(request.getUsername());
        String token = jwtTokenUtils.generateToken(details);
        return ResponseEntity.ok(new JwtResponse(token, request.getUsername()));
    }

    @PostMapping("/reg")
    public void createNewUser(@RequestBody JwtRequest request) {
        if (userService.findByUsername(request.getUsername()).isPresent()){
            throw new UserAlreadyRegisteredException("User by username " + request.getUsername() +  " is already registered");
        }
        request.setPassword(passwordEncoder.encode(request.getPassword()));
        userService.save(request);
    }
}
