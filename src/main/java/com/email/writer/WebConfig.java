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
                                "chrome-extension://hhpgolmapneehaennhmcpnljkbffamkp",// extension
                                "https://mail.google.com",
                                "https://emailwriter.saadmirza7862004.workers.dev"
                        )
                        .allowedMethods("GET", "POST")
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }
        };
    }
}
