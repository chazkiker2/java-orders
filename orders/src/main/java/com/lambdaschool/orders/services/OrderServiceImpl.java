package com.lambdaschool.orders.services;


import com.lambdaschool.orders.models.Order;
import com.lambdaschool.orders.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;



@Transactional
@Service(value = "orderservice")
public class OrderServiceImpl
		implements OrderService {
	private OrderRepository orderRepo;

	@Autowired
	public OrderServiceImpl(OrderRepository orderRepo) {
		this.orderRepo = orderRepo;
	}

	@Override
	public Order findOrderByNum(long ordnum) {
		Order o = orderRepo.findOrderByOrdnum(ordnum);
		if (o == null) {
			throw new EntityNotFoundException("Order with ordernum " + ordnum + " Not Found");
		}
		return o;
	}

	@Override
	public List<Order> findOrdersWithPositiveAdvance() {
		return orderRepo.findAllByAdvanceamountGreaterThan(0);
	}

	@Override
	public List<Order> findOrdersWithPositiveOutstanding() {
		return orderRepo.findTop3OrdersByCustomer_OutstandingamtGreaterThan(0);
	}

	@Transactional
	@Override
	public Order save(Order order) {
		return orderRepo.save(order);
	}

}
