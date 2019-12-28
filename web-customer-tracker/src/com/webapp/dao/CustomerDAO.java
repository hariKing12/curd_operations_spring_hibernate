package com.webapp.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.webapp.entity.Customer;

@Repository
public interface CustomerDAO {
	
	public List<Customer> getCustomers();

	public void saveCustomer(Customer customer);

	public Customer findById(int id);

	public void deleteById(Integer id);

	public List<Customer> searchCustomer(String name);

	public void saveCustomer(List<Customer> list);

}
