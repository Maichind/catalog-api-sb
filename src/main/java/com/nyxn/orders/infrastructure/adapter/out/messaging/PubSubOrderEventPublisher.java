package com.nyxn.orders.infrastructure.adapter.out.messaging;

import com.nyxn.orders.application.port.out.OrderEventPublisherPort;
import com.nyxn.orders.domain.model.Order;
import com.google.cloud.pubsub.v1.Publisher;
import com.google.protobuf.ByteString;
import com.google.pubsub.v1.PubsubMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PubSubOrderEventPublisher implements OrderEventPublisherPort {
    private final Publisher publisher; // bean configurado apuntando al tópico "orders.confirmed"

    @Override
    public void publishOrderConfirmed(Order order) {
        String payload = """
                {"orderId":"%s","customerId":"%s","total":"%s"}
                """.formatted(order.getId(), order.getCustomerId(), order.total());

        PubsubMessage message = PubsubMessage.newBuilder()
                .setData(ByteString.copyFromUtf8(payload))
                .build();

        publisher.publish(message); // asíncrono; en prod: manejar ApiFuture + retry/backoff
    }
}
