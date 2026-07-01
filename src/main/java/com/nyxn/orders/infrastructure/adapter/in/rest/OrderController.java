package com.nyxn.orders.infrastructure.adapter.in.rest;

import com.nyxn.orders.application.port.in.ConfirmOrderUseCase;
import com.nyxn.orders.application.port.in.CreateOrderUseCase;
import com.nyxn.orders.domain.model.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final CreateOrderUseCase createOrderUseCase;
    private final ConfirmOrderUseCase confirmOrderUseCase;

    @PostMapping
    public ResponseEntity<OrderResponse> create(@RequestBody CreateOrderRequest request) {
        Order order = createOrderUseCase.create(request.customerId());
        return ResponseEntity.status(HttpStatus.CREATED).body(OrderResponse.from(order));
    }

    @PostMapping("/{id}/confirm")
    public ResponseEntity<Void> confirm(@PathVariable String id) {
        confirmOrderUseCase.confirm(id);
        return ResponseEntity.accepted().build();
    }
}
