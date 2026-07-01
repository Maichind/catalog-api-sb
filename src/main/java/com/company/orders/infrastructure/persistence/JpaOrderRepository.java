package com.company.orders.infrastructure.persistence;

import com.company.orders.domain.Order;
import com.company.orders.domain.port.OrderRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

interface SpringDataOrderRepository extends JpaRepository<Order, String> {}

@Repository
public class JpaOrderRepository implements OrderRepository {
    private final SpringDataOrderRepository jpaRepo;

    JpaOrderRepository(SpringDataOrderRepository jpaRepo) {
        this.jpaRepo = jpaRepo;
    }

    @Override
    public Optional<Order> findById(String orderId) {
        return jpaRepo.findById(orderId); // parametrizado, sin concatenación de strings
    }

    @Override
    public void save(Order order) {
        jpaRepo.save(order);
    }
}
