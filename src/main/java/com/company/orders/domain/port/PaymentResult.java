package com.company.orders.domain.port;

public record PaymentResult(boolean approved, String transactionId, String failureReason) {
}
