package com.lambdaschool.orders.repositories;


import com.lambdaschool.orders.models.Payment;
import org.springframework.data.repository.CrudRepository;

import java.util.List;



public interface PaymentRepository
		extends CrudRepository<Payment, Long> {
//	List<Payment> findAllBy()
}
