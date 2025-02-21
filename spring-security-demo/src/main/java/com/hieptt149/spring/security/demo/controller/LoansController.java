package com.hieptt149.spring.security.demo.controller;

import com.hieptt149.spring.security.demo.model.Customer;
import com.hieptt149.spring.security.demo.model.Loans;
import com.hieptt149.spring.security.demo.repository.CustomerRepository;
import com.hieptt149.spring.security.demo.repository.LoanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class LoansController {

    private final LoanRepository loanRepository;
    private final CustomerRepository customerRepository;

    @GetMapping("/myLoans")
    @PostAuthorize("hasRole('USER')")
    public List<Loans> getLoanDetails(@RequestParam String email) {
        Optional<Customer> optCustomer = customerRepository.findByEmail(email);
        return optCustomer.map(customer -> loanRepository.findByCustomerIdOrderByStartDtDesc(customer.getId()))
                .orElse(null);
    }

}
