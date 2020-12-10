//package com.lambdaschool.orders.controllers;
//
//
//import com.lambdaschool.orders.models.Agent;
//import com.lambdaschool.orders.models.dto.AgentPartDTO;
//import com.lambdaschool.orders.repositories.AgentRepository;
//import com.lambdaschool.orders.services.AgentRestService;
//import com.lambdaschool.orders.services.AgentService;
//import com.lambdaschool.orders.util.DTO;
//import org.modelmapper.ModelMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@RestController
//@RequestMapping("/restagents")
//public class AgentRestController {
////	private AgentRepository agentRepo;
//	private final AgentRestService agentService;
//	@Bean
//	public ModelMapper modelMapper() {
//		return new ModelMapper();
//	}
//	private final ModelMapper modelMapper = modelMapper();
//
//	@Autowired
//	public AgentRestController(
//			AgentRestService agentService
//	) {
//		this.agentService   = agentService;
//	}
//
//	@GetMapping("/all")
//	public List<Agent> getAgents() {
//		return agentService.getAgents();
//	}
//
//	private AgentPartDTO convertToDto(Agent agent) {
//		return modelMapper.map(agent, AgentPartDTO.class);
//	}
//	@GetMapping("/all/partial")
//	public List<AgentPartDTO> getPartialAgents() {
//		List<Agent> agents = agentService.getAgents();
//		return agents.stream().map(this::convertToDto).collect(Collectors.toList());
//	}
//
////	@GetMapping("/partial/all")
////	public void getPartialAgents() {
////		List<Agent> partialAgents = new ArrayList<>();
////		agentRepo.findAll().stream().forEach((Agent agent) -> {
////			partialAgents.add(@DTO(AgentPartDTO.class) agent);
////		});
//	}



