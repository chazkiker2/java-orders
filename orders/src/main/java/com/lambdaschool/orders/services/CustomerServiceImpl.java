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
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;



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
			AgentRepository agentRepo,
			PaymentRepository paymentRepo
	) {
		this.customerRepo = customerRepo;
		this.orderRepo    = orderRepo;
		this.agentRepo    = agentRepo;
		this.paymentRepo  = paymentRepo;
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
	public Customer findCustomerByCode(long custcode)
			throws
			EntityNotFoundException {
		Optional<Customer> customer = customerRepo.findById(custcode);
		if (customer.isPresent()) {
			return customer.get();
		} else {
			throw new EntityNotFoundException("Customer with id " + custcode + " Not Found");
		}
	}

	@Override
	public List<CustomerOrderCount> getCustomerOrderCount() {
		return customerRepo.getCustomerOrderCount();
	}



		@Transactional
		@Override
		public Customer update(
				Customer c,
				long id
		) {
			Customer upC = customerRepo.findById(id)
			                           .orElseThrow(() -> new EntityNotFoundException("Customer " + id + " Not " + "Found"));
			upC.setCustcode(id);

			upC.setCheckAll(c);

			if (c.getOrders()
			     .size() > 0) {
				upC.getOrders()
				   .clear();
				for (Order order : c.getOrders()) {
					Optional<Order> optionalOrder = orderRepo.findById(order.getOrdnum());
					Order           newOrder;
					if (optionalOrder.isPresent()) {
						newOrder = optionalOrder.get();
					} else {
						newOrder = new Order();
						newOrder.setOrdamount(order.getOrdamount());
						newOrder.setAdvanceamount(order.getAdvanceamount());
						if (order.getOrderdescription() != null) {
							newOrder.setOrderdescription(order.getOrderdescription());
						} else {
							newOrder.setOrderdescription("None");
						}
						newOrder.setCustomer(upC);
						newOrder.getPayments()
						        .clear();
						for (Payment payment : order.getPayments()) {
							Optional<Payment> optionalPayment = paymentRepo.findById(payment.getPaymentid());
							Payment           newPayment;
							if (optionalPayment.isPresent()) {
								newPayment = optionalPayment.get();
							} else {
								newPayment = new Payment();
								if (payment.getType() != null) {
									newPayment.setType(payment.getType());
								}
								if (payment.getOrders()
								           .size() > 0) {
									newPayment.setOrders(payment.getOrders());
								}
							}
							newOrder.getPayments().add(newPayment);
						}
					}
					upC.getOrders()
					   .add(newOrder);
				}
			}

			if (c.getAgent() != null) {
				Optional<Agent> optionalAgent = agentRepo.findById(c.getAgent()
				                                                    .getAgentcode());
				Agent newAgent;
				if (optionalAgent.isPresent()) {
					newAgent = optionalAgent.get();
				} else {
					newAgent = new Agent();
					agentRepo.save(newAgent);
				}

				upC.setAgent(newAgent);
			}


			return customerRepo.save(upC);
		}

	@Transactional
	@Override
	public Customer save(Customer customer) {
		Customer newCustomer = new Customer();
		if (customer.getCustcode() != 0) {
			customerRepo.findById(customer.getCustcode())
			            .orElseThrow(() -> new EntityNotFoundException("Customer " + customer.getCustcode() + " Not Found"));
			newCustomer.setCustcode(customer.getCustcode());
			newCustomer.setCheckAll(customer);
		} else {
			newCustomer.setAll(customer);
		}

		newCustomer.getOrders()
		           .clear();
		for (Order order : customer.getOrders()) {
			Order           newOrder;
			Optional<Order> optionalOrder = orderRepo.findById(order.getOrdnum());

			if (optionalOrder.isPresent()) {
				newOrder = optionalOrder.get();
			} else {
				newOrder = new Order();
				newOrder.setOrdamount(order.getOrdamount());
				newOrder.setAdvanceamount(order.getAdvanceamount());
				if (order.getOrderdescription() != null) {
					newOrder.setOrderdescription(order.getOrderdescription());
				} else { newOrder.setOrderdescription("None"); }
				newOrder.setCustomer(newCustomer);
				newOrder.getPayments()
				        .clear();

				for (Payment payment : order.getPayments()) {
					Payment           newPayment;
					Optional<Payment> optionalPayment = paymentRepo.findById(payment.getPaymentid());
					if (optionalPayment.isPresent()) {
						newPayment = optionalPayment.get();
					} else {
						newPayment = new Payment();
						if (payment.getType() != null) {
							newPayment.setType(payment.getType());
						}
						if (payment.getOrders()
						           .size() > 0) {
							newPayment.setOrders(payment.getOrders());
						}
					}
					newOrder.getPayments()
					        .add(newPayment);
				}
			}

			newCustomer.getOrders()
			           .add(newOrder);
		}

		Agent newAgent;
		Optional<Agent> optionalAgent = agentRepo.findById(customer.getAgent()
		                                                           .getAgentcode());
		if (optionalAgent.isEmpty()) {
			newAgent = new Agent();
			agentRepo.save(newAgent);
		} else {
			newAgent = optionalAgent.get();
		}
		newCustomer.setAgent(newAgent);


		return customerRepo.save(newCustomer);
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
