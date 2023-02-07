package com.ggt.finalproject.config;


import com.ggt.finalproject.jwt.JwtAuthFilter;
import com.ggt.finalproject.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity // 스프링 Security 지원을 가능하게 함
@EnableGlobalMethodSecurity(securedEnabled = true) // @Secured 어노테이션 활성화 -> 유저 권한에따라 나누
public class WebSecurityConfig {
    private final JwtUtil jwtUtil;

    @Bean   // 비밀번호 암호화를 위해 빈 등록
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.cors();

        // 기본 설정인 Session 방식은 사용하지 않고 JWT 방식을 사용하기 위한 설정
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);


        http.authorizeRequests().antMatchers("/api/user/**").permitAll()     //로그인,회원가입 관련
                .antMatchers("/api/post/search").permitAll()               // 검색
                .antMatchers("/api/user/kakao/callback").permitAll()         // 소셜로그인
                .antMatchers(HttpMethod.GET,"/api/post/**").permitAll()        // 상세게시글 조회
                .antMatchers(HttpMethod.GET,"/api/post/postlist").permitAll()    // 전체게시글 조회
                .antMatchers("/api/post/imageUrlReturn").permitAll()
                .antMatchers("/api/post/likeTop6").permitAll()
//                .antMatchers(HttpMethod.GET,"/api/mypage/**").permitAll()
                .antMatchers("/api/mypage/**").permitAll()
                .antMatchers("/api/mypage/pwCheck").permitAll()
                .antMatchers("/api/user/naver/callback").permitAll()          // 소셜로그인
                .antMatchers("/api/post/getWorldcupImage/**").permitAll()       // 이상형 월드컵



                .antMatchers("/swagger-ui/**").permitAll() //스웨거 권한설정 X
                .antMatchers("/swagger-resources/**").permitAll() //스웨거 권한설정 X
                .antMatchers("/swagger-ui.html").permitAll() //스웨거 권한설정 X
                .antMatchers("/v2/api-docs").permitAll() //스웨거 권한설정 X
                .antMatchers("/v3/api-docs").permitAll() //스웨거 권한설정 X
                .antMatchers("/webjars/**").permitAll() //스웨거 권한설정 X

                .anyRequest().authenticated()
                // JWT 인증/인가를 사용하기 위한 설정
                .and().addFilterBefore(new JwtAuthFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class);

        return http.build();

    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource(){

        CorsConfiguration config = new CorsConfiguration();

        // 서버에서 응답하는 리소스에 접근 가능한 출처를 명시
        // Access-Control-Allow-Origin
        config.addAllowedOrigin("http://localhost:3000");
        config.addAllowedOrigin("https://www.ggultong.co.kr/");
        config.addAllowedOrigin("https://dev.d134m2xe6xydy2.amplifyapp.com");
        config.addAllowedOrigin("https://dev2.d134m2xe6xydy2.amplifyapp.com");
        config.addAllowedOrigin("https://dev2.d1g5ive02v0psv.amplifyapp.com");

        // 특정 헤더를 클라이언트 측에서 꺼내어 사용할 수 있게 지정
        // 만약 지정하지 않는다면, Authorization 헤더 내의 토큰 값을 사용할 수 없음
        // Access-Control-Expose-Headers
        config.addExposedHeader(JwtUtil.AUTHORIZATION_HEADER);

        // 본 요청에 허용할 HTTP method(예비 요청에 대한 응답 헤더에 추가됨)
        // Access-Control-Allow-Methods
        config.addAllowedMethod("*");

        // 본 요청에 허용할 HTTP header(예비 요청에 대한 응답 헤더에 추가됨)
        // Access-Control-Allow-Headers
        config.addAllowedHeader("*");

        // 기본적으로 브라우저에서 인증 관련 정보들을 요청 헤더에 담지 않음
        // 이 설정을 통해서 브라우저에서 인증 관련 정보들을 요청에 담을 수 있도록 해줍니다.
        // Access-Control-Allow-Credentials
        config.setAllowCredentials(true);

        // allowCredentials 를 true로 하였을 때,
        // allowedOrigin의 값이 * (즉, 모두 허용)이 설정될 수 없도록 검증합니다.
        config.validateAllowCredentials();

        // 어떤 경로에 이 설정을 적용할 지 명시합니다. (여기서는 전체 경로)
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return source;
    }

}
