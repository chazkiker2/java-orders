package com.lambdaschool.orders.services;


import com.lambdaschool.orders.models.Customer;
import com.lambdaschool.orders.repositories.CustomerRepository;
import com.lambdaschool.orders.views.CustomerOrderCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;



@Transactional
@Service(value = "customerService")
public class CustomerServiceImpl
		implements CustomerService {
	private CustomerRepository customerRepo;

	@Autowired
	public CustomerServiceImpl(CustomerRepository customerRepo) {
		this.customerRepo = customerRepo;
	}

	@Override
	public List<Customer> findAllCustomers() {
		List<Customer> customers = new ArrayList<>();
		customerRepo.findAll()
		            .iterator()
		            .forEachRemaining(customers::add);
		return customers;
	}

	@Override
	public List<Customer> findAllByCustomerNameLike(String subname) {
		return customerRepo.findAllByCustnameContainingIgnoreCase(subname);
	}

	@Override
	public List<Customer> findCustomerByCode(long custcode) {
		return customerRepo.findAllByCustcode(custcode);
	}

	@Override
	public List<CustomerOrderCount> getCustomerOrderCount() {
		return customerRepo.getCustomerOrderCount();
	}

	@Transactional
	@Override
	public Customer save(Customer customer) {
		return customerRepo.save(customer);
	}

}
