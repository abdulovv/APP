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

                .route("auth-service-route", r -> r
                        .path("/api/auth/**")
                        .uri("http://localhost:8081"))

                .route("nutrition-service-route", r -> r
                        .path("/api/nutrition/**")
                        .uri("http://localhost:8082"))

                .route("diary-service-route", r -> r
                        .path("/api/diary/**")
                        .uri("http://localhost:8083"))
                .build();
    }
}