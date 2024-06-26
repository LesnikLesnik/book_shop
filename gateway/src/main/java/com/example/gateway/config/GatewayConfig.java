package com.example.gateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Autowired
    private CustomAuthenticationFilter customAuthenticationFilter;

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                // Публичные маршруты без фильтра аутентификации
                .route("auth-service-register", r -> r.path("/api/auth/register")
                        .uri("lb://account-service"))
                .route("auth-service-login", r -> r.path("/api/auth/login")
                        .uri("lb://account-service"))
                .route("books-service-getAllBooks", r -> r.path("/api/books")
                        .uri("lb://book-service"))
                .route("books-service-getBookById", r -> r.path("/api/books/{id}")
                        .uri("lb://book-service"))
                .route("authors-service-getAllAuthors", r -> r.path("/api/authors")
                        .uri("lb://book-service"))
                .route("authors-service-getAuthor", r -> r.path("/api/authors/{id}")
                        .uri("lb://book-service"))
                .route("authors-service-getBooksByAuthorId", r -> r.path("/api/authors/{id}/books")
                        .uri("lb://book-service"))

                // Применение фильтра аутентификации к защищенным маршрутам
                .route("account-service", r -> r.path("/account-service/api/accounts/**")
                        .filters(f -> f.filter(customAuthenticationFilter))
                        .uri("lb://account-service"))
                .route("bill-service", r -> r.path("/bill-service/api/bills/**")
                        .filters(f -> f.filter(customAuthenticationFilter))
                        .uri("lb://bill-service"))
                .route("books-service-secured", r -> r.path("/book-service/api/books/**")
                        .filters(f -> f.filter(customAuthenticationFilter))
                        .uri("lb://book-service"))
                .route("authors-service-secured", r -> r.path("/book-service/api/authors/**")
                        .filters(f -> f.filter(customAuthenticationFilter))
                        .uri("lb://book-service"))
                .build();
    }
}
