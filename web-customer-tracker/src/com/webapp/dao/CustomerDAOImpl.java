package com.webapp.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.webapp.entity.Customer;

@Repository
public class CustomerDAOImpl implements CustomerDAO {

	@Autowired
	private SessionFactory factory;

	@Override
	public List<Customer> getCustomers() {
		Session session = factory.getCurrentSession();

		@SuppressWarnings("unchecked")
		List<Customer> list = session.createQuery("from Customer order by lastname", Customer.class).getResultList();

		// Query<Customer> query=session.createQuery("from Customer",Customer.class)

		return list;
	}

	@Override
	public void saveCustomer(Customer customer) {
		Session session = factory.getCurrentSession();
		session.saveOrUpdate(customer);
	}

	@Override
	public Customer findById(int id) {
		Session session = factory.getCurrentSession();
		Customer customer = session.get(Customer.class, id);
		return customer;
	}

	@Override
	public void deleteById(Integer id) {
		Session session = factory.getCurrentSession();
		Query query = session.createQuery("delete from Customer where id=:id");
		query.setParameter("id", id);
		query.executeUpdate();
	}

	@Override
	public List<Customer> searchCustomer(String theSearchName) {
		System.out.println("Connecting to db");
		Session session = factory.getCurrentSession();
		Query query = null;
		if (theSearchName != null && theSearchName.trim().length() > 0) {
			query = session.createQuery(
					"from Customer where lower(firstName) like :theName or lower(lastname) like :theName",
					Customer.class);
			query.setParameter("theName", "%" + theSearchName.toLowerCase() + "%");
		} else {
			// theSearchName is empty ... so just get all customers
			query = session.createQuery("from Customer", Customer.class);
		}
		List<Customer> customers = query.getResultList();
		System.out.println("Came back from DB");
		return customers;
	}

	@Override
	public void saveCustomer(List<Customer> list) {
		for (Customer customer : list) {
			Session session = factory.getCurrentSession();
			session.save(customer);
			//session.getTransaction().commit();
		}
	}

}
