package com.ggt.finalproject.jwt;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.ggt.finalproject.dto.SecurityExceptionDto;
import com.ggt.finalproject.exception.ErrorResponse;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String accessToken = jwtUtil.resolveToken(request, "Access");
        String refreshToken = jwtUtil.resolveToken(request, "Refresh");

        if (accessToken != null) {
            if (!jwtUtil.validateToken(accessToken)) {
                jwtExceptionHandler(response, "Access 토큰이 만료되었습니다", HttpStatus.BAD_REQUEST.value());
                return;
            }
            setAuthentication(jwtUtil.getloginIdFromToken(accessToken));
        } else if (refreshToken != null) {
            if (!jwtUtil.refreshTokenValidation(refreshToken)) {
                jwtExceptionHandler(response, "Refresh 토큰이 만료되었습니다.", HttpStatus.BAD_REQUEST.value());
                return;
            }
            setAuthentication(jwtUtil.getloginIdFromToken(refreshToken));
        }

        filterChain.doFilter(request, response);
    }

    public void setAuthentication(String loginId) {
        Authentication authentication = jwtUtil.createAuthentication(loginId);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    public void jwtExceptionHandler(HttpServletResponse response, String msg, int statusCode) {
        response.setStatus(statusCode);
        response.setContentType("application/json");
        try {
            String json = new ObjectMapper().writeValueAsString(new ErrorResponse(statusCode,msg));
            response.getWriter().write(json);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
