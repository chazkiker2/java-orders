package com.lambdaschool.orders.services;


import com.lambdaschool.orders.models.Agent;
import com.lambdaschool.orders.repositories.AgentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service(value="agentrestService")
public class AgentRestServiceImpl implements AgentRestService {
	private final AgentRepository agentRepo;

	@Autowired
	public AgentRestServiceImpl(AgentRepository agentRepo) {
		this.agentRepo = agentRepo;
	}

	@Override
	public List<Agent> getAgents() {
		return agentRepo.findAll().toList();
	}

}
