package com.email.writer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins(
                                "https://ai-email-writer-4rk4.onrender.com", // deployed frontend
                                "chrome-extension://nefgnkboedlacmpgbkgjoknjeigpppln",// extension
                                                 // nefgnkboedlacmpgbkgjoknjeigpppln
                                "https://mail.google.com",
                                "https://ai-email-writer-backend-production.up.railway.app"
                                )
                        .allowedMethods("GET", "POST", "OPTIONS")
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }
        };
    }
}
