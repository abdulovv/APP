package com.app.api_gateway.config;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class SessionUserFilter implements GlobalFilter, Ordered {
    private static final List<String> PUBLIC_PATHS = List.of(
            "/api/auth/sign-in",
            "/api/auth/sign-up",
            "/api/auth/sign-out",
            "/api/countries"
    );

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        if (exchange.getRequest().getMethod() == HttpMethod.OPTIONS) {
            return chain.filter(exchange);
        }
        String path = exchange.getRequest().getPath().value();
        boolean isPublic = PUBLIC_PATHS.stream().anyMatch(path::startsWith);

        if (isPublic) {
            return chain.filter(exchange.mutate().request(
                    exchange.getRequest()
                            .mutate()
                            .headers(headers -> headers.remove("X-User-Id"))
                            .headers(headers -> headers.remove("X-User-Role"))
                            .build()
            ).build());
        }

        return exchange.getSession().flatMap(session -> {
            Long userId = session.getAttribute("userId");
            String role = session.getAttribute("role");
            if (userId == null) {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            ServerHttpRequest newRequest = exchange.getRequest()   // берём оригинал
                    .mutate()                                      // хочу копию с изменениями
                    .headers(headers -> {
                        headers.remove("X-User-Id");
                        headers.set("X-User-Id", userId.toString());
                    })
                    .headers( headers -> {
                        headers.remove("X-User-Role");
                        headers.set("X-User-Role", role);
                    })
                    .build();

            return chain.filter(exchange.mutate().request(newRequest).build());
        });
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
