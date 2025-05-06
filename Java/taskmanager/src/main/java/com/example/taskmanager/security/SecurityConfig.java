package com.example.taskmanager.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

/**
 * Configuración de seguridad para la aplicación Spring Boot.
 */
@Configuration
public class SecurityConfig {

    /**
     * Define el filtro de seguridad principal de la aplicación.
     * - Habilita CORS con configuración personalizada.
     * - Permite todas las solicitudes sin autenticación.
     *
     * @param http objeto {@link HttpSecurity} que se configura
     * @return la cadena de filtros de seguridad configurada
     * @throws Exception si ocurre algún error en la configuración
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable()) // CSRF deshabilitado para APIs REST
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // CORS habilitado
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll() // Permitir todas las rutas sin autenticación
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Stateless session
                .build();
    }

    /**
     * Configuración de CORS para permitir solicitudes desde cualquier origen
     * con métodos y cabeceras específicos.
     *
     * @return una fuente de configuración de CORS que aplica reglas a todos los endpoints
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOriginPattern("*"); // Permite cualquier origen
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS")); // Métodos permitidos
        config.setAllowedHeaders(List.of("Content-Type", "Authorization")); // Cabeceras permitidas
        config.setAllowCredentials(true); // Permite enviar cookies/autenticación

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config); // Aplica configuración a todos los endpoints
        return source;
    }
}
