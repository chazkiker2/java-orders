package com.lambdaschool.orders.repositories;


import com.lambdaschool.orders.models.Agent;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.util.Streamable;

import java.util.List;



public interface AgentRepository
		extends CrudRepository<Agent, Long> {
	List<Agent> findAgentsByAgentcode(long agentcode);
	@NotNull Streamable<Agent> findAll();
}
