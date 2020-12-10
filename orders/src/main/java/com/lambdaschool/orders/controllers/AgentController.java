package com.lambdaschool.orders.controllers;


import com.lambdaschool.orders.models.Agent;
import com.lambdaschool.orders.models.dto.AgentFullDTO;
import com.lambdaschool.orders.models.dto.AgentPartDTO;
import com.lambdaschool.orders.services.AgentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;



@RestController
@RequestMapping("/agents")
public class AgentController {
	private final AgentService agentService;
//	private final ModelMapper modelMapper = modelMapper();

	@Resource(name="modelMapper")
	private final ModelMapper modelMapper;

	@Autowired
	public AgentController(
			AgentService agentService,
			ModelMapper modelMapper
	) {
		this.agentService = agentService;
		this.modelMapper  = modelMapper;
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
		List<Agent> agents = agentService.findAgentDetails()
		                                 .stream()
		                                 .collect(Collectors.toList());

		return new ResponseEntity<>(
				agents,
				HttpStatus.OK
		);
	}

	@GetMapping("/all/partial")
	public List<AgentPartDTO> getPartialAgents() {
		List<Agent> agents = agentService.findAll();
		return agents.stream()
		             .map(this::convertToPartDto)
		             .collect(Collectors.toList());
	}

	private AgentPartDTO convertToPartDto(Agent agent) {
		return modelMapper.map(
				agent,
				AgentPartDTO.class
		);
	}

	@GetMapping("/all/full")
	public List<AgentFullDTO> getFullAgents() {
		List<Agent> agents = agentService.findAll();
		return agents.stream()
		             .map(this::convertToFullDto)
		             .collect(Collectors.toList());
	}

	private AgentFullDTO convertToFullDto(Agent agent) {
		return modelMapper.map(
				agent,
				AgentFullDTO.class
		);
	}


}
