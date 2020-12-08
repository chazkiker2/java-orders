package com.lambdaschool.orders.services;


import com.lambdaschool.orders.models.Agent;

import java.util.List;



public interface AgentService {
	List<Agent> findAgentsByAgentcode(long agentcode);

	Agent save(Agent agent);

}
