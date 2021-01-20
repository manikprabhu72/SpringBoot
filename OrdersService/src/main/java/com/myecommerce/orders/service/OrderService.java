package com.myecommerce.orders.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myecommerce.orders.exceptions.ResourceNotFoundException;
import com.myecommerce.orders.model.Address;
import com.myecommerce.orders.model.DeliveryMethod;
import com.myecommerce.orders.model.Item;
import com.myecommerce.orders.model.Order;
import com.myecommerce.orders.repository.AddressRepository;
import com.myecommerce.orders.repository.OrderRepository;

@Service
public class OrderService {
	
	private OrderRepository orderRepository;
	private DeliveryService deliveryService;
	private AddressRepository addressRepository;
	
	@Autowired
	public OrderService(OrderRepository orderRepository, DeliveryService deliveryService, AddressRepository addressRepository) {		
		this.orderRepository = orderRepository;
		this.deliveryService = deliveryService;
		this.addressRepository = addressRepository;
	}
	
	public Order getOrderById(Long orderId) throws ResourceNotFoundException {
		Order order = orderRepository.findById(orderId)
				.orElseThrow(() -> new ResourceNotFoundException("Order Not Found for this id "));
		return order;
	}

	public List<Order> getAllOrders() {
		List<Order> orderList = new ArrayList<>();
		this.orderRepository.findAll().forEach((order) -> orderList.add(order));
		return orderList;
	}
	
	public Order createOrder(Order order) throws ResourceNotFoundException {
		DeliveryMethod deliveryMethod = this.deliveryService.getDeliveryById(order.getDeliveryMethod().getDeliveryId());
		if(order.getShippingAddress()!=null & order.getShippingAddress().getId()!=0) {
			Address address = addressRepository.findById(order.getShippingAddress().getId()).orElseThrow(() -> new ResourceNotFoundException("Address with id not found"));
			order.setShippingAddress(address);
		}
		System.out.print(order.getDeliveryMethod());
		order.setDeliveryMethod(deliveryMethod);
		order.setCreatedTime(new Date());
		order.setModifiedTime(null);
		order.setStatus("Created");
		order.setSubTotal(getSubTotal(order));
		order.setTax(getTax(order));
		order.setTotal(getTotal(order));
		return this.orderRepository.save(order);
	}
	
	public Double getSubTotal(Order order) {
		double subTot = 0.0;
		for(Item item: order.getItems()) {
			subTot+=item.getPrice()*item.getQuantity();
		}
		return subTot;
	}
	
	public Double getTax(Order order) {
		double tax = 0.0;
		for(Item item: order.getItems()) {
			tax+=item.getPrice()*item.getQuantity()*0.02;
		}
		return tax;
	}
	
	public Double getTotal(Order order) {
		return getSubTotal(order)+getTax(order)+order.getDeliveryMethod().getDeliveryPrice();
	}
	
	public void cancelOrder(Long orderId) throws ResourceNotFoundException {
		Order order = this.orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order Not Found with id "));		
		order.setStatus("CANCELLED");
		this.orderRepository.save(order);
	}

	public void createOrderBulk(List<Order> orders) {
		orders.stream().forEach((order) -> {
			try{
				createOrder(order);
			}
			catch(ResourceNotFoundException exception) {
				System.out.println(exception);
			}
		});
	}
	
	public void cancelOrderBulk(List<Long> orderIds) {
		orderIds.stream().forEach((orderId) -> {
			try {
				cancelOrder(orderId);
			}
			catch(ResourceNotFoundException exception) {
				System.out.println(exception);
			}
		});
	}
	

}
