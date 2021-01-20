package com.myecommerce.orders.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.myecommerce.orders.model.Item;

@Repository
public interface ItemRepository extends CrudRepository<Item, Long> {

}
