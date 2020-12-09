package com.lambdaschool.orders.repositories;


import com.lambdaschool.orders.models.Order;
import org.springframework.data.repository.CrudRepository;

import java.util.List;



public interface OrderRepository
		extends CrudRepository<Order, Long> {
	Order findOrderByOrdnum(long ordnum);
	List<Order> findAllByAdvanceamountGreaterThan(double amount);
//	List<Order> findOrdersByAdvanceamountGreaterThanAndCustomer_OutstandingamtGreaterThan(double amount);
	List<Order> findTop3OrdersByCustomer_OutstandingamtGreaterThan(double amount);

}
