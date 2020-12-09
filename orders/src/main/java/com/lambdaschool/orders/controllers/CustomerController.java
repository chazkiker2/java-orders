package com.lambdaschool.orders.controllers;


import com.lambdaschool.orders.models.Customer;
import com.lambdaschool.orders.services.CustomerService;
import com.lambdaschool.orders.views.CustomerOrderCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
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
		Customer customer = customerService.findCustomerByCode(custcode);
		return new ResponseEntity<>(
				customer,
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

	/**
	 * Adds a new customer including any new orders
	 *
	 * @param newCustomer The customer to add
	 *
	 * @return JSON of freshly added customer, HTTP Headers with new customer's URI, and status of CREATED (201)
	 */
	@PostMapping(value = "/customer",
	             consumes = "application/json",
	             produces = "application/json")
	public ResponseEntity<?> addCustomer(
			@Valid
			@RequestBody
					Customer newCustomer
	) {
		newCustomer.setCustcode(0);
		newCustomer = customerService.save(newCustomer);
		HttpHeaders responseHeaders = new HttpHeaders();
		URI newCustomerURI = ServletUriComponentsBuilder.fromCurrentRequest()
		                                                .path("/{custcode}")
		                                                .buildAndExpand(newCustomer.getCustcode())
		                                                .toUri();
		responseHeaders.setLocation(newCustomerURI);
		return new ResponseEntity<>(
				newCustomer,
				responseHeaders,
				HttpStatus.CREATED
		);

	}

	@PutMapping(value = "/customer/{custcode}",
	            consumes = "application/json")
	public ResponseEntity<?> updateCustomer(
			@Valid
			@RequestBody
					Customer c,
			@PathVariable
					long custcode
	) {
		c.setCustcode(custcode);

		customerService.save(c);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
