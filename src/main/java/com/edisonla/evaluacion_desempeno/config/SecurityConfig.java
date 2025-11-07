// java
package com.edisonla.evaluacion_desempeno.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // desactivar para APIs REST (solo en dev o con token)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/**").permitAll() // Abierto para todos los endpoints /api
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults()); // mantiene basic auth para otras rutas

        return http.build();
    }
}
