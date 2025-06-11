package com.asyncshopping.paymentservice.repository;

import com.asyncshopping.paymentservice.model.InboxEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InboxRepository extends JpaRepository<InboxEvent, String> {}
