package com.nyxn.orders.infrastructure.adapter.out.notification;

import com.nyxn.orders.application.port.out.NotificationPort;
import com.nyxn.orders.domain.model.Order;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class EmailNotificationAdapter implements NotificationPort {
    private final JavaMailSender mailSender;

    @Override
    public void notifyOrderConfirmed(Order order) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(resolveCustomerEmail(order.getCustomerId())); // en prod: lookup real vía Customer service
        message.setSubject("Tu pedido ha sido confirmado");
        message.setText("Hola, tu pedido " + order.getId() + " por un total de " + order.total() + " fue confirmado.");
        try {
            mailSender.send(message);
        } catch (Exception ex) {
            log.error("Fallo al enviar email de confirmación para orden {}", order.getId(), ex);
            // no se relanza: un fallo de notificación no debe revertir la confirmación de la orden
        }
    }

    private String resolveCustomerEmail(String customerId) {
        return customerId + "@example.com"; // placeholder — en prod: integración real con servicio de clientes
    }
}
