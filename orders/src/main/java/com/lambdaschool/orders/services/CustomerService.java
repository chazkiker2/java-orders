package com.lambdaschool.orders.services;


import com.lambdaschool.orders.models.Customer;
import com.lambdaschool.orders.views.CustomerOrderCount;

import java.util.List;



public interface CustomerService {
	List<Customer> findAllCustomers();

	List<Customer> findAllByCustomerNameLike(String subname);

	List<Customer> findCustomerByCode(long custcode);

	List<CustomerOrderCount> getCustomerOrderCount();
	Customer save(Customer customer);

}
