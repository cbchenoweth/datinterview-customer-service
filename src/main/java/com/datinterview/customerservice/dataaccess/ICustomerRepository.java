package com.datinterview.customerservice.dataaccess;

import java.util.Collection;

import com.datinterview.customerservice.model.Customer;

public interface ICustomerRepository {

	Collection<Customer> findAll();

	Customer save(Customer newCustomerRequest);

	void delete(int customerId);
	
	Customer find(int customerId);

}
