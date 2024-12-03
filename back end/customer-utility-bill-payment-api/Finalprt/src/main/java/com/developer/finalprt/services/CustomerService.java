package com.developer.finalprt.services;
import com.developer.finalprt.models.Customer;
import com.developer.finalprt.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public ResponseEntity<List<Customer>> getAllCustomers() {
        return ResponseEntity.ok(customerRepository.findAll());
    }

    public ResponseEntity<Customer> getCustomerById(String id) {
        return ResponseEntity.ok(customerRepository.findByCustomerId(id));
    }


}

