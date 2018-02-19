package com.datinterview.customerservice.dataaccess;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Component;

import com.datinterview.customerservice.model.Customer;

@Component
public class InMemoryCustomerRepository implements ICustomerRepository {

	private static final ConcurrentHashMap<Integer, Customer> customerMap = new ConcurrentHashMap<>();
	private static final AtomicInteger nextId = new AtomicInteger(1);
	
	@Override
	public List<Customer> getAllCustomers() {
		return new ArrayList<>(customerMap.values());
	}

	@Override
	public Customer createNewCustomer(Customer newCustomerRequest) {
		Customer dbCustomer = new Customer();
		dbCustomer.setId(nextId.getAndIncrement());
		dbCustomer.setName(newCustomerRequest.getName());
		dbCustomer.setCompany(newCustomerRequest.getCompany());
		dbCustomer.setAddress(newCustomerRequest.getAddress());
		dbCustomer.setCustomerType(newCustomerRequest.getCustomerType());
		dbCustomer.setPhoneNumber(newCustomerRequest.getPhoneNumber());
		dbCustomer.setEmailAddress(newCustomerRequest.getEmailAddress());
		dbCustomer.setTags(new ArrayList<>(newCustomerRequest.getTags()));
		
		customerMap.put(dbCustomer.getId(), dbCustomer);
		
		return dbCustomer;
	}

	public void resetDatabse() {
		customerMap.clear();
		nextId.set(1);
	}

	@Override
	public void deleteCustomer(int customerId) {
		customerMap.remove(customerId);
	}

}
