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

    public SpringConfig(CustomAuthenticationManager customAuthenticationManager, 
                        @Value("${jwt.secret}") String jwtSecret) {
        this.customAuthenticationManager = customAuthenticationManager;
        this.jwtSecret = jwtSecret;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        JWTAuthenticationFilter authenticationFilter = new JWTAuthenticationFilter(customAuthenticationManager, jwtSecret);
        
        authenticationFilter.setFilterProcessesUrl("/api/auth/login");

        http
            .csrf(csrf -> csrf.disable())
            .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.sameOrigin()))
            .authorizeHttpRequests(request -> request
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/v1/users/username/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/v1/users").hasAnyRole("ADMIN", "USER")
                .requestMatchers("/api/songs/**").hasRole("ADMIN")
                .requestMatchers("/api/game/**").authenticated()
                .anyRequest().authenticated()
            )
            .addFilter(authenticationFilter)
            .addFilterAfter(new JWTAuthorizationFilter(jwtSecret), JWTAuthenticationFilter.class)
            .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }
}