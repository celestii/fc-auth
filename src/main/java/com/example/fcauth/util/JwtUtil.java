package com.example.fcauth.util;

import com.example.fcauth.model.Employee;
import com.example.fcauth.model.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;

public class JwtUtil {
    private static final String key = "fc-auth-jwt-token-key-2024-safe-long!";
    private static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8));

    public static String createToken(Employee employee) {
        Date now = new Date();
        Date expireAt = new Date(now.getTime() + 1000 * 60 * 60 * 24);

        Map<String, Object> claims = Map.of(
                "nickname", employee.getKakaoNickName(),
                "roles", employee.getRoles().stream().map(Role::getName).collect(Collectors.toSet())
        );

        return Jwts.builder()
                .subject(String.valueOf(employee.getId()))
                .claims(claims)
                .issuedAt(now)
                .expiration(expireAt)
                .signWith(SECRET_KEY)
                .compact();
    }

    public static Claims parseToken(String token) {
        return Jwts.parser()
                .verifyWith(SECRET_KEY)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
