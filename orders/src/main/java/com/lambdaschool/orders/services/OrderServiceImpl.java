package com.lambdaschool.orders.services;


import com.lambdaschool.orders.models.Order;
import com.lambdaschool.orders.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;



@Transactional
@Service(value="orderservice")
public class OrderServiceImpl implements OrderService {
	private OrderRepository orderRepo;

	@Autowired
	public OrderServiceImpl(OrderRepository orderRepo) {
		this.orderRepo = orderRepo;
	}

	@Transactional
	@Override
	public Order save(Order order) {
		return orderRepo.save(order);
	}

}
