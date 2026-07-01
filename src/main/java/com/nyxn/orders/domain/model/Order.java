package com.nyxn.orders.domain.model;

import com.nyxn.orders.domain.exception.InvalidOrderStateException;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Order {
    private final String id;
    private final String customerId;
    private final List<OrderLine> lines = new ArrayList<>();
    private OrderStatus status;

    public Order(String id, String customerId) {
        this.id = id;
        this.customerId = customerId;
        this.status = OrderStatus.DRAFT;
    }

    public void addLine(OrderLine line) {
        if (status != OrderStatus.DRAFT) {
            throw new InvalidOrderStateException("No se pueden agregar líneas a una orden no editable");
        }
        lines.add(line);
    }

    public void confirm() {
        if (lines.isEmpty()) {
            throw new InvalidOrderStateException("No se puede confirmar una orden sin ítems");
        }
        if (status != OrderStatus.DRAFT) {
            throw new InvalidOrderStateException("Solo una orden DRAFT puede confirmarse");
        }
        this.status = OrderStatus.CONFIRMED;
    }

    public Money total() {
        return lines.stream()
                .map(OrderLine::subtotal)
                .reduce(Money.zero(), Money::add);
    }

    /**
     * Método de reconstitución para uso EXCLUSIVO de adaptadores de persistencia al rehidratar
     * una orden ya existente desde la base de datos. A diferencia del constructor público
     * (que siempre arranca en DRAFT) y de confirm() (que valida invariantes de transición),
     * este método reconstruye el objeto en el estado exacto en que fue persistido, sin
     * re-ejecutar las reglas de negocio de creación/confirmación.
     */
    public static Order reconstruct(String id, String customerId, OrderStatus status, List<OrderLine> lines) {
        Order order = new Order(id, customerId);
        order.lines.addAll(lines);
        order.status = status;
        return order;
    }

    // getLines() se define manualmente para devolver una copia inmutable;
    // Lombok detecta el método existente y no genera uno propio para este campo.
    public List<OrderLine> getLines() {
        return List.copyOf(lines);
    }
}
