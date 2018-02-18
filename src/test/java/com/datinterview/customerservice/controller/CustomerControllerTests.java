package com.datinterview.customerservice.controller;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RunWith(SpringRunner.class)
public class CustomerControllerTests {

	private CustomerController controller;
	private Class<CustomerController> controllerClass = CustomerController.class;
	
	@Before
	public void setUp() {
		controller = new CustomerController();
	}
	
	@Test
	public void isARestController() throws Exception {
		Assert.assertNotNull(controllerClass.getDeclaredAnnotation(RestController.class));
		
		GetMapping methodAnnotation = controllerClass.getDeclaredMethod("getAllCustomers").getDeclaredAnnotation(GetMapping.class);
		Assert.assertEquals("/customers", methodAnnotation.value()[0]);
	}
	
	@Test
	public void canGetAllCustomers() {
		List<Integer> allCustomers = controller.getAllCustomers();
		
		Assert.assertEquals(Arrays.asList(1, 2, 3), allCustomers);
	}
	
}
