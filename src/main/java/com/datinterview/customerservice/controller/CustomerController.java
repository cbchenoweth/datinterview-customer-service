package com.datinterview.customerservice.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.datinterview.customerservice.dataaccess.ICustomerRepository;
import com.datinterview.customerservice.model.Customer;

@RestController
public class CustomerController {
	
	private ICustomerRepository customerRepository;

	@Autowired
	public CustomerController(ICustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	@GetMapping("/customers")
	public Collection<Customer> getAllCustomers() {
		return customerRepository.getAllCustomers();
	}

	@PostMapping("/customers")
	public Customer createNewCustomer(@RequestBody Customer newCustomerRequest) {
		return customerRepository.createNewCustomer(newCustomerRequest);
	}
	
}
