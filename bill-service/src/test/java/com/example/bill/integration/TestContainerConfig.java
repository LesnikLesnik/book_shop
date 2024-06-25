package com.example.bill.integration;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.PostgreSQLContainer;

@TestConfiguration
public class TestContainerConfig {

    @Bean
    public PostgreSQLContainer<?> postgresqlContainer() {
        PostgreSQLContainer<?> postgresqlContainer = new PostgreSQLContainer<>("postgres:13.3")
                .withDatabaseName("test")
                .withUsername("test")
                .withPassword("test");
        postgresqlContainer.start();
        return postgresqlContainer;
    }
}
