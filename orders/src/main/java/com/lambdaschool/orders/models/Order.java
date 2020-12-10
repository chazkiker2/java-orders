package com.lambdaschool.orders.models;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;



@Entity
@Table(name = "orders")
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long ordnum;

	@Column(nullable = false,
	        unique = false)
	private double ordamount;

	@Column(nullable = false,
	        unique = false)
	private double advanceamount;

	@Column(nullable = false,
	        unique = false)
	private String orderdescription;

	@ManyToOne()
	@JoinColumn(name = "custcode",
	            nullable = false)
	@JsonIgnoreProperties("customers")
	private Customer customer;

	@ManyToMany
	@JoinTable(name = "orderpayments",
	           joinColumns = @JoinColumn(name = "ordnum"),
	           inverseJoinColumns = @JoinColumn(name = "paymentid"))
	@JsonIgnoreProperties(value = "orders")
	private Set<Payment> payments = new HashSet<>();

	public Order() {}

	public Order(
			double ordamount,
			double advanceamount,
			Customer customer,
			String orderdescription
	) {
		this.ordamount        = ordamount;
		this.advanceamount    = advanceamount;
		this.customer         = customer;
		this.orderdescription = orderdescription;
	}

	public long getOrdnum() {
		return ordnum;
	}

	public void setOrdnum(long ordnum) {
		this.ordnum = ordnum;
	}

	public Set<Payment> getPayments() {
		return payments;
	}

	public void setPayments(Set<Payment> payments) {
		this.payments = payments;
	}

	public void setAll(Order in) {
		setOrderdescription(in.getOrderdescription());
		setOrdamount(in.getOrdamount());
		setAdvanceamount(in.getAdvanceamount());

		setCustomer(in.getCustomer());
		//		setPayments(in.getPayments());
	}

	public String getOrderdescription() {
		return orderdescription;
	}

	public double getOrdamount() {
		return ordamount;
	}

	public void setOrdamount(double ordamount) {
		this.ordamount = ordamount;
	}

	public double getAdvanceamount() {
		return advanceamount;
	}

	public void setAdvanceamount(double advanceamount) {
		this.advanceamount = advanceamount;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public void setOrderdescription(String orderdescription) {
		this.orderdescription = orderdescription;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj || super.equals(obj)) { return true; }
		if (obj == null || getClass() != obj.getClass()) { return false; }
		Order that = (Order) obj;
		return ordamount == that.ordamount && advanceamount == that.advanceamount &&
		       orderdescription.equals(that.orderdescription) && customer.equals(that.getCustomer());
	}

	@Override
	public String toString() {
		return "Order{" + "ordnum=" + ordnum + ", ordamount=" + ordamount + ", advanceamount=" + advanceamount +
		       ", orderdescription='" + orderdescription + '\'' + ", customer=" + customer + ", payments=" + payments + '}';
	}

}
