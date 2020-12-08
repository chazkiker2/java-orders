package com.lambdaschool.orders.repositories;


import com.lambdaschool.orders.models.Customer;
import org.springframework.data.repository.CrudRepository;

import java.util.List;



public interface CustomerRepository
		extends CrudRepository<Customer, Long> {

//	List<Customer> findAll();

	List<Customer> findAllByCustnameLike(String subname);

	List<Customer> findAllByCustnameContainingIgnoreCase(String subname);

	List<Customer> findAllByCustcode(long custcode);

	List<Customer> findAllByAgentcode(long agentcode);

//	List<Customer> findAllByOrders();

}
