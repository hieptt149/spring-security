package com.hieptt149.spring.security.demo.controller;

import com.hieptt149.spring.security.demo.model.Cards;
import com.hieptt149.spring.security.demo.model.Customer;
import com.hieptt149.spring.security.demo.repository.CardsRepository;
import com.hieptt149.spring.security.demo.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class CardsController {

    private final CardsRepository cardsRepository;
    private final CustomerRepository customerRepository;

    @GetMapping("/myCards")
    public List<Cards> getCardDetails(@RequestParam String email) {
        Optional<Customer> optCustomer = customerRepository.findByEmail(email);
        return optCustomer.map(customer -> cardsRepository.findByCustomerId(customer.getId()))
                .orElse(null);
    }

}
