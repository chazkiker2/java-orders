package com.lambdaschool.orders.models.dto;


import javax.persistence.Id;
import javax.validation.constraints.NotNull;



public class AgentPartDTO {
	@Id
	@NotNull
	private long agentcode;
	@NotNull
	private String agentname;
	@NotNull
	private String phone;
	@NotNull
	private String workingarea;
	@NotNull
	private String country;

	public AgentPartDTO() {}

	public AgentPartDTO(
			@NotNull long agentcode,
			@NotNull String agentname,
			@NotNull String phone,
			@NotNull String workingarea,
			@NotNull String country
	) {
		this.agentcode   = agentcode;
		this.agentname   = agentname;
		this.phone       = phone;
		this.workingarea = workingarea;
		this.country     = country;
	}

	public long getAgentcode() {
		return agentcode;
	}

	public void setAgentcode(long agentcode) {
		this.agentcode = agentcode;
	}

	public String getAgentname() {
		return agentname;
	}

	public void setAgentname(String agentname) {
		this.agentname = agentname;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getWorkingarea() {
		return workingarea;
	}

	public void setWorkingarea(String workingarea) {
		this.workingarea = workingarea;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

}
