package ru.develop.schedule.extern.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class JwtTokenUtils {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.lifetime}")
    private long jwtLifeTime;

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        List<String> rolesList = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        claims.put("roles", rolesList);

        Date issuedDate = new Date();
        Date expiredDate = new Date(issuedDate.getTime() + jwtLifeTime);
        Key key = Keys.hmacShaKeyFor(secret.getBytes());

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(issuedDate)
                .setExpiration(expiredDate)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String getUsername(String token) {
        return getAllClaimsFromToken(token).getSubject();
    }

    public List<String> getRoles(String token) {
        return getAllClaimsFromToken(token).get("roles", List.class);
    }

    private Claims getAllClaimsFromToken(String token) {
        Key key = Keys.hmacShaKeyFor(secret.getBytes());
        return Jwts.parser()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
