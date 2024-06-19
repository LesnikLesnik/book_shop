package com.example.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableRabbit
@Configuration
public class RabbitMQConfig {

    //Deposit
    public static final String QUEUE_DEPOSIT = "js.deposit.notify";

    private static final String TOPIC_EXCHANGE_DEPOSIT = "js.deposit.notify.exchange";

    private static final String ROUTING_KEY_DEPOSIT = "js.key.deposit";

    @Bean
    public TopicExchange depositExchange() {
        return new TopicExchange(TOPIC_EXCHANGE_DEPOSIT);
    }

    @Bean
    public Queue queueDeposit() {
        return new Queue(QUEUE_DEPOSIT);
    }

    @Bean
    public Binding depositBinding() {
        return BindingBuilder
                .bind(queueDeposit())
                .to(depositExchange())
                .with(ROUTING_KEY_DEPOSIT);
    }

    //Buy book
    public static final String QUEUE_BOOK = "js.book.notify";

    private static final String TOPIC_EXCHANGE_BOOK = "js.book.notify.exchange";

    private static final String ROUTING_KEY_BOOK = "js.key.book";

    @Bean
    public TopicExchange bookExchange() {
        return new TopicExchange(TOPIC_EXCHANGE_BOOK);
    }

    @Bean
    public Queue queueBook() {
        return new Queue(QUEUE_BOOK);
    }

    @Bean
    public Binding bookBinding() {
        return BindingBuilder
                .bind(queueBook())
                .to(bookExchange())
                .with(ROUTING_KEY_BOOK);
    }


}


