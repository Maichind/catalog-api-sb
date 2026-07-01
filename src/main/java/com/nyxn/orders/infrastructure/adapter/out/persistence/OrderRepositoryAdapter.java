package com.nyxn.orders.infrastructure.adapter.out.persistence;

import com.nyxn.orders.application.port.out.OrderRepositoryPort;
import com.nyxn.orders.domain.model.Money;
import com.nyxn.orders.domain.model.Order;
import com.nyxn.orders.domain.model.OrderLine;
import com.nyxn.orders.domain.model.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryAdapter implements OrderRepositoryPort {
    private final SpringDataOrderRepository jpaRepository;

    @Override
    public Order save(Order order) {
        jpaRepository.save(toEntity(order));
        return order; // el objeto de dominio ya refleja el estado que se acaba de persistir
    }

    @Override
    public Optional<Order> findById(String id) {
        return jpaRepository.findById(id).map(this::toDomain);
    }

    private OrderJpaEntity toEntity(Order order) {
        OrderJpaEntity entity = new OrderJpaEntity();
        entity.setId(order.getId());
        entity.setCustomerId(order.getCustomerId());
        entity.setStatus(order.getStatus().name());
        entity.setLines(order.getLines().stream()
                .map(line -> new OrderLineEmbeddable(
                        line.getProductId(), line.getQuantity(), line.getUnitPrice().getAmount()))
                .toList());
        return entity;
    }

    private Order toDomain(OrderJpaEntity entity) {
        List<OrderLine> lines = entity.getLines().stream()
                .map(l -> new OrderLine(l.getProductId(), l.getQuantity(), Money.of(l.getUnitPrice())))
                .toList();
        return Order.reconstruct(entity.getId(), entity.getCustomerId(),
                OrderStatus.valueOf(entity.getStatus()), lines);
    }
}
