package com.lambdaschool.orders.services;


import com.lambdaschool.orders.models.Customer;
import com.lambdaschool.orders.views.CustomerOrderCount;

import java.util.List;



public interface CustomerService {
	// QUERY METHODS
	List<Customer> findAllCustomers();

	List<Customer> findAllByCustomerNameLike(String subname);

	Customer findCustomerByCode(long custcode);

	List<CustomerOrderCount> getCustomerOrderCount();

	//	// TRANSACTIONAL METHODS —— CRUD
	//	Customer update(
	//			Customer customer,
	//			long id
	//	); // PATCH, PUT

	Customer save(Customer customer); // POST

	void delete(long id); // DELETE

	void deleteAll(); // DELETE

}
