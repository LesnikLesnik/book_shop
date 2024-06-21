package com.example.gateway.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtUtils {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration; // Время жизни токена в миллисекундах

    private Key key;


    private void initKey() {
        if (this.key == null) {
            this.key = Keys.hmacShaKeyFor(secret.getBytes());
        }
    }

    public Claims getClaims(String token) {
        initKey();
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    public boolean isExpired(String token) {
        return getClaims(token).getExpiration().before(new Date());
    }
}
