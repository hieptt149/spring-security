package com.hieptt149.spring.security.demo.controller;

import com.hieptt149.spring.security.demo.model.Customer;
import com.hieptt149.spring.security.demo.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody Customer customer) {
        try {
            String pwd = passwordEncoder.encode(customer.getPwd());
            customer.setPwd(pwd);
            Customer savedCustomer = customerRepository.save(customer);
            if (savedCustomer.getId() > 0) {
                return ResponseEntity.status(HttpStatus.CREATED).body("Given user details are successfully registered");
            } else {
                return ResponseEntity.badRequest().body("User registration failed");
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("An exception has occurred: " + e.getMessage());
        }
    }
}
