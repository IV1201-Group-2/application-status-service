package com.example.applicationstatusservice.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.crypto.spec.SecretKeySpec;


/**
 * SecurityConfig contains configurations and security settings.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * @param http is the HttpSecurity that will be configured.
     * @return The configured filter chain.
     * @throws Exception thrown if an error occurs during the
     *                   SecurityFilterChain configuration.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable).authorizeHttpRequests(requests -> requests.
                        requestMatchers("/api/applicant", "/error", "/api/test").permitAll());

        return http.build();
    }

}