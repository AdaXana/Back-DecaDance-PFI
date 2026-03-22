package com.decadance.Back_DecaDance_PFI.security.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.decadance.Back_DecaDance_PFI.dto.request.LoginRequestDTO;
import com.decadance.Back_DecaDance_PFI.security.CustomAuthenticationManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final CustomAuthenticationManager customAuthenticationManager;
    private final String jwtSecret;

    public JWTAuthenticationFilter(CustomAuthenticationManager customAuthenticationManager, String jwtSecret) {
        this.customAuthenticationManager = customAuthenticationManager;
        this.jwtSecret = jwtSecret;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            LoginRequestDTO credentials = new ObjectMapper().readValue(request.getInputStream(), LoginRequestDTO.class);
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    credentials.username(), 
                    credentials.password()
            );
            return customAuthenticationManager.authenticate(authentication);
        } catch (Exception e) {
            throw new RuntimeException("Error al leer las credenciales del login", e);
        }
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write("Error de autenticacion: " + failed.getMessage());
        response.getWriter().flush();
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        List<String> roles = authResult.getAuthorities().stream()
                .map(grantedAuthority -> grantedAuthority.getAuthority().replace("ROLE_", ""))
                .collect(Collectors.toList());
        String token = JWT.create()
                .withSubject(authResult.getName())
                .withClaim("roles", roles)
                .withExpiresAt(new Date(System.currentTimeMillis() + (12 * 60 * 60 * 1000))) 
                .sign(Algorithm.HMAC512(jwtSecret));
        response.addHeader("Authorization", "Bearer " + token);
    }
}