package com.nyxn.orders.infrastructure.config;

import com.nyxn.orders.application.port.in.ConfirmOrderUseCase;
import com.nyxn.orders.application.port.in.CreateOrderUseCase;
import com.nyxn.orders.application.port.out.NotificationPort;
import com.nyxn.orders.application.port.out.OrderEventPublisherPort;
import com.nyxn.orders.application.port.out.OrderRepositoryPort;
import com.nyxn.orders.application.service.ConfirmOrderService;
import com.nyxn.orders.application.service.CreateOrderService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {
    @Bean
    public CreateOrderUseCase createOrderUseCase(OrderRepositoryPort repositoryPort) {
        return new CreateOrderService(repositoryPort);
    }

    @Bean
    public ConfirmOrderUseCase confirmOrderUseCase(OrderRepositoryPort repositoryPort,
                                                   OrderEventPublisherPort publisherPort,
                                                   NotificationPort notificationPort) {
        return new ConfirmOrderService(repositoryPort, publisherPort, notificationPort);
    }
}
