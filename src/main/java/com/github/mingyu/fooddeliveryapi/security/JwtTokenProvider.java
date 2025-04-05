package com.github.mingyu.fooddeliveryapi.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider {

    private final String secretKey;

    public JwtTokenProvider(@Value("${secret.key}") String secretKey) {
        this.secretKey = secretKey;
    }

    private final long validityInMilliseconds = 60 * 60 * 1000; //1시간
    private Key key;

    @PostConstruct
    protected void init() {
        key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    // JWT 생성
    public String createToken(String email, List<String> roles) {

        Claims claims = Jwts.claims()
                .subject(email)
                .add("roles", roles)
                .build();

        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // JWT에서 인증 정보 추출
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = User.withUsername(getUsername(token))
                .password("") // 패스워드는 빈 값으로 처리
                .authorities(getRoles(token).stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()))
                .build();

        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // 토큰에서 유저 이메일 추출
    public String getUsername(String token) {
        return Jwts.parser().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
    }

    // 토큰에서 권한(roles) 추출
    public List<String> getRoles(String token) {
        return (List<String>) Jwts.parser().setSigningKey(key).build().parseClaimsJws(token).getBody().get("roles");
    }

    // JWT 유효성 검증
    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(key).build().parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}
