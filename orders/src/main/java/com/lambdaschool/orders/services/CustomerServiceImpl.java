package com.lambdaschool.orders.services;


import com.lambdaschool.orders.models.Agent;
import com.lambdaschool.orders.models.Customer;
import com.lambdaschool.orders.models.Order;
import com.lambdaschool.orders.models.Payment;
import com.lambdaschool.orders.repositories.AgentRepository;
import com.lambdaschool.orders.repositories.CustomerRepository;
import com.lambdaschool.orders.repositories.OrderRepository;
import com.lambdaschool.orders.repositories.PaymentRepository;
import com.lambdaschool.orders.views.CustomerOrderCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;



@Transactional
@Service(value = "customerService")
public class CustomerServiceImpl
		implements CustomerService {
	private CustomerRepository customerRepo;
	private OrderRepository    orderRepo;
	private AgentRepository    agentRepo;
	private PaymentRepository  paymentRepo;

	@Autowired
	public CustomerServiceImpl(
			CustomerRepository customerRepo,
			OrderRepository orderRepo,
			AgentRepository agentRepo
	) {
		this.customerRepo = customerRepo;
		this.orderRepo    = orderRepo;
		this.agentRepo    = agentRepo;
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
	public Customer update(
			Customer customer,
			long id
	) {
		return null;
	}

	@Transactional
	@Override
	public Customer save(Customer c) {
		Customer newC = new Customer();
		if (c.getCustcode() != 0) {
			customerRepo.findById(c.getCustcode())
			            .orElseThrow(() -> new EntityNotFoundException("Customer " + c.getCustcode() + " Not Found"));
			newC.setCustcode(c.getCustcode());
		}

//
		newC.setAll(c);
		Agent newAgent = agentRepo.findById(c.getAgent()
		                                     .getAgentcode())
		                          .orElse(new Agent());
		agentRepo.save(newAgent);
		newC.setAgent(newAgent);
//		newAgent.addCustomer(newC);
		//		newAgent.getCustomers().add(newC);
//		newAgent.getCustomers()
//		        .add(newC);
//		newAgent.setCustomers(newAgent.getCustomers().a));
//		newC.setAgent(newAgent);

		newC.getOrders()
		    .clear();
		for (Order o : c.getOrders()) {
//			Order newOrder      = new Order();
			Order existingOrder = orderRepo.findById(o.getOrdnum())
			                               .orElse(null);
			if (existingOrder == null) {
				existingOrder = new Order();
				existingOrder.setOrdamount(o.getOrdamount());
				existingOrder.setAdvanceamount(o.getAdvanceamount());
				if (o.getOrderdescription() != null) {
					existingOrder.setOrderdescription(o.getOrderdescription());
				} else { existingOrder.setOrderdescription("SOD");}
				existingOrder.setCustomer(newC);

//				existingOrder.getPayments()
//				             .add(new Payment());
			}

			newC.getOrders()
			    .add(existingOrder);

			//			Order newOrder;
			//			if (o.getOrdnum() > 0) {
			//				newOrder = orderRepo.findById(o.getOrdnum())
			//				                    .orElseThrow(() -> new EntityNotFoundException("Order " + o.getOrdnum() + " Not Found"));
			//			} else {
			//				//				List<Order>     ords = new ArrayList<>();
			//				for (Order item : orderRepo.findAll()) {
			//					if (item.equals(o)) {
			//						newOrder = o;
			//						System.out.println("item.equals(o) —— " + newOrder );
			//						break;
			//					}
			//				}
			//			}

		}


		return customerRepo.save(newC);
	}

	@Transactional
	@Override
	public void delete(long id) {
		customerRepo.deleteById(id);
	}

	@Transactional
	@Override
	public void deleteAll() {
		customerRepo.deleteAll();
	}

}
