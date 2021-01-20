package com.myecommerce.orders.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.myecommerce.orders.model.Order;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {

}
