package com.lambdaschool.orders.controllers;


import com.lambdaschool.orders.models.Customer;
import com.lambdaschool.orders.services.CustomerService;
import com.lambdaschool.orders.views.CustomerOrderCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;



@RestController
@RequestMapping("/customers")
public class CustomerController {
	private CustomerService customerService;

	@Autowired
	public CustomerController(CustomerService customerService) {
		this.customerService = customerService;
	}

	@GetMapping("/orders")
	public ResponseEntity<?> listAllCustomers() {
		List<Customer> customers = customerService.findAllCustomers();
		return new ResponseEntity<>(
				customers,
				HttpStatus.OK
		);
	}

	@GetMapping("/namelike/{subname}")
	public ResponseEntity<?> listCustomerWithNameLike(
			@PathVariable
					String subname
	) {
		List<Customer> customers = customerService.findAllByCustomerNameLike(subname);
		System.out.println(subname);
		System.out.println(customers);
		return new ResponseEntity<>(
				customers,
				HttpStatus.OK
		);
	}

	@GetMapping("/customer/{custcode}")
	public ResponseEntity<?> listCustomerWithId(
			@PathVariable
					long custcode
	) {
		List<Customer> customers = customerService.findCustomerByCode(custcode);
		return new ResponseEntity<>(
				customers,
				HttpStatus.OK
		);
	}

	@GetMapping(value = "/orders/count",
	            produces = {"application/json"})
	public ResponseEntity<?> listCustomerOrderCount() {
		List<CustomerOrderCount> customerOrderCounts = customerService.getCustomerOrderCount();
		return new ResponseEntity<>(
				customerOrderCounts,
				HttpStatus.OK
		);
	}

}
