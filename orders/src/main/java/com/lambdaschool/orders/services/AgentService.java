package com.lambdaschool.orders.services;


import com.lambdaschool.orders.models.Agent;
import org.springframework.data.util.Streamable;

import java.util.List;



public interface AgentService {
	List<Agent> findAgentsByAgentcode(long agentcode);
	List<Agent> findAgentDetails();
	Agent save(Agent agent);

}
