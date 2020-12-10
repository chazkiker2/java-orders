package com.lambdaschool.orders.models;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;



@Entity
@Table(name = "payments")
public class Payment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long paymentid;

	@Column(nullable = false,
	        unique = true)
	private String type;

	@ManyToMany(mappedBy = "payments")
	@JsonIgnoreProperties("payments")
	private Set<Order> orders = new HashSet<>();

	public Payment() {}

	public Payment(
			String type
	) {
		this.type = type;
	}

	public long getPaymentid() {
		return paymentid;
	}

	public void setPaymentid(long paymentid) {
		this.paymentid = paymentid;
	}

	public void setCheckAll(Payment in) {
		if (in.getType() != null) {
			setType(in.getType());
		}
		if (in.getOrders()
		      .size() > 0) {
			setOrders(in.getOrders());
		}
	}

	public String getType() {
		return type;
	}

	public Set<Order> getOrders() {
		return orders;
	}

	public void setOrders(Set<Order> orders) {
		this.orders = orders;
	}

	public void setType(String type) {
		this.type = type;
	}

}
