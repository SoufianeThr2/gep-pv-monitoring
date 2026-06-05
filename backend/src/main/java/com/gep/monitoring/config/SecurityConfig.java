package com.gep.monitoring.config; // Vérifie juste que ce "package" correspond bien au tien

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // 1. Désactiver la protection CSRF (qui bloque souvent les requêtes POST/PUT depuis React)
                .csrf(csrf -> csrf.disable())

                // 2. Activer les règles CORS (définies juste en dessous)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))

                // 3. Autoriser l'accès à tes routes API
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/**").permitAll() // On laisse le Frontend accéder à toutes les API
                        .anyRequest().permitAll()
                );

        return http.build();
    }

    // Configuration officielle pour autoriser le Frontend (React sur le port 5173 ou 3000) à discuter avec le Backend
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*")); // Autorise toutes les origines
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}