package com.myecommerce.orders.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.myecommerce.orders.model.Address;

@Repository
public interface AddressRepository extends CrudRepository<Address, Long> {

}
