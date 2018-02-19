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
	public void canSaveNewCustomers() {
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
		
		Customer newCustomer1 = repository.save(newCustomerRequest1);
		Customer newCustomer2 = repository.save(newCustomerRequest2);
		
		newCustomerRequest1.setId(1); // expect id value
		assertCustomerProperties(newCustomerRequest1, newCustomer1);
		
		newCustomerRequest2.setId(2); // expect id value
		assertCustomerProperties(newCustomerRequest2, newCustomer2);
	}
	
	@Test
	public void canFindCustomersById() {
		Customer newCustomerRequest1 = new Customer();
		newCustomerRequest1.setName("customer1");
		
		Customer newCustomerRequest2 = new Customer();
		newCustomerRequest2.setName("customer2");
		
		Customer newCustomer1 = repository.save(newCustomerRequest1);
		Customer newCustomer2 = repository.save(newCustomerRequest2);
		
		Customer foundCustomer1 = repository.find(newCustomer1.getId());
		assertCustomerProperties(newCustomer1, foundCustomer1);
		
		Customer foundCustomer2 = repository.find(newCustomer2.getId());
		assertCustomerProperties(newCustomer2, foundCustomer2);
	}
	
	@Test
	public void findCustomersByIdReturnsNullIfNoUserFound() {
		repository.save(new Customer());
		
		Assert.assertNull(repository.find(123));
		Assert.assertNull(repository.find(444));
	}
	
	@Test
	public void canUpdateCustomers() {
		Customer newCustomerRequest = new Customer();
		newCustomerRequest.setName("customer1");
		Customer newCustomer = repository.save(newCustomerRequest);
		
		newCustomer.setName("customer2");
		newCustomer.setCompany("companyAdded");
		Customer updatedCustomer = repository.save(newCustomer);
		
		assertCustomerProperties(newCustomer, updatedCustomer);
		
		Customer customerFoundAgainAfterUpdate = repository.find(newCustomer.getId());
		assertCustomerProperties(newCustomer, customerFoundAgainAfterUpdate);
	}
	
	@Test
	public void databaseRecordsAreDeepCopied() {
		// these are the values that should always be in the database.
		Customer expectedStateInTheDatabase = new Customer();
		expectedStateInTheDatabase.setId(1);
		expectedStateInTheDatabase.setName("customer123");
		expectedStateInTheDatabase.setCompany("companyAbc");
		expectedStateInTheDatabase.setAddress("address");
		expectedStateInTheDatabase.setCustomerType("customerType");
		expectedStateInTheDatabase.setPhoneNumber("111-222-3333");
		expectedStateInTheDatabase.setEmailAddress("email@example.com");
		expectedStateInTheDatabase.setTags(new ArrayList<>(Arrays.asList("tag1", "tag2", "tag3")));
		
		
		// save a new customer
		Customer customerToSave = new Customer();
		customerToSave.setName(expectedStateInTheDatabase.getName());
		customerToSave.setCompany(expectedStateInTheDatabase.getCompany());
		customerToSave.setAddress(expectedStateInTheDatabase.getAddress());
		customerToSave.setCustomerType(expectedStateInTheDatabase.getCustomerType());
		customerToSave.setPhoneNumber(expectedStateInTheDatabase.getPhoneNumber());
		customerToSave.setEmailAddress(expectedStateInTheDatabase.getEmailAddress());
		customerToSave.setTags(new ArrayList<>(expectedStateInTheDatabase.getTags()));
		
		Customer customerReturnedFromSaveCall = repository.save(customerToSave);
		
		
		// changing to request object should not change the saved record.
		customerToSave.setName("name");
		customerToSave.setCompany("comp");
		customerToSave.setAddress("address");
		customerToSave.setCustomerType("custType");
		customerToSave.setPhoneNumber("newPhone");
		customerToSave.setEmailAddress("diffEmail");
		customerToSave.getTags().add("anotherTag");
		
		assertCustomerProperties(expectedStateInTheDatabase, customerReturnedFromSaveCall);
		
		
		// changing the record returned from save should not change the db state.
		customerReturnedFromSaveCall.setName("name2");
		customerReturnedFromSaveCall.setCompany("comp2");
		customerReturnedFromSaveCall.setAddress("address2");
		customerReturnedFromSaveCall.setCustomerType("custType2");
		customerReturnedFromSaveCall.setPhoneNumber("newPhone2");
		customerReturnedFromSaveCall.setEmailAddress("diffEmail2");
		customerReturnedFromSaveCall.getTags().add("anotherTag2");
		
		Customer customerFetchedAgain = repository.find(customerReturnedFromSaveCall.getId());
		
		assertCustomerProperties(expectedStateInTheDatabase, customerFetchedAgain);
	}
	
	@Test
	public void canGetAllCustomersWhenRepoIsEmpty() {
		List<Customer> allCustomers = repository.findAll();
		Assert.assertEquals(0, allCustomers.size());
	}
	
	@Test
	public void canGetAllCustomersWhenThereAreSomeInTheRepository() {
		Customer customer1 = repository.save(new Customer());
		Customer customer2 = repository.save(new Customer());
		
		assertFindAllReturns(Arrays.asList(customer1, customer2));
	}
	
	@Test
	public void canDeleteCustomersFromTheRepository() {
		Customer customer1 = repository.save(new Customer());
		Customer customer2 = repository.save(new Customer());
		Customer customer3 = repository.save(new Customer());
		
		repository.delete(customer1.getId());
		assertFindAllReturns(Arrays.asList(customer2, customer3));
		
		repository.delete(customer3.getId());
		assertFindAllReturns(Arrays.asList(customer2));
		
		repository.delete(customer2.getId());
		assertFindAllReturns(Arrays.asList());
	}

	private void assertFindAllReturns(List<Customer> expectedCustomers) {
		List<Customer> allCustomers = repository.findAll();
		
		Assert.assertEquals(expectedCustomers.size(), allCustomers.size());
		for(int i = 0; i < allCustomers.size(); i++) {
			assertCustomerProperties(expectedCustomers.get(i), allCustomers.get(i));
		}
	}
	
	private void assertCustomerProperties(Customer expected, Customer actual) {
		Assert.assertEquals(expected.getId(), actual.getId());
		Assert.assertEquals(expected.getName(), actual.getName());
		Assert.assertEquals(expected.getCompany(), actual.getCompany());
		Assert.assertEquals(expected.getAddress(), actual.getAddress());
		Assert.assertEquals(expected.getCustomerType(), actual.getCustomerType());
		Assert.assertEquals(expected.getPhoneNumber(), actual.getPhoneNumber());
		Assert.assertEquals(expected.getEmailAddress(), actual.getEmailAddress());
		Assert.assertEquals(expected.getTags(), actual.getTags());
	}
}
