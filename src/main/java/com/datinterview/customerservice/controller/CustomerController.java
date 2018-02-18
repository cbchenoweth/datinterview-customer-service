package com.datinterview.customerservice.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController {
	
	@GetMapping("/customers")
	public List<Integer> getAllCustomers() {
		return Arrays.asList(1, 2, 3);
	}
	
}
