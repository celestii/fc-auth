package com.example.fcauth.util;

import com.example.fcauth.dto.ValidateTokenDto;
import com.example.fcauth.model.*;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class JwtUtil {
    private static final String key = "fc-auth-jwt-token-key-2024-safe-long!";
    private static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8));
    private static final long expirationTimeInMills = 1000 * 60 * 60; // 1hr

    public static String createAppToken(App app) {
        Date now = new Date();
        Date expireAt = new Date(now.getTime() + expirationTimeInMills);

        Map<String, Object> claims = new HashMap<>();
        claims.put("type", "app");
        claims.put("roles", app.getAppRoles().stream().map(AppRole::getApi).map(Api::getId).collect(Collectors.toSet()));

        return Jwts.builder()
                .setSubject(String.valueOf(app.getId()))
                .claims(claims)
                .setIssuedAt(now)
                .setExpiration(expireAt)
                .signWith(SECRET_KEY)
                .compact();
    }

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

    public static String parseSubject(String token) {
        return parseToken(token).getSubject();
    }

    public static ResponseEntity<String> validateAppToken(ValidateTokenDto dto, Api api) {
        Claims claims;
        try {
            claims = parseToken(dto.getToken());
        } catch (Exception e) {
            return new ResponseEntity<>("invalid token", HttpStatus.UNAUTHORIZED);
        }

        Date now = new Date();
        if (claims.getExpiration().before(now)) {
            return new ResponseEntity<>("token expired.", HttpStatus.UNAUTHORIZED);
        }
        if (!StringUtils.equals("app", claims.get("type").toString())) {
            return new ResponseEntity<>("invalid token type", HttpStatus.UNAUTHORIZED);
        }

        String roles = claims.get("roles").toString();
        if (roles.contains(api.getId().toString())) {
            return new ResponseEntity<>("권한이 존재합니다.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("권한이 없습니다.", HttpStatus.FORBIDDEN);
        }
    }
}
