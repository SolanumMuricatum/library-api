package com.pepino.apigatewayapplication.config;

import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.cloud.gateway.route.RouteLocator;

@Configuration
public class GatewayConfig {
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("book-storage-service", r -> r.path("/book/getById/{id}")
                        .and().method("GET")
                        .uri("http://localhost:8080"))
                .route("book-storage-service", r -> r.path("/book/getByIsbn/{isbn}")
                        .and().method("GET")
                        .uri("http://localhost:8080"))
                .route("book-storage-service", r -> r.path("/book/getAll")
                        .and().method("GET")
                        .uri("http://localhost:8080"))
                .route("book-storage-service", r -> r.path("/book/save")
                        .and().method("POST")
                        .uri("http://localhost:8080"))
                .route("book-storage-service", r -> r.path("/book/update/{isbn}")
                        .and().method("PATCH")
                        .uri("http://localhost:8080"))
                .route("book-storage-service", r -> r.path("/book/delete/{isbn}")
                        .and().method("DELETE")
                        .uri("http://localhost:8080"))
                .build();
    }
}