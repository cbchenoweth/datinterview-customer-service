package com.datinterview.customerservice.dataaccess;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import com.datinterview.customerservice.model.Customer;

@RunWith(SpringRunner.class)
public class InMemoryCustomerRepositoryTests {

	private InMemoryCustomerRepository repository;
	
	@Before
	public void setUp() {
		repository = new InMemoryCustomerRepository();
		repository.resetDatabse();
	}
	
	@Test
	public void canCreateNewCustomerRecord() {
		Customer newCustomerRequest1 = new Customer();
		newCustomerRequest1.setName("customer123");
		newCustomerRequest1.setCompany("companyAbc");
		newCustomerRequest1.setAddress("address");
		newCustomerRequest1.setCustomerType("customerType");
		newCustomerRequest1.setPhoneNumber("111-222-3333");
		newCustomerRequest1.setEmailAddress("email@example.com");
		newCustomerRequest1.setTags(Arrays.asList("tag1", "tag2", "tag3"));
		
		Customer newCustomerRequest2 = new Customer();
		newCustomerRequest2.setName("customer456");
		newCustomerRequest2.setCompany("companyXyz");
		newCustomerRequest2.setAddress("address2");
		newCustomerRequest2.setCustomerType("differentCustomerType");
		newCustomerRequest2.setPhoneNumber("123-456-7890");
		newCustomerRequest2.setEmailAddress("another@sample.com");
		newCustomerRequest2.setTags(Arrays.asList("tagA", "tagB", "tagC"));
		
		Customer newCustomer1 = repository.createNewCustomer(newCustomerRequest1);
		Customer newCustomer2 = repository.createNewCustomer(newCustomerRequest2);
		
		Assert.assertEquals(1, newCustomer1.getId());
		assertCustomerProperties(newCustomerRequest1, newCustomer1);
		
		Assert.assertEquals(2, newCustomer2.getId());
		assertCustomerProperties(newCustomerRequest2, newCustomer2);
	}
	
	@Test
	public void createNewCustomerDeepCopiesInformation() {
		Customer newCustomerRequest = new Customer();
		newCustomerRequest.setName("customer123");
		newCustomerRequest.setCompany("companyAbc");
		newCustomerRequest.setAddress("address");
		newCustomerRequest.setCustomerType("customerType");
		newCustomerRequest.setPhoneNumber("111-222-3333");
		newCustomerRequest.setEmailAddress("email@example.com");
		newCustomerRequest.setTags(new ArrayList<>(Arrays.asList("tag1", "tag2", "tag3")));
		
		Customer newCustomer = repository.createNewCustomer(newCustomerRequest);
		
		newCustomerRequest.setId(-999);
		newCustomerRequest.setName("name");
		newCustomerRequest.setCompany("comp");
		newCustomerRequest.setAddress("address");
		newCustomerRequest.setCustomerType("custType");
		newCustomerRequest.setPhoneNumber("newPhone");
		newCustomerRequest.setEmailAddress("diffEmail");
		newCustomerRequest.getTags().add("anotherTag");
		
		Assert.assertEquals(1, newCustomer.getId());
		Assert.assertEquals("customer123", newCustomer.getName());
		Assert.assertEquals("companyAbc", newCustomer.getCompany());
		Assert.assertEquals("address", newCustomer.getAddress());
		Assert.assertEquals("customerType", newCustomer.getCustomerType());
		Assert.assertEquals("111-222-3333", newCustomer.getPhoneNumber());
		Assert.assertEquals("email@example.com", newCustomer.getEmailAddress());
		Assert.assertEquals(Arrays.asList("tag1", "tag2", "tag3"), newCustomer.getTags());
	}
	
	@Test
	public void canGetAllCustomersWhenRepoIsEmpty() {
		List<Customer> allCustomers = repository.getAllCustomers();
		Assert.assertEquals(0, allCustomers.size());
	}
	
	@Test
	public void canGetAllCustomersWhenThereAreSomeInTheRepository() {
		Customer customer1 = repository.createNewCustomer(new Customer());
		Customer customer2 = repository.createNewCustomer(new Customer());
		
		List<Customer> allCustomers = repository.getAllCustomers();
		
		Assert.assertEquals(Arrays.asList(customer1, customer2), allCustomers);
	}

	private void assertCustomerProperties(Customer expected, Customer actual) {
		Assert.assertEquals(expected.getName(), actual.getName());
		Assert.assertEquals(expected.getCompany(), actual.getCompany());
		Assert.assertEquals(expected.getAddress(), actual.getAddress());
		Assert.assertEquals(expected.getCustomerType(), actual.getCustomerType());
		Assert.assertEquals(expected.getPhoneNumber(), actual.getPhoneNumber());
		Assert.assertEquals(expected.getEmailAddress(), actual.getEmailAddress());
		Assert.assertEquals(expected.getTags(), actual.getTags());
	}
}
