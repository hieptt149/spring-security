package com.hieptt149.spring.security.demo.controller;

import com.hieptt149.spring.security.demo.model.Accounts;
import com.hieptt149.spring.security.demo.model.Customer;
import com.hieptt149.spring.security.demo.repository.AccountsRepository;
import com.hieptt149.spring.security.demo.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class AccountController {

    private final AccountsRepository accountsRepository;
    private final CustomerRepository customerRepository;

    @GetMapping("/myAccount")
    public Accounts getAccountDetails(@RequestParam String email) {
        Optional<Customer> optCustomer = customerRepository.findByEmail(email);
        return optCustomer.map(customer -> accountsRepository.findByCustomerId(customer.getId()))
                .orElse(null);
    }

}
