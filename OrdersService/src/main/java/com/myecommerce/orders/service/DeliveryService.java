package com.myecommerce.orders.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myecommerce.orders.exceptions.ResourceNotFoundException;
import com.myecommerce.orders.model.DeliveryMethod;
import com.myecommerce.orders.repository.DeliveryRepository;

@Service
public class DeliveryService {
	
	private DeliveryRepository deliveryRepository;

	@Autowired
	public DeliveryService(DeliveryRepository deliveryRepository) {		
		this.deliveryRepository = deliveryRepository;
	}
	
	public DeliveryMethod getDeliveryById(long deliveryId) throws ResourceNotFoundException {
		return deliveryRepository.findById(deliveryId).orElseThrow(() -> new ResourceNotFoundException("Delivery Method not found with that id"));
	}

	public DeliveryRepository getDeliveryRepository() {
		return deliveryRepository;
	}

	public void setDeliveryRepository(DeliveryRepository deliveryRepository) {
		this.deliveryRepository = deliveryRepository;
	}
	
	

}
