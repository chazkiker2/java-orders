package com.lambdaschool.orders.services;


import com.lambdaschool.orders.models.Customer;
import com.lambdaschool.orders.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;



@Transactional
@Service(value = "customerservice")
public class CustomerServiceImpl
		implements CustomerService {
	private CustomerRepository customerRepository;

	@Autowired
	public CustomerServiceImpl(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	@Transactional

	@Override
	public Customer save(Customer customer) {
		return customerRepository.save(customer);
	}

}
