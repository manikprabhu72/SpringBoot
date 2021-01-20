package com.myecommerce.orders.kafka;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.myecommerce.orders.model.Order;

@Service
public class Producer {
	
	public static final String CREATE_ORDERS_TOPIC = "create_order";
	public static final String CANCEL_ORDERS_TOPIC = "cancel_order";    
    private KafkaTemplate<String, List<Order>> createOrderKafkaTemplate;       
    private KafkaTemplate<String, List<Long>> cancelOrderKafkaTemplate;
    
    @Autowired
    public Producer(KafkaTemplate<String, List<Order>> createOrderKafkaTemplate,
			KafkaTemplate<String, List<Long>> cancelOrderKafkaTemplate) {
		super();
		this.createOrderKafkaTemplate = createOrderKafkaTemplate;
		this.cancelOrderKafkaTemplate = cancelOrderKafkaTemplate;
	}

	public void createOrders(List<Order> orders) {    	
        this.createOrderKafkaTemplate.send(CREATE_ORDERS_TOPIC, orders);
    }
    
    public void cancelOrders(List<Long> orderIds) {    	
    	this.cancelOrderKafkaTemplate.send(CANCEL_ORDERS_TOPIC, orderIds);
    }

}
