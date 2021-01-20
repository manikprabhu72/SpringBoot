package com.myecommerce.orders.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.myecommerce.orders.model.DeliveryMethod;

@Repository
public interface DeliveryRepository extends CrudRepository<DeliveryMethod, Long> {

}
