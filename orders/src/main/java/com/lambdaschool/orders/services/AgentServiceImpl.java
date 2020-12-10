package com.lambdaschool.orders.services;


import com.lambdaschool.orders.models.Agent;
import com.lambdaschool.orders.repositories.AgentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;



@Transactional
@Service(value = "agentservice")
public class AgentServiceImpl
		implements AgentService {
	private AgentRepository agentRepo;

	@Autowired
	public AgentServiceImpl(AgentRepository agentRepo) {
		this.agentRepo = agentRepo;
	}

	@Override
	public List<Agent> findAgentsByAgentcode(long agentcode) {
		return agentRepo.findAgentsByAgentcode(agentcode);
	}

	@Override
	public List<Agent> findAgentDetails() {
		return agentRepo.findAll()
		                .stream()
		                .collect(Collectors.toList());
	}
	@Override
	public List<Agent> findAll() {
		return agentRepo.findAll().toList();
	}

	@Transactional
	@Override
	public Agent save(Agent agent) {
		return agentRepo.save(agent);
	}

}
