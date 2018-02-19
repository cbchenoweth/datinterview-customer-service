package com.datinterview.customerservice.controller;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.datinterview.customerservice.CustomerServiceApplication;
import com.datinterview.customerservice.model.Customer;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CustomerServiceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class CustomerControllerIntegrationTests {

	TestRestTemplate restTemplate = new TestRestTemplate();

	@Test
	public void testCreateReadAndDeleteAValidCustomer() throws Exception {
		// create new customer
		Customer customerRequest = new Customer();
		customerRequest.setName("customer1");

		ResponseEntity<String> response = createCustomer(customerRequest);
		Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
		
		JSONObject responseJSON = new JSONObject(response.getBody());
		
		JSONObject expectedCustomerJSONFromCreate = new JSONObject();
		expectedCustomerJSONFromCreate.put("name", customerRequest.getName());
		
		JSONAssert.assertEquals(expectedCustomerJSONFromCreate, responseJSON, false);
		
		int customerId = responseJSON.getInt("id");
		
		
		// read all customers - should have 1
		response = getAllCustomers();
		Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
		JSONAssert.assertEquals(expectedCustomerJSONFromCreate, new JSONArray(response.getBody()).getJSONObject(0), false);
		
		// TODO - update record and check again!
		customerRequest.setName("customer2");
		response = updateCustomer(customerId, customerRequest);
		Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
		
		JSONObject expectedCustomerJSONFromUpdate = new JSONObject();
		expectedCustomerJSONFromUpdate.put("name", customerRequest.getName());
		JSONAssert.assertEquals(expectedCustomerJSONFromUpdate, new JSONObject(response.getBody()), false);
		
		// read all customers - should have 1 (updated)
		response = getAllCustomers();
		Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
		JSONAssert.assertEquals(expectedCustomerJSONFromUpdate, new JSONArray(response.getBody()).getJSONObject(0), false);
		
		// TODO - delete and then check again!
		response = deleteCustomer(customerId);
		Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
		
		// read all customers - should have 0
		response = getAllCustomers();
		Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
		Assert.assertEquals(0, new JSONArray(response.getBody()).length());
	}

	private ResponseEntity<String> deleteCustomer(int customerId) {
		return restTemplate.exchange("http://localhost:8080/customers/" + customerId, HttpMethod.DELETE, new HttpEntity<String>(""), String.class);
	}

	private ResponseEntity<String> getAllCustomers() {
		return restTemplate.exchange("http://localhost:8080/customers", HttpMethod.GET, new HttpEntity<String>(""), String.class);
	}

	private ResponseEntity<String> createCustomer(Customer newCustomerRequest) {
		HttpEntity<Customer> entity = new HttpEntity<Customer>(newCustomerRequest);
		return restTemplate.exchange("http://localhost:8080/customers", HttpMethod.POST, entity, String.class);
	}
	
	private ResponseEntity<String> updateCustomer(int customerId, Customer updateCustomerRequest) {
		HttpEntity<Customer> entity = new HttpEntity<Customer>(updateCustomerRequest);
		return restTemplate.exchange("http://localhost:8080/customers/" + customerId, HttpMethod.PUT, entity, String.class);
	}

}