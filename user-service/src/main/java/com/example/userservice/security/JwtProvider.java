package com.example.userservice.security;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

@Component
public class JwtProvider {

    private final SecretKey secretKey;
    // 3시간 만료
    private final long validityInMilliseconds = 3600000;

    public JwtProvider(Environment environment) {
        String secret = environment.getProperty("token.secret");
        byte[] keyBytes = secret.getBytes();
        this.secretKey = new SecretKeySpec(keyBytes, SignatureAlgorithm.HS512.getJcaName());
    }

    public String createToken(String subject) {
        return Jwts.builder()
                .setSubject(subject)
                .setExpiration(new java.util.Date(System.currentTimeMillis() + validityInMilliseconds))
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();
    }
}
