package com.github.mingyu.fooddeliveryapi.aop;

import com.github.mingyu.fooddeliveryapi.security.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.nio.file.AccessDeniedException;

@Aspect
@Component
@RequiredArgsConstructor
public class UserAuthorizationAspect {

    private final JwtTokenProvider jwtTokenProvider;
    private final HttpServletRequest request;

    @Around("@annotation(com.github.mingyu.fooddeliveryapi.aop.CheckUserAuthorization) || " +
            "@within(com.github.mingyu.fooddeliveryapi.aop.CheckUserAuthorization)")
    public Object checkUserAuthorization(ProceedingJoinPoint joinPoint) throws Throwable {

        // 토큰에서 현재 사용자 id 추출
        String token = jwtTokenProvider.resolveToken(request);
        Long currentUserId = jwtTokenProvider.getUserId(token);

        // 메서드 인자에서 id 추출
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            if (arg instanceof Long userId) {
                if (!userId.equals(currentUserId)) {
                    throw new AccessDeniedException("본인만 접근 가능합니다.");
                }
            }
        }

        return joinPoint.proceed();
    }
}
