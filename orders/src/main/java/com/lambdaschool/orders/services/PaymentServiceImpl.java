package com.lambdaschool.orders.services;


import com.lambdaschool.orders.models.Payment;
import com.lambdaschool.orders.repositories.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;



@Transactional
@Service(value = "paymentservice")
public class PaymentServiceImpl
		implements PaymentService {
	private PaymentRepository paymentRepo;

	@Autowired
	public PaymentServiceImpl(PaymentRepository paymentRepo) {
		this.paymentRepo = paymentRepo;
	}

	@Transactional
	@Override
	public Payment save(Payment payment) {
		return paymentRepo.save(payment);
	}

}
