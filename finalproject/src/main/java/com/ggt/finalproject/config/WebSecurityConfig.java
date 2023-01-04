package com.ggt.finalproject.config;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity // 스프링 Security 지원을 가능하게 함
@EnableGlobalMethodSecurity(securedEnabled = true) // @Secured 어노테이션 활성화
public class WebSecurityConfig {
//    private final JwtUtil jwtUtil;

    @Bean   // 비밀번호 암호화를 위해 빈 등록
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http.csrf().disable();
//
//        // 기본 설정인 Session 방식은 사용하지 않고 JWT 방식을 사용하기 위한 설정
//        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//
//        http.authorizeRequests().antMatchers("/api/user/**").permitAll()
//                .antMatchers("/").permitAll()
//                .antMatchers("/api/posts").permitAll()
//                .antMatchers("/api/posts/{postId}").permitAll()
//                .antMatchers("/api/signup").permitAll()
//                .antMatchers("/api/login").permitAll()
//                .anyRequest().authenticated()
//                // JWT 인증/인가를 사용하기 위한 설정
//                .and().addFilterBefore(new JwtAuthFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class);
//
//        return http.build();
//
//    }
}