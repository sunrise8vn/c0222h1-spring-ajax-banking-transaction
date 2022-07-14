package com.cg.controller.rest;


import com.cg.model.Customer;
import com.cg.service.customer.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/api/customers")
public class CustomerRestController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("/create")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> doCreate(@RequestBody Customer customer) {

        customer.setId(0L);
        customer.setBalance(BigDecimal.ZERO);

        Map<String, String> errors = new HashMap<>();

        Boolean existEmail = customerService.existsByEmail(customer.getEmail());

        Boolean existPhone = customerService.existsByPhone(customer.getPhone());

        if (existEmail) {
            errors.put("email", "Trùng email");
        }

        if (existPhone) {
            errors.put("phone", "Trùng Phone");
        }

        if (errors.isEmpty()) {
            try {
                customer = customerService.save(customer);

                return new ResponseEntity<>(customer.toCustomerDTO(), HttpStatus.CREATED);
            } catch (Exception e) {
                return new ResponseEntity<>("Server ko xử lý được", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<>(errors, HttpStatus.CONFLICT);
    }

}
