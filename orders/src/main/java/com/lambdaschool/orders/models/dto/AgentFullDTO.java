package com.lambdaschool.orders.models.dto;


import javax.persistence.Id;
import javax.validation.constraints.NotNull;



public class AgentFullDTO {
	@Id
	private long paymentid;
	@NotNull
	private String agentname;
	@NotNull
	private String workingarea;
	@NotNull
	private double commission;
	@NotNull
	private String phone;
	@NotNull
	private String country;

	public long getPaymentid() {
		return paymentid;
	}

	public void setPaymentid(long paymentid) {
		this.paymentid = paymentid;
	}

	public String getAgentname() {
		return agentname;
	}

	public void setAgentname(String agentname) {
		this.agentname = agentname;
	}

	public String getWorkingarea() {
		return workingarea;
	}

	public void setWorkingarea(String workingarea) {
		this.workingarea = workingarea;
	}

	public double getCommission() {
		return commission;
	}

	public void setCommission(double commission) {
		this.commission = commission;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

}
