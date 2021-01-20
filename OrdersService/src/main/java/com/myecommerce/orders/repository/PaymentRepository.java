package com.myecommerce.orders.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.myecommerce.orders.model.Payment;

@Repository
public interface PaymentRepository extends CrudRepository<Payment, Long> {

}
