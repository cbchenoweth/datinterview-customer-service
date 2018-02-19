package com.datinterview.customerservice.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
		return customerRepository.findAll();
	}
	
	@GetMapping("/customers/{customerId}")
	public Customer findById(@PathVariable int customerId) {
		return customerRepository.find(customerId);
	}

	@PostMapping("/customers")
	public Customer createNewCustomer(@RequestBody Customer newCustomerRequest) {
		return customerRepository.save(newCustomerRequest);
	}

	@DeleteMapping("/customers/{customerId}")
	public void deleteCustomer(@PathVariable int customerId) {
		customerRepository.delete(customerId);
	}

	@PutMapping("/customers/{customerId}")
	public Customer updateCustomer(@PathVariable int customerId, @RequestBody Customer updateCustomerRequest) {
		updateCustomerRequest.setId(customerId);
		return customerRepository.save(updateCustomerRequest);
	}

	@PostMapping("/customers/{primaryCustomerId}/subAccounts/{subCustomerId}")
	public void mergeCustomers(@PathVariable int primaryCustomerId, @PathVariable int subCustomerId) {
		Customer primaryCustomer = customerRepository.find(primaryCustomerId);
		Customer subCustomer = customerRepository.find(subCustomerId);
		
		if(primaryCustomer != null && subCustomer != null) {
			subCustomer.setParentCustomerId(primaryCustomerId);
			customerRepository.save(subCustomer);
		}
	}
	
}
