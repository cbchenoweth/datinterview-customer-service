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
		Customer newCustomerRequest = new Customer();
		newCustomerRequest.setName("customer1");
		
		JSONObject expectedCustomerJSON = new JSONObject();
		expectedCustomerJSON.put("name", newCustomerRequest.getName());
		
		// create new customer
		ResponseEntity<String> response = createCustomer(newCustomerRequest);
		Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
		JSONAssert.assertEquals(expectedCustomerJSON, new JSONObject(response.getBody()), false);
		
		// read all customers - should have 1
		response = getAllCustomers();
		Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
		JSONAssert.assertEquals(expectedCustomerJSON, new JSONArray(response.getBody()).getJSONObject(0), false);
		
		// TODO - delete and then check again!
	}

	private ResponseEntity<String> getAllCustomers() {
		ResponseEntity<String> response;
		response = restTemplate.exchange("http://localhost:8080/customers", HttpMethod.GET, new HttpEntity<String>(""), String.class);
		return response;
	}

	private ResponseEntity<String> createCustomer(Customer newCustomerRequest) {
		HttpEntity<Customer> entity = new HttpEntity<Customer>(newCustomerRequest);
		ResponseEntity<String> response = restTemplate.exchange("http://localhost:8080/customers", HttpMethod.POST, entity, String.class);
		return response;
	}

}