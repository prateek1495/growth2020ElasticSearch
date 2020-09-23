package com.elasticsearch.demo.controller;

import com.elasticsearch.demo.model.Customer;
import com.elasticsearch.demo.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("/saveCustomer")
    public int saveCustomer(@RequestBody List<Customer> customers) {
        customerService.saveAllCustomers(customers);
        return customers.size();
    }

    @GetMapping("/findAll")
    public Iterable<Customer> findAllCustomers() {
        return customerService.findAllCustomers();
    }

    @GetMapping("/findByFName/{firstName}/{age}")
    public List<Customer> findByFirstName(@PathVariable String firstName,@PathVariable int age) {
        return customerService.findByFirstname(firstName,age);
    }

}
