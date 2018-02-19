package com.datinterview.customerservice.controller;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
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
	public void canFindCustomersById() {
		int customerId = 123;
		Customer expectedCustomer = new Customer();
		Mockito.when(customerRepository.find(customerId)).thenReturn(expectedCustomer);
		
		Customer customer = controller.findById(customerId);
		
		Assert.assertEquals(expectedCustomer, customer);
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
	
	@Test
	public void canMergeCustomers() {
		int primaryCustomerId = 123;
		int subCustomerId = 234;
		
		Customer primaryCustomerRecord = new Customer();
		Mockito.when(customerRepository.find(primaryCustomerId)).thenReturn(primaryCustomerRecord);
		
		Customer subCustomerRecord = new Customer();
		Mockito.when(customerRepository.find(subCustomerId)).thenReturn(subCustomerRecord);
		
		Mockito.when(customerRepository.save(subCustomerRecord)).then(i -> {
			Assert.assertEquals(primaryCustomerId, subCustomerRecord.getParentCustomerId());
			return new Customer();
		});
		
		controller.mergeCustomers(primaryCustomerId, subCustomerId);
		
		Mockito.verify(customerRepository).save(subCustomerRecord);
	}
	
	@Test
	public void mergeCustomersHandlesInvalidPrimaryAccountId() {
		int primaryCustomerId = 123;
		int subCustomerId = 234;
		
		Mockito.when(customerRepository.find(subCustomerId)).thenReturn(new Customer());
		
		controller.mergeCustomers(primaryCustomerId, subCustomerId);
		
		Mockito.verify(customerRepository, Mockito.never()).save(ArgumentMatchers.any());
	}
	
	@Test
	public void mergeCustomersHandlesInvalidSubAccountId() {
		int primaryCustomerId = 123;
		int subCustomerId = 234;
		
		Mockito.when(customerRepository.find(primaryCustomerId)).thenReturn(new Customer());
		
		controller.mergeCustomers(primaryCustomerId, subCustomerId);
		
		Mockito.verify(customerRepository, Mockito.never()).save(ArgumentMatchers.any());
	}
	
}
