package com.webapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.webapp.dao.CustomerDAO;
import com.webapp.entity.Customer;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerDAO customerDao;
	
	@Override
	@Transactional
	public List<Customer> getCustomers() {
		return customerDao.getCustomers();
	}

	@Override
	@Transactional
	public void saveCustomer(Customer customer) {
		customerDao.saveCustomer(customer);
	}

	@Override
	@Transactional
	public Customer getById(int id) {
		Customer customer=customerDao.findById(id);
		return customer;
	}

	@Override
	@Transactional
	public void deleteById(Integer id) {
		customerDao.deleteById(id);
		
	}

	@Override
	@Transactional
	public List<Customer> searchCustomer(String name) {
		System.out.println("connecting to DAO");
		List<Customer> customers=customerDao.searchCustomer(name);
		System.out.println("Came back from dao");
		return customers;
	}

	@Override
	@Transactional
	public void saveCustomer(List<Customer> list) {
		
		customerDao.saveCustomer(list);
	}

}
