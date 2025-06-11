package com.asyncshopping.paymentservice.service;

import com.asyncshopping.common.event.Events;
import com.asyncshopping.common.json.JacksonUtils;
import com.asyncshopping.paymentservice.model.*;
import com.asyncshopping.paymentservice.repository.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accounts;
    private final InboxRepository inbox;
    private final OutboxRepository outbox;

    @Transactional
    public Account createAccount(Long userId) {
        return accounts.save(Account.builder().userId(userId).balance(BigDecimal.ZERO).build());
    }

    @Transactional
    public Account topUp(Long userId, BigDecimal amount) {
        Account acc = accounts.findById(userId).orElseThrow();
        acc.setBalance(acc.getBalance().add(amount));
        return acc;
    }

    @Transactional(readOnly=true)
    public BigDecimal balance(Long userId) {
        return accounts.findById(userId).map(Account::getBalance).orElseThrow();
    }

    @Transactional
    public void handleOrderEvent(Events.OrderStatusChangedEvent evt, String eventId) {
        if (!"NEW".equals(evt.newStatus())) return;
        System.out.println("Account Service handleOrderEvent: " + evt.newStatus());
        if (inbox.existsById(eventId)) return;

        Account acc = accounts.findById(evt.userId()).orElseThrow();
        String result;
        if (acc.getBalance().compareTo(evt.total()) >= 0) {
            acc.setBalance(acc.getBalance().subtract(evt.total()));
            result = "SUCCESS";
        } else {
            result = "FAIL";
        }
        Events.PaymentStatusChangedEvent payEvt = new Events.PaymentStatusChangedEvent(
                evt.orderId(), evt.userId(), result, Instant.now());
        String json = null;
        try {
            json = JacksonUtils.mapper().writeValueAsString(payEvt);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        outbox.save(OutboxEvent.builder()
                .aggregateId(payEvt.orderId())
                .type("PAYMENT_STATUS_CHANGED")
                .payload(json)
                .sent(false)
                .build());
        inbox.save(new InboxEvent(eventId));
    }
}