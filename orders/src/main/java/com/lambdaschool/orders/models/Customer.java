package com.lambdaschool.orders.models;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;



@Entity
@Table(name = "customers")
@JsonIgnoreProperties(value = {"hasOpeningamt", "hasReceiveamt", "hasPaymentamt", "hasOutstandingamt"})
public class Customer {
	@Transient
	public boolean hasOpeningamt     = false;
	@Transient
	public boolean hasReceiveamt     = false;
	@Transient
	public boolean hasPaymentamt     = false;
	@Transient
	public boolean hasOutstandingamt = false;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long custcode;
	@Column(nullable = false)
	private String custname;
	@Column(nullable = false)
	private String custcity;
	@Column(nullable = false)
	private String workingarea;
	@Column(nullable = false)
	private String custcountry;
	@Column(nullable = false)
	private String grade;
	@Column(nullable = false)
	private double openingamt;
	@Column(nullable = false)
	private double receiveamt;
	@Column(nullable = false)
	private double paymentamt;
	@Column(nullable = false)
	private double outstandingamt;
	@Column(nullable = false)
	private String phone;
	@ManyToOne()
	@JoinColumn(name = "agentcode",
	            nullable = false)
	@JsonIgnoreProperties("agent")
	private Agent agent;
	@OneToMany(mappedBy = "customer",
	           cascade = CascadeType.ALL,
	           orphanRemoval = true)
	@JsonIgnoreProperties("customer")
	private List<Order> orders = new ArrayList<>();

	public Customer() {}

	public Customer(
			String custname,
			String custcity,
			String workingarea,
			String custcountry,
			String grade,
			double openingamt,
			double receiveamt,
			double paymentamt,
			double outstandingamt,
			String phone,
			Agent agent
	) {
		this.custname       = custname;
		this.custcity       = custcity;
		this.workingarea    = workingarea;
		this.custcountry    = custcountry;
		this.grade          = grade;
		this.openingamt     = openingamt;
		this.receiveamt     = receiveamt;
		this.paymentamt     = paymentamt;
		this.outstandingamt = outstandingamt;
		this.phone          = phone;
		this.agent          = agent;
	}

	public long getCustcode() {
		return custcode;
	}

	public void setCustcode(long custcode) {
		this.custcode = custcode;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	public void setAll(Customer in) {
		setCustname(in.getCustname());
		setCustcity(in.getCustcity());
		setWorkingarea(in.getWorkingarea());
		setCustcountry(in.getCustcountry());
		setGrade(in.getGrade());
		setOpeningamt(in.getOpeningamt());
		setReceiveamt(in.getReceiveamt());
		setPaymentamt(in.getPaymentamt());
		setOutstandingamt(in.getOutstandingamt());
		setPhone(in.getPhone());
//		setAgent(in.getAgent());
	}

	public String getCustname() {
		return custname;
	}

	public void setCustname(String custname) {
		this.custname = custname;
	}

	public String getCustcity() {
		return custcity;
	}

	public void setCustcity(String custcity) {
		this.custcity = custcity;
	}

	public String getWorkingarea() {
		return workingarea;
	}

	public void setWorkingarea(String workingarea) {
		this.workingarea = workingarea;
	}

	public String getCustcountry() {
		return custcountry;
	}

	public void setCustcountry(String custcountry) {
		this.custcountry = custcountry;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public double getOpeningamt() {
		return openingamt;
	}

	public void setOpeningamt(double openingamt) {
		hasOpeningamt   = true;
		this.openingamt = openingamt;
	}

	public double getReceiveamt() {
		return receiveamt;
	}

	public void setReceiveamt(double receiveamt) {
		hasReceiveamt   = true;
		this.receiveamt = receiveamt;
	}

	public double getPaymentamt() {
		return paymentamt;
	}

	public void setPaymentamt(double paymentamt) {
		hasPaymentamt   = true;
		this.paymentamt = paymentamt;
	}

	public double getOutstandingamt() {
		return outstandingamt;
	}

	public void setOutstandingamt(double outstandingamt) {
		hasOutstandingamt   = true;
		this.outstandingamt = outstandingamt;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Agent getAgent() {
		return agent;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}

	public void setCheckAll(
			@org.jetbrains.annotations.NotNull Customer in
	) {
		if (in.getCustname() != null) {
			setCustname(in.getCustname());
		}
		if (in.getCustcity() != null) {
			setCustcity(in.getCustcity());
		}
		if (in.getWorkingarea() != null) {
			setWorkingarea(in.getWorkingarea());
		}
		if (in.getCustcountry() != null) {
			setCustcountry(in.getCustcountry());
		}
		if (in.getGrade() != null) {
			setGrade(in.getGrade());
		}
		if (in.hasReceiveamt) {
			setReceiveamt(in.getReceiveamt());
		}
		if (in.hasPaymentamt) {
			setPaymentamt(in.getPaymentamt());
		}
		if (in.hasOpeningamt) {
			setOpeningamt(in.getOpeningamt());
		}
		if (in.hasOutstandingamt) {
			setOutstandingamt(in.getOutstandingamt());
		}
		if (in.getPhone() != null) {
			setPhone(in.getPhone());
		}
		if (in.getAgent() != null) {
			setAgent(in.getAgent());
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) { return true; }
		if (super.equals(obj)) { return true; }
		if (obj == null || getClass() != obj.getClass()) { return false; }
		Customer that = (Customer) obj;

		return
				custname.equals(that.custname) &&
				custcity.equals(that.custcity) &&
				workingarea.equals(that.workingarea) &&
				custcountry.equals(that.custcountry) &&
				grade.equals(that.grade) &&
				openingamt == that.openingamt &&
				receiveamt == that.receiveamt &&
				paymentamt == that.paymentamt &&
				outstandingamt == that.outstandingamt &&
				phone.equals(that.phone);
	}

}
