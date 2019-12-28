package com.webapp.service;

import java.util.List;

import com.webapp.entity.Customer;

public interface CustomerService {

	public List<Customer> getCustomers();

	public void saveCustomer(Customer customer);

	public Customer getById(int id);

	public void deleteById(Integer id);

	public List<Customer> searchCustomer(String name);

	public void saveCustomer(List<Customer> list);
	
}
