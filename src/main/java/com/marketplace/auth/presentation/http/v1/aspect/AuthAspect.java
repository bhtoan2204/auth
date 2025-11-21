package com.marketplace.auth.presentation.http.v1.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.marketplace.auth.application.auth.service.JwtAuthenticationServiceImpl;
import com.marketplace.auth.presentation.http.v1.annotation.RequireAuth;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Aspect
@Component
@RequiredArgsConstructor
public class AuthAspect {

    private final JwtAuthenticationServiceImpl jwtAuthenticationService;

    @Around("@annotation(requireAuth)")
    public Object authenticate(ProceedingJoinPoint joinPoint, RequireAuth requireAuth) throws Throwable {

        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = attrs.getRequest();

        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Missing or invalid authorization header");
        }

        String token = authHeader.substring(7);
        if (!jwtAuthenticationService.validateToken(token)) {
            throw new RuntimeException("Invalid token");
        }

        Integer userId = jwtAuthenticationService.extractUserId(token);
        if (userId == null) {
            throw new RuntimeException("Invalid token payload");
        }

        request.setAttribute("userId", userId);

        return joinPoint.proceed();
    }
}
