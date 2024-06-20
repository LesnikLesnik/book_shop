package com.gateway.config;

import com.gateway.service.JwtUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@RefreshScope
public class CustomAuthenticationFilter implements GatewayFilter {

    @Autowired
    private RouterValidator validator;

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        if (validator.isSecured.test(request)) {
            // Проверка, если токен отсутствует в заголовках
            if (authMissing(request)) {
                return onError(exchange, HttpStatus.UNAUTHORIZED);
            }

            final String token = getAuthHeader(request);

            if (jwtUtils.isExpired(token)) {
                return onError(exchange, HttpStatus.UNAUTHORIZED);
            }

            // Добавляем заголовки с информацией из токена для последующих микросервисов
            enrichRequestWithHeaders(exchange, token);
        }
        return chain.filter(exchange);
    }

    private Mono<Void> onError(ServerWebExchange exchange, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        return response.setComplete();
    }

    private boolean authMissing(ServerHttpRequest request) {
        return !request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION);
    }

    private String getAuthHeader(ServerHttpRequest request) {
        List<String> authHeaders = request.getHeaders().getOrEmpty(HttpHeaders.AUTHORIZATION);
        return authHeaders.isEmpty() ? null : authHeaders.get(0);
    }

    private void enrichRequestWithHeaders(ServerWebExchange exchange, String token) {
        Claims claims = jwtUtils.getClaims(token);
        exchange.getRequest().mutate()
                .header("X-User-Id", claims.get("id").toString())
                .header("X-User-Role", claims.get("role").toString())
                .build();
    }
}
