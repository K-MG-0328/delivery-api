package com.github.mingyu.fooddeliveryapi.domain.user.application.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
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
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final RedisTemplate<String, String> redisTemplate;

    @Value("${secret.key}")
    private String secretKey;

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

    // 토큰에서 유저 아이디 추출
    public Long getUserId(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.get("userId", Long.class);
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

    // 요청 헤더에서 토큰 추출
    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    /*
    * JWT 로그아웃 처리 방식
    * 1. 클라이언트에서 로그아웃 요청
    * 2. 서버에서 JWT를 Redis 블랙리스트에 추가
    * 3. 서버에서 요청이 올 때마다 블랙리스트 확인
    * 4. JWT 만료 후 Redis에서 자동 삭제
    * */

    // 토큰 무효화 (블랙 리스트 추가)
    public void invalidateToken(String token) {
        long expiration = getExpiration(token) - System.currentTimeMillis();
        redisTemplate.opsForValue().set(token, "logout", expiration, TimeUnit.MILLISECONDS);
    }

    // 토큰 만료시간 조회
    private long getExpiration(String token) {
        return Jwts.parser()
                .setSigningKey(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration()
                .getTime();
    }

    // 토큰 블랙리스트 확인
    public boolean isTokenBlacklisted(String token) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(token));
    }
}
