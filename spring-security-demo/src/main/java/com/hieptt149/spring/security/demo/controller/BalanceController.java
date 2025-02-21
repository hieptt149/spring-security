package com.hieptt149.spring.security.demo.controller;

import com.hieptt149.spring.security.demo.model.AccountTransactions;
import com.hieptt149.spring.security.demo.model.Customer;
import com.hieptt149.spring.security.demo.repository.AccountTransactionsRepository;
import com.hieptt149.spring.security.demo.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class BalanceController {

    private final AccountTransactionsRepository accountTransactionsRepository;
    private final CustomerRepository customerRepository;

    @GetMapping("/myBalance")
    public List<AccountTransactions> getBalanceDetails(@RequestParam String email) {
        Optional<Customer> optCustomer = customerRepository.findByEmail(email);
        return optCustomer.map(customer -> accountTransactionsRepository.findByCustomerIdOrderByTransactionDtDesc(customer.getId()))
                .orElse(null);
    }
}
