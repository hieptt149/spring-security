package com.hieptt149.spring.security.demo.controller;

import com.hieptt149.spring.security.demo.model.Accounts;
import com.hieptt149.spring.security.demo.repository.AccountsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AccountController {

    private final AccountsRepository accountsRepository;

    @GetMapping("/myAccount")
    public Accounts getAccountDetails(@RequestParam long id) {
        return accountsRepository.findByCustomerId(id);
    }

}
