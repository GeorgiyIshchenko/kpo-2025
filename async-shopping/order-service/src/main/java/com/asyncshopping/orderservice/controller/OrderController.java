package com.asyncshopping.orderservice.controller;

import com.asyncshopping.orderservice.model.Order;
import com.asyncshopping.orderservice.service.OrderService;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private record CreateRequest(@NotNull Long userId,
                                 @NotNull @Min(1) BigDecimal total) {}

    private final OrderService orders;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Order create(@RequestBody CreateRequest req) {
        return orders.create(req.userId(), req.total());
    }

    @PatchMapping("/{id}/status/{status}")
    public Order update(@PathVariable Long id, @PathVariable String status) {
        return orders.updateStatus(id, status);
    }

    @GetMapping("/{id}")
    public Order get(@PathVariable Long id) {
        return orders.findById(id);
    }

    @GetMapping("/")
    public List<Order> getAll() {
        return orders.findAll();
    }

}
