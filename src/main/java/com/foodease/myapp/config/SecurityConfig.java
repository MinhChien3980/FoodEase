package com.foodease.myapp.config;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
public class SecurityConfig {

    // your custom JWT decoder (for everything except the docs)
    CustomJwtDecoder customJwtDecoder;

    // these are ALL the paths swagger-ui needs
    private static final String[] SWAGGER_WHITELIST = {
            "/v3/api-docs",       // <-- MUST match exactly
            "/v3/api-docs/**",    // swagger‐config & any groups
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/webjars/**"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 1) CORS
                .cors(Customizer.withDefaults())
                // 2) No CSRF in a pure REST API
                .csrf(AbstractHttpConfigurer::disable)
                // 3) Who can call what
                .authorizeHttpRequests(auth -> auth
                        // allow CORS pre-flights everywhere
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        // swagger → PUBLIC
                        .requestMatchers(SWAGGER_WHITELIST).permitAll()

                        // everything else → JWT protected
                        .anyRequest().authenticated()
                )
                // 4) JWT resource server
                .oauth2ResourceServer(oauth2 -> oauth2
                        .authenticationEntryPoint(new JwtAuthenEntryPoint())
                        .jwt(jwt -> jwt
                                .decoder(customJwtDecoder)
                                .jwtAuthenticationConverter(jwtAuthenticationConverter())
                        )
                );

        return http.build();
    }

    /**
     * We still need a CORS-configuration‐source bean so that
     * http.cors() can pick it up.
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration cfg = new CorsConfiguration();
        cfg.setAllowedOriginPatterns(List.of("*"));
        cfg.setAllowedMethods(List.of("GET","POST","PUT","DELETE","OPTIONS"));
        cfg.setAllowedHeaders(List.of("*"));
        cfg.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource src = new UrlBasedCorsConfigurationSource();
        src.registerCorsConfiguration("/**", cfg);
        return src;
    }

    /**
     * Don’t put swagger paths through the security filter chain at all.
     * (This is purely optional since we already .permitAll() above,
     *  but it can save a tiny bit of overhead.)
     */
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().requestMatchers(SWAGGER_WHITELIST);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    private JwtAuthenticationConverter jwtAuthenticationConverter() {
        var granted = new org.springframework.security.oauth2.server.resource.authentication
                .JwtGrantedAuthoritiesConverter();
        granted.setAuthorityPrefix("");
        var conv = new JwtAuthenticationConverter();
        conv.setJwtGrantedAuthoritiesConverter(granted);
        return conv;
    }
}