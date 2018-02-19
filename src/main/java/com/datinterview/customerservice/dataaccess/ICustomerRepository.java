package com.datinterview.customerservice.dataaccess;

import java.util.Collection;

import com.datinterview.customerservice.model.Customer;

public interface ICustomerRepository {

	Collection<Customer> getAllCustomers();

	Customer createNewCustomer(Customer newCustomerRequest);

	void deleteCustomer(int customerId);

}
