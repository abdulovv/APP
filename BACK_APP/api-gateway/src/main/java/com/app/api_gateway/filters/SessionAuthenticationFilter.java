package com.app.api_gateway.filters;

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
public class SessionAuthenticationFilter implements GlobalFilter, Ordered {
    public static final String USER_ID_HEADER = "X-User-Id";
    public static final String USER_ROLE_HEADER = "X-User-Role";

    private static final String SESSION_USER_ID = "userId";
    private static final String SESSION_ROLE = "role";

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
            ServerHttpRequest withoutUserHeaders = exchange.getRequest().mutate()
                    .headers(headers -> {
                        headers.remove(USER_ID_HEADER);
                        headers.remove(USER_ROLE_HEADER);
                    })
                    .build();
            return chain.filter(exchange.mutate().request(withoutUserHeaders).build());
        }

        return exchange.getSession().flatMap(session -> {
            Long userId = session.getAttribute(SESSION_USER_ID);
            String role = session.getAttribute(SESSION_ROLE);
            if (userId == null) {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            // берём исходный запрос и делаем копию с нашими заголовками
            ServerHttpRequest withUserHeaders = exchange.getRequest().mutate()
                    .headers(headers -> {
                        headers.set(USER_ID_HEADER, userId.toString());
                        headers.set(USER_ROLE_HEADER, role);
                    })
                    .build();
            return chain.filter(exchange.mutate().request(withUserHeaders).build());
        });
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
