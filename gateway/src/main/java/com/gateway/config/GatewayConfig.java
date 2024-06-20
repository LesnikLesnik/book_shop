package com.gateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableHystrix
public class GatewayConfig {

    @Autowired
    private AuthenticationFilter filter;

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("account-service", r -> r.path("/api/accounts/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://account-service"))
                .route("bill-service", r -> r.path("/api/bills/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://user-service"))
                .build();
    }
}
