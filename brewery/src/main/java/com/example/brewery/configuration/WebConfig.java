package com.example.brewery.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuration class to set up CORS (Cross-Origin Resource Sharing) mappings.
 * This allows the application to handle cross-origin requests from specified origins.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * Configure CORS settings for the application.
     * This method sets up the allowed origins, HTTP methods, and other settings for cross-origin requests.
     *
     * @param registry the CorsRegistry to be configured
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:4200")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "HEAD")
                .allowCredentials(true);
    }
}
