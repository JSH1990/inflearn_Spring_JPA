package com.studyolle.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable()) // 필요 시 disable
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                        .requestMatchers("/", "/sign-up", "/check-email", "/check-email-token",
                                "/email-login", "/check-email-login", "/login-link").permitAll()
                        .requestMatchers(HttpMethod.GET, "/profile/*").permitAll()
                        .anyRequest().authenticated()
                )
                // ✅ 기본 로그인 페이지 활성화 (Spring Security 제공)
                .formLogin(form -> form
                        .loginPage("/")
                        .loginProcessingUrl("/")
                        .defaultSuccessUrl("/", true)
                        .permitAll()  // 누구나 접근 가능
                )
                // ✅ 기본 로그아웃 처리 (Security가 자동으로 제공)
                .logout(logout -> logout
                        .logoutSuccessUrl("/")  // 로그아웃 후 이동 경로
                        .permitAll()
                );

        return http.build();
    }
}
