package com.decadance.Back_DecaDance_PFI.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 1. Aplica a todas las rutas de la API
                .allowedOrigins("http://localhost:5173") // 2. Permite SOLO a tu React (Vite)
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS") // 3. Métodos permitidos
                .allowedHeaders("*") // 4. Permite cualquier cabecera (incluido el Token de JWT)
                .allowCredentials(true); // 5. Permite cookies o autenticación si fuera necesario
    }
}