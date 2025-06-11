package com.asyncshopping.common.event;

import java.math.BigDecimal;
import java.time.Instant;

public interface Events {
    record OrderStatusChangedEvent(Long orderId, Long userId,
                                   String oldStatus, String newStatus,
                                   BigDecimal total,
                                   Instant timestamp) {}

    record PaymentStatusChangedEvent(Long orderId, Long userId,
                                     String result,   // SUCCESS / FAIL
                                     Instant timestamp) {}
}