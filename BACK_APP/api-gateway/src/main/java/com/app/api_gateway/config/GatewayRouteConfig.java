package com.app.api_gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayRouteConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()

                .route("account-service-route", r -> r
                        .path(
                                "/api/auth/**",
                                "/api/countries/**",
                                "/api/users/**",
                                "/api/pages/**",
                                "/api/premium/**",
                                "/api/admin/users/**"
                        )
                        .uri("lb://account-service"))

                .route("product-service-route", r -> r
                        .path("/api/products/**")
                        .uri("lb://product-service"))

                .route("diary-service-route", r -> r
                        .path("/api/diary/**")
                        .uri("lb://diary-service"))
                .build();
    }
}
