package com.example.userservice.security;

import com.example.userservice.domain.User;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.vo.RequestLogin;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import org.springframework.core.env.Environment;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;



@Slf4j
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final UserRepository userRepository;
    private final Environment environment;
    private final JwtProvider jwtProvider;

    public AuthenticationFilter(AuthenticationManager authenticationManager, UserRepository userRepository, Environment environment, JwtProvider jwtProvider) {
        super.setAuthenticationManager(authenticationManager);
        this.userRepository = userRepository;
        this.environment = environment;
        this.jwtProvider = jwtProvider;
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            RequestLogin creds = new ObjectMapper().readValue(request.getInputStream(), RequestLogin.class);
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(creds.getEmail(),
                    creds.getPassword(), new ArrayList<>());
            return getAuthenticationManager().authenticate(token);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {

        String username = authResult.getName();

        User user = userRepository.findByEmail(username).orElseThrow(
                () -> new UsernameNotFoundException("User not found with email: " + username)
        );

        log.debug("user id {}", user.getUserId());

        response.addHeader("token", jwtProvider.createToken(username));
        response.addHeader("userId", user.getUserId());
    }
}