package com.canifa.stylenest.utils;
import com.canifa.stylenest.exception.CommonException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;

@Component
@Slf4j
public class JwtUtil {
    private final String SECRET_KEY = "gdz+U1XlMH0Qnij+mb6ZzqquktTZ4SEQa4KRq6Whx3zye9XE4s4unlRYEoKaL1SS";

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        List<String> authorities = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        claims.put("authorities", authorities);
        return createToken(claims, userDetails.getUsername());
    }

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 giờ
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public Boolean validateToken(String token, String username) {
        return extractUsername(token).equals(username) && !isTokenExpired(token);
    }

    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    public List<SimpleGrantedAuthority> extractAuthorities(String token) {
        List<String> authorities = extractAllClaims(token).get("authorities", List.class);
        System.out.println(authorities);
        return authorities.stream().map(authority -> new SimpleGrantedAuthority((String) authority)).collect(Collectors.toList());
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractAllClaims(token).getExpiration();
    }

    private Claims extractAllClaims(String token) {
        try{
            return Jwts
                    .parserBuilder()
                    .setSigningKey(getSignInKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (SignatureException | MalformedJwtException ex) {
            log.error("Invalid JWT token");
            throw new CommonException("Invalid JWT token", HttpStatus.UNAUTHORIZED);
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
            throw new CommonException("Expired JWT token", HttpStatus.UNAUTHORIZED);
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
            throw new CommonException("Unsupported JWT token", HttpStatus.UNAUTHORIZED);
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty.");
            throw new CommonException("JWT claims string is empty", HttpStatus.UNAUTHORIZED);
        }
    }
}

