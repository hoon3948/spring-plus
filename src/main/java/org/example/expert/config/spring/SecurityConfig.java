package org.example.expert.config.spring;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.expert.config.JwtFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toStaticResources;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 1. csrf 비활성화 (JWT 방식이므로)
                .csrf(AbstractHttpConfigurer::disable)
                .headers(headers -> headers
                        .frameOptions(frame -> frame.disable()) // H2 콘솔 iframe 허용!
                )
                // 2. SessionManagement → STATELESS (JWT 방식이므로)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                // 3. URL별 권한 설정
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(toStaticResources().atCommonLocations()).permitAll()
//                        .requestMatchers(HttpMethod.GET, "/api/public/**").permitAll()
                        //    - /auth/** → 모두 허용
                        .requestMatchers(HttpMethod.POST,
                                "/api/auth/**"
                        ).permitAll()
                        // 헬스체크 허용
//                        .requestMatchers("/actuator/**").permitAll()
                        .requestMatchers("/h2-console/**").permitAll()
                        //    - /admin/** → ADMIN 권한만
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        //    - 나머지 → 인증 필요
                        .requestMatchers("/api/**").authenticated()
                        .anyRequest().authenticated()
                )
                // JwtFilter 등록
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                // 인증/인가 실패 처리
                .exceptionHandling(ex -> ex
                        // 인증 정보가 없는 요청 (토큰 없음) → 401
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            response.setContentType("application/json;charset=UTF-8");
                            response.getWriter().write(
                                    "{\"code\":\"UNAUTHORIZED\",\"message\":\"인증이 필요합니다.\",\"data\":null}"
                            );
                        })
                        // 인증은 됐지만 권한이 없는 요청 → 403
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                            response.setContentType("application/json;charset=UTF-8");
                            response.getWriter().write(
                                    "{\"code\":\"FORBIDDEN\",\"message\":\"접근 권한이 없습니다.\",\"data\":null}"
                            );
                        })
                );
        return http.build();
    }

    @Bean
    public FilterRegistrationBean<JwtFilter> jwtFilterRegistration(JwtFilter jwtFilter) {
        FilterRegistrationBean<JwtFilter> registration = new FilterRegistrationBean<>(jwtFilter);
        registration.setEnabled(false); // 서블릿 필터로 자동 등록 방지!
        return registration;
    }
}