package com.lambdaschool.orders.services;


import com.lambdaschool.orders.models.Customer;
import com.lambdaschool.orders.models.Order;
import com.lambdaschool.orders.models.Payment;
import com.lambdaschool.orders.repositories.CustomerRepository;
import com.lambdaschool.orders.repositories.OrderRepository;
import com.lambdaschool.orders.repositories.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;



@Transactional
@Service(value = "orderservice")
public class OrderServiceImpl
		implements OrderService {
	private OrderRepository   orderRepo;
	private PaymentRepository paymentRepo;
	private CustomerRepository customerRepo;

	@Autowired
	public OrderServiceImpl(
			OrderRepository orderRepo,
			PaymentRepository paymentRepo,
			CustomerRepository customerRepo
	) {
		this.orderRepo   = orderRepo;
		this.paymentRepo = paymentRepo;
		this.customerRepo = customerRepo;
	}

	@Override
	public Order findOrderByNum(long ordnum) {
		//		Order o = orderRepo.findOrderByOrdnum(ordnum);
		return orderRepo.findById(ordnum)
		                .orElseThrow(() -> new EntityNotFoundException("Order with ordernum " + ordnum + " Not Found"));
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
		Order newOrder = new Order();
		if (order.getOrdnum() != 0) {
			orderRepo.findById(order.getOrdnum())
			         .orElseThrow(() -> new EntityNotFoundException("Order " + order.getOrdnum() + " Not Found"));
			newOrder.setOrdnum(order.getOrdnum());
		}
		newOrder.setAll(order);

		if (order.getCustomer() != null) {
			Customer newCustomer;
			Optional<Customer> optionalCustomer = customerRepo.findById(order.getCustomer().getCustcode());
			if (optionalCustomer.isPresent()) {
				newCustomer = optionalCustomer.get();
			} else {
				newCustomer = new Customer();
				newCustomer.setAll(order.getCustomer());
			}
			newOrder.setCustomer(newCustomer);
		}

		newOrder.getPayments()
		        .clear();
		for (Payment p : order.getPayments()) {
			Payment           newPayment;
			Optional<Payment> optionalPayment = paymentRepo.findById(p.getPaymentid());
			if (optionalPayment.isPresent()) {
				newPayment = optionalPayment.get();
			} else {
				newPayment = new Payment();
				newPayment.setCheckAll(p);
			}
			newOrder.getPayments()
			        .add(newPayment);
		}
		return orderRepo.save(newOrder);
	}

//	@Override
//	public Order update(
//			Order order,
//			long ordnum
//	) {
//		return null;
//	}

	@Override
	public void delete(long ordnum) {

	}

	@Override
	public void deleteAll() {

	}

}
