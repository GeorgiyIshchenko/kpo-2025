package com.asyncshopping.orderservice.repository;

import com.asyncshopping.orderservice.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {}