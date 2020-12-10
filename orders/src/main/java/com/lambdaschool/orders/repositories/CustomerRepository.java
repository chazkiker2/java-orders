package com.lambdaschool.orders.repositories;


import com.lambdaschool.orders.models.Customer;
import com.lambdaschool.orders.views.CustomerOrderCount;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;



public interface CustomerRepository
		extends CrudRepository<Customer, Long> {


//	List<Customer> findAllByCustnameLike(String subname);

	List<Customer> findAllByCustnameContainingIgnoreCase(String subname);

	List<Customer> findAllByCustcode(long custcode);

	@Query(value = "SELECT count(o.ordnum) as order_count, "+
	               "c.custcode as customer_code, "+
	               "c.custname as customer_name "+
	               "FROM customers c "+
	               "LEFT JOIN orders o "+
	               "ON c.custcode = o.custcode "+
	               "GROUP BY c .custcode " +
	               "ORDER BY order_count DESC;",
	       nativeQuery = true)
	List<CustomerOrderCount> getCustomerOrderCount();

}
