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
	private ICustomerRepository customerRepository; 
	
	@Before
	public void setUp() {
		controller = new CustomerController(customerRepository);
	}
	
	@Test
	public void canGetAllCustomers() {
		List<Customer> expectedCustomers = Arrays.asList(new Customer(), new Customer());
		Mockito.when(customerRepository.findAll()).thenReturn(expectedCustomers);
		
		Collection<Customer> allCustomers = controller.getAllCustomers();
		
		Assert.assertEquals(expectedCustomers, allCustomers);
	}
	
	@Test
	public void canCreateNewCustomer() {
		Customer newCustomerRequest = new Customer();
		Customer expectedCreatedCustomer = new Customer();
		Mockito.when(customerRepository.save(newCustomerRequest)).thenReturn(expectedCreatedCustomer);
		
		Customer actualCreatedCustomer = controller.createNewCustomer(newCustomerRequest);
		
		Assert.assertEquals(expectedCreatedCustomer, actualCreatedCustomer);
	}
	
	@Test
	public void canDeleteCustomer() {
		int customerId = 123;
		
		controller.deleteCustomer(customerId);
		
		Mockito.verify(customerRepository).delete(customerId);
	}
	
	@Test
	public void canUpdateCustomer() {
		int customerId = 123;
		Customer updateCustomerRequest = new Customer();
		Customer expectedUpdatedCustomer = new Customer();
		Mockito.when(customerRepository.save(updateCustomerRequest)).then(i -> {
			Customer passedCustomer = i.getArgument(0);
			Assert.assertEquals(customerId, passedCustomer.getId());
			
			return expectedUpdatedCustomer;
		});
		
		Customer actualUpdatedCustomer = controller.updateCustomer(customerId, updateCustomerRequest);
		
		Assert.assertEquals(expectedUpdatedCustomer, actualUpdatedCustomer);
	}
	
}
