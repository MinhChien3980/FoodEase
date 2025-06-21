package com.foodease.myapp.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    // this name must match the one you use in SecurityRequirement below
    private static final String BEARER_SCHEME = "bearerAuth";

    @Bean
    public OpenAPI apiDocs() {
        return new OpenAPI()
                // 1) Info block
                .info(new Info()
                        .title("FoodEase API")
                        .version("v1"))

                // 2) Declare the bearer‐JWT security scheme
                .components(new Components()
                        .addSecuritySchemes(BEARER_SCHEME,
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                                        .in(SecurityScheme.In.HEADER)
                                        .name("Authorization")
                        )
                )

                // 3) Apply this scheme to all operations by default
                .addSecurityItem(new SecurityRequirement()
                        .addList(BEARER_SCHEME)
                );
    }
}
