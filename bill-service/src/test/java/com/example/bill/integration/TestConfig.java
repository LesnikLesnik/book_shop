package com.example.bill.integration;

import com.example.bill.integration.client.account.AccountServiceClientStub;
import com.example.bill.integration.client.book.BookServiceClientStub;
import com.example.bill.сlient.account.AccountServiceClient;
import com.example.bill.сlient.book.BookServiceClient;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.testcontainers.containers.PostgreSQLContainer;

import javax.sql.DataSource;

import static org.mockito.Mockito.mock;

@TestConfiguration
public class TestConfig {

    @Bean
    @Primary
    public RabbitTemplate rabbitTemplate() {
        return mock(RabbitTemplate.class);
    }

    @Bean
    @Qualifier
    public AccountServiceClient accountServiceClient() {
        return new AccountServiceClientStub();
    }

    @Bean
    @Qualifier
    public BookServiceClient bookServiceClient() {
        return new BookServiceClientStub();
    }

    @Bean
    @Primary
    public DataSource dataSource(PostgreSQLContainer<?> postgresqlContainer) {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(postgresqlContainer.getDriverClassName());
        dataSource.setUrl(postgresqlContainer.getJdbcUrl());
        dataSource.setUsername(postgresqlContainer.getUsername());
        dataSource.setPassword(postgresqlContainer.getPassword());
        return dataSource;
    }
}
