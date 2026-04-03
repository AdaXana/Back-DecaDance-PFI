package com.decadance.Back_DecaDance_PFI.security;

import com.decadance.Back_DecaDance_PFI.security.filter.JWTAuthenticationFilter;
import com.decadance.Back_DecaDance_PFI.security.filter.JWTAuthorizationFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SpringConfig {

    private final CustomAuthenticationManager customAuthenticationManager;
    private final String jwtSecret;

    public SpringConfig(CustomAuthenticationManager customAuthenticationManager, @Value("${jwt.secret}") String jwtSecret) {
        this.customAuthenticationManager = customAuthenticationManager;
        this.jwtSecret = jwtSecret;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        JWTAuthenticationFilter authenticationFilter = new JWTAuthenticationFilter(customAuthenticationManager, jwtSecret);
        
        authenticationFilter.setFilterProcessesUrl("/api/v1/auth/login");

        http
            .csrf(csrf -> csrf.disable())
            .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.sameOrigin()))
            .authorizeHttpRequests(request -> request
                .requestMatchers("/api/v1/auth/**").permitAll()
                .requestMatchers("/error").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/v1/songs/active").permitAll()
                .requestMatchers("/api/v1/songs/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/v1/users/username/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/v1/users").hasAnyRole("ADMIN", "USER")
                .requestMatchers("/api/v1/games/**").authenticated()
                .requestMatchers(HttpMethod.GET, "/api/v1/genres").permitAll()
                .anyRequest().authenticated()
            )
            .addFilter(authenticationFilter)
            .addFilterAfter(new JWTAuthorizationFilter(jwtSecret), JWTAuthenticationFilter.class)
            .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }
}