package com.datinterview.customerservice.model;

import java.util.ArrayList;
import java.util.List;

public class Customer {
	private int id;
	private String name;
	private String company;
	private String address;
	private String customerType;
	private String phoneNumber;
	private String emailAddress;
	private List<String> tags = new ArrayList<>();
	private int parentCustomerId;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public int getParentCustomerId() {
		return parentCustomerId;
	}

	public void setParentCustomerId(int parentCustomerId) {
		this.parentCustomerId = parentCustomerId;
	}

}
