package com.lambdaschool.orders.controllers;


import com.lambdaschool.orders.models.Order;
import com.lambdaschool.orders.services.OrderService;
import org.apache.coyote.Response;
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
@RequestMapping("/orders")
public class OrderController {
	private OrderService orderService;

	@Autowired
	public OrderController(OrderService orderService) {
		this.orderService = orderService;
	}

	@GetMapping(value = "/order/{ordnum}")
	public ResponseEntity<?> listOrderByOrdernum(
			@PathVariable
					long ordnum
	) {
		Order order = orderService.findOrderByNum(ordnum);
		return new ResponseEntity<>(
				order,
				HttpStatus.OK
		);
	}

	@GetMapping(value = "/advanceamount",
	            produces = {"application/json"})
	public ResponseEntity<?> listOrdersAdvanceamount() {
		//		List<Order> orders = orderService.findOrdersWithPositiveAdvance();
		List<Order> orders = orderService.findOrdersWithPositiveOutstanding();
		return new ResponseEntity<>(
				orders,
				HttpStatus.OK
		);
	}

	@PostMapping(value = "/order",
	             consumes = "application/json",
	             produces = "application/json")
	public ResponseEntity<?> addOrder(
			@Valid
			@RequestBody
					Order order
	) {
		order.setOrdnum(0);
		Order       newOrder        = orderService.save(order);
		HttpHeaders responseHeaders = new HttpHeaders();
		URI newOrderUri = ServletUriComponentsBuilder.fromCurrentRequest()
		                                             .path("/{ordnum}")
		                                             .buildAndExpand(newOrder.getOrdnum())
		                                             .toUri();
		responseHeaders.setLocation(newOrderUri);
		return new ResponseEntity<>(
				newOrder,
				responseHeaders,
				HttpStatus.CREATED
		);
	}

	@PutMapping(value="/order/{ordnum}", consumes="application/json")
	public ResponseEntity<?> updateOrder(@Valid @RequestBody Order orderIn, @PathVariable long ordnum) {
		orderIn.setOrdnum(ordnum);
		orderService.save(orderIn);
		return new ResponseEntity<>(HttpStatus.OK);
	}

//	@PatchMapping(value="/order/{ordnum}", consumes="application/json")
//	public ResponseEntity<?> patchOrder(@RequestBody Order orderIn, @PathVariable long ordnum) {
//		orderService.update(orderIn, ordnum);
//		return new ResponseEntity<>(HttpStatus.OK);
//	}

	@DeleteMapping(value="order/{ordnum}")
	public ResponseEntity<?> deleteOrder(@PathVariable long ordnum) {
		orderService.delete(ordnum);
		return new ResponseEntity<>(HttpStatus.OK);
	}


}
