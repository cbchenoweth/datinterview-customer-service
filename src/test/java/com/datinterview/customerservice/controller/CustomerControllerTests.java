package com.datinterview.customerservice.controller;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import com.datinterview.customerservice.dataaccess.ICustomerRepository;
import com.datinterview.customerservice.model.Customer;

@RunWith(SpringRunner.class)
public class CustomerControllerTests {

	private CustomerController controller;
	
	@Mock
	private ICustomerRepository customerService; 
	
	@Before
	public void setUp() {
		controller = new CustomerController(customerService);
	}
	
	@Test
	public void canGetAllCustomers() {
		List<Customer> expectedCustomers = Arrays.asList(new Customer(), new Customer());
		Mockito.when(customerService.getAllCustomers()).thenReturn(expectedCustomers);
		
		Collection<Customer> allCustomers = controller.getAllCustomers();
		
		Assert.assertEquals(expectedCustomers, allCustomers);
	}
	
	@Test
	public void canCreateNewCustomer() {
		Customer newCustomerRequest = new Customer();
		Customer expectedCreatedCustomer = new Customer();
		Mockito.when(customerService.createNewCustomer(newCustomerRequest)).thenReturn(expectedCreatedCustomer);
		
		Customer actualCreatedCustomer = controller.createNewCustomer(newCustomerRequest);
		
		Assert.assertEquals(expectedCreatedCustomer, actualCreatedCustomer);
	}
	
}
