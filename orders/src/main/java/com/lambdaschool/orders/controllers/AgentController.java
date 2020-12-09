package com.lambdaschool.orders.controllers;


import com.lambdaschool.orders.models.Agent;
import com.lambdaschool.orders.services.AgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;



@RestController
@RequestMapping("/agents")
public class AgentController {
	private AgentService agentService;

	@Autowired
	public AgentController(AgentService agentService) {
		this.agentService = agentService;
	}

	@GetMapping("/agent/{agentcode}")
	public ResponseEntity<?> listCustomerWithCode(
			@PathVariable
					long agentcode
	) {
		List<Agent> agents = agentService.findAgentsByAgentcode(agentcode);
		return new ResponseEntity<>(
				agents,
				HttpStatus.OK
		);
	}

	@GetMapping("/all")
	public ResponseEntity<?> listAllAgents() {
		List<Agent> agents = agentService.findAgentDetails().stream().collect(Collectors.toList());

		return new ResponseEntity<>(agents, HttpStatus.OK);
	}


}
