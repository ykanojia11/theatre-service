package com.movies.controller;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Value("${jwt.secret:theatre}")
    private String jwtSecret;

    @PostMapping("/token")
    public ResponseEntity<Map<String, String>> generateToken(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String role = request.get("role");
        // For demo, no password check. In production, validate credentials!
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", new String[]{"ROLE_" + role.toUpperCase()});
        String token = Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7))
                .signWith(SignatureAlgorithm.HS256, jwtSecret.getBytes())
                .compact();
        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        return ResponseEntity.ok(response);
    }
}
