package com.lambdaschool.orders.repositories;


import com.lambdaschool.orders.models.Agent;
import org.springframework.data.repository.CrudRepository;

import java.util.List;



public interface AgentRepository
		extends CrudRepository<Agent, Long> {
	List<Agent> findAgentsByAgentcode(long agentcode);

}
