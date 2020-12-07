package com.lambdaschool.orders.services;


import com.lambdaschool.orders.models.Agent;
import com.lambdaschool.orders.repositories.AgentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@Transactional
@Service(value="agentservice")
public class AgentServiceImpl implements AgentService {
	private AgentRepository agentRepo;

	@Autowired
	public AgentServiceImpl(AgentRepository agentRepo) {
		this.agentRepo = agentRepo;
	}

	@Transactional
	@Override
	public Agent save(Agent agent) {
		return agentRepo.save(agent);
	}

}
