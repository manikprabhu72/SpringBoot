package com.myecommerce.orders.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myecommerce.orders.exceptions.ResourceNotFoundException;
import com.myecommerce.orders.kafka.Producer;
import com.myecommerce.orders.model.Order;
import com.myecommerce.orders.service.OrderService;

@RestController
@RequestMapping("/myecommerce/api")
public class OrderController {
	
	private OrderService orderService;
	private Producer kafkaMessageProducer;
	
	@Autowired
	public OrderController(OrderService orderService, Producer kafkaMessageProducer) {		
		this.orderService = orderService;
		this.kafkaMessageProducer = kafkaMessageProducer;
	}
	
	@GetMapping("/orders/{id}")	
	public ResponseEntity<Order> getOrderById(@PathVariable(value="id") Long orderId){
		try{
			Order order = this.orderService.getOrderById(orderId);
			return ResponseEntity.ok().body(order);
		}
		catch(ResourceNotFoundException exception){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
		
	}
	
	@GetMapping("/orders")
	public ResponseEntity<List<Order>>getAllOrders(){
		return ResponseEntity.ok().body(this.orderService.getAllOrders());
	}
	

	@PostMapping("/orders")
	public ResponseEntity<Order> createOrder(@RequestBody Order order){
		Order orderCreated;
		try {
			orderCreated = orderService.createOrder(order);
			return ResponseEntity.ok().body(orderCreated);
		} catch (ResourceNotFoundException e) {		
			return ResponseEntity.badRequest().body(null);
		}
		
	}
	
	@DeleteMapping("/orders/{id}")
	public ResponseEntity<Order> cancelOrder(@PathVariable(value="id") Long orderId) throws ResourceNotFoundException{
		try {
			this.orderService.cancelOrder(orderId);
			return ResponseEntity.ok().body(null);
		}
		catch(ResourceNotFoundException exception) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
	}
	
	@PostMapping("/orders/batch")	
	public void createOrderBatch(@RequestBody List<Order> orders) {
		kafkaMessageProducer.createOrders(orders);
	}
	
	@DeleteMapping("/orders/batch")	
	public void cancelOrderBatch(@RequestBody List<Long> ids) {
		kafkaMessageProducer.cancelOrders(ids);
		
	}

}
