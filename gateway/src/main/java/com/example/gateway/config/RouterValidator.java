package com.example.gateway.config;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Predicate;

@Service
public class RouterValidator {

    public static final List<String> openEndpoints = List.of(
            "/api/auth/register",
            "/api/auth/login",
            "/api/books",  // Доступ к списку всех книг
            "/api/books/{id}", // Доступ к книге по id
            "/api/authors", // Доступ к списку всех авторов
            "/api/authors/{id}", // Доступ к автору по id
            "/api/authors/{id}/books" // Доступ к книгам автора по id
    );

    public Predicate<ServerHttpRequest> isSecured =
            request -> openEndpoints.stream()
                    .noneMatch(uri -> request.getURI().getPath().matches(convertToRegex(uri)));

    private String convertToRegex(String path) {
        return path.replace("{id}", "[^/]+"); // Замена {id} на regex для любого текста, кроме "/"
    }
}
