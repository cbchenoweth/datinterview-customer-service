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
	public List<Customer> findAll() {
		return new ArrayList<>(customerMap.values());
	}

	@Override
	public Customer save(Customer newCustomerRequest) {
		Customer dbCustomer = clone(newCustomerRequest);
		if(dbCustomer.getId() == 0) {
			dbCustomer.setId(nextId.getAndIncrement());
		}
		
		customerMap.put(dbCustomer.getId(), dbCustomer);
		
		return clone(dbCustomer);
	}

	@Override
	public void delete(int customerId) {
		customerMap.remove(customerId);
	}

	public Customer find(int customerId) {
		Customer found = customerMap.get(customerId);
		return (found != null) ? clone(found) : null;
	}
	
	public void resetDatabse() {
		customerMap.clear();
		nextId.set(1);
	}
	
	private Customer clone(Customer customer) {
		Customer clone = new Customer();
		clone.setId(customer.getId());
		clone.setName(customer.getName());
		clone.setCompany(customer.getCompany());
		clone.setAddress(customer.getAddress());
		clone.setCustomerType(customer.getCustomerType());
		clone.setPhoneNumber(customer.getPhoneNumber());
		clone.setEmailAddress(customer.getEmailAddress());
		clone.setTags(new ArrayList<>(customer.getTags()));
		return clone;
	}
}
