package com.elasticsearch.demo.service;

import com.elasticsearch.demo.model.Customer;

import java.util.List;

public interface CustomerService {

    void saveAllCustomers(List<Customer> customers);

    Iterable<Customer> findAllCustomers();

    List<Customer> findByFirstname(String firstName,int age);

    Long getCustomerByAge(Integer age);
}
