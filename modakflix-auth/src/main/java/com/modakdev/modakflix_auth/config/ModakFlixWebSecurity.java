package com.modakdev.modakflix_auth.config;

import com.modakdev.modakflix_auth.ModakFlixAccessDeniedHandler;
import com.modakdev.modakflix_auth.ModakFlixAuthenticationEntryPoint;
import com.modakdev.modakflix_auth.filters.JwtAuthenticationFilter;
import com.modakdev.models.values.Role;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ModakFlixWebSecurity implements WebMvcConfigurer {

    private final ModakFlixAccessDeniedHandler accessDeniedHandler;
    private final ModakFlixAuthenticationEntryPoint authenticationEntryPoint;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public ModakFlixWebSecurity(ModakFlixAccessDeniedHandler accessDeniedHandler, ModakFlixAuthenticationEntryPoint authenticationEntryPoint, JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.accessDeniedHandler = accessDeniedHandler;
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll() // Allow OPTIONS for all paths
                        .requestMatchers("/admin/login").permitAll()
                        .requestMatchers(HttpMethod.GET, "/secure/signup-api-key-page").authenticated()
                        .requestMatchers("/admin/**").hasRole(Role.ADMIN.getValue())
                        .requestMatchers("/user/**").hasAnyRole(Role.USER.getValue(), Role.ADMIN.getValue())
                        .anyRequest().authenticated())
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .accessDeniedHandler(accessDeniedHandler)
                        .authenticationEntryPoint(authenticationEntryPoint)
                )
                .httpBasic()
                .and()
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .csrf(AbstractHttpConfigurer::disable)
                .cors(); // Enable CORS handling here

        return http.build();
    }
}
