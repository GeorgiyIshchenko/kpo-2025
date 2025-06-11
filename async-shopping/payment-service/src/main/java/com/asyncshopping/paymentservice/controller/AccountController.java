package com.asyncshopping.paymentservice.controller;

import com.asyncshopping.paymentservice.model.Account;
import com.asyncshopping.paymentservice.service.AccountService;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/accounts")
public class AccountController {

    private record CreateReq(@NotNull Long userId) {}
    private record TopUpReq(@NotNull @Min(1) BigDecimal amount) {}

    private final AccountService accounts;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Account create(@RequestBody CreateReq r) {
        return accounts.createAccount(r.userId());
    }

    @PostMapping("/{id}/top-up")
    public Account topUp(@PathVariable Long id, @RequestBody TopUpReq r) {
        return accounts.topUp(id, r.amount());
    }

    @GetMapping("/{id}/balance")
    public Map<String, BigDecimal> balance(@PathVariable Long id) {
        return Map.of("balance", accounts.balance(id));
    }
}
