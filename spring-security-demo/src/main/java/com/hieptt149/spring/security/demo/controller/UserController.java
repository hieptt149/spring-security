package com.hieptt149.spring.security.demo.controller;

import com.hieptt149.spring.security.demo.constants.JwtConstants;
import com.hieptt149.spring.security.demo.model.Customer;
import com.hieptt149.spring.security.demo.model.LoginRequestDto;
import com.hieptt149.spring.security.demo.model.LoginResponseDto;
import com.hieptt149.spring.security.demo.repository.CustomerRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final Environment env;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody Customer customer) {
        try {
            String hashPwd = passwordEncoder.encode(customer.getPwd());
            customer.setPwd(hashPwd);
            customer.setCreateDt(new Date(System.currentTimeMillis()));
            Customer savedCustomer = customerRepository.save(customer);

            if (savedCustomer.getId() > 0) {
                return ResponseEntity.status(HttpStatus.CREATED).body("Given user details are successfully registered");
            } else {
                return ResponseEntity.badRequest().body("User registration failed");
            }
        } catch (Exception ex) {
            return ResponseEntity.internalServerError()
                    .body("An exception occurred: " + ex.getMessage());
        }
    }

    @RequestMapping("/user")
    public Customer getUserDetailsAfterLogin(Authentication authentication) {
        Optional<Customer> optionalCustomer = customerRepository.findByEmail(authentication.getName());
        return optionalCustomer.orElse(null);
    }

    @PostMapping("/apiLogin")
    public ResponseEntity<LoginResponseDto> apiLogin(@RequestBody LoginRequestDto loginRequest) {
        String jwt = "";
        Authentication authentication = UsernamePasswordAuthenticationToken.unauthenticated(loginRequest.username(), loginRequest.password());
        Authentication authenticationResponse = authenticationManager.authenticate(authentication);
        if (null != authenticationResponse && authenticationResponse.isAuthenticated()) {
            if (null != env) {
                String secret = env.getProperty(JwtConstants.JWT_SECRET_KEY, JwtConstants.JWT_SECRET_DEFAULT_VALUE);
                SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
                jwt = Jwts.builder()
                        .issuer("Eazy Bank")
                        .subject("Jwt Token")
                        .claim(JwtConstants.JWT_CLAIM_USERNAME, authenticationResponse.getName())
                        .claim(
                                JwtConstants.JWT_CLAIM_AUTHORITIES,
                                authenticationResponse.getAuthorities().stream()
                                        .map(GrantedAuthority::getAuthority)
                                        .collect(Collectors.joining(","))
                        )
                        .issuedAt(new java.util.Date())
                        .expiration(new java.util.Date(new java.util.Date().getTime() + 1000 * 60 * 60 * 8))
                        .signWith(secretKey)
                        .compact();
            }
        }
        return ResponseEntity.status(HttpStatus.OK)
                .header(JwtConstants.JWT_HEADER, jwt)
                .body(new LoginResponseDto(HttpStatus.OK.getReasonPhrase(), jwt));
    }
}
