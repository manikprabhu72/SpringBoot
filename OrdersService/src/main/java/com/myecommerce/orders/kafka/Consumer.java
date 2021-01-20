package com.myecommerce.orders.kafka;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import com.myecommerce.orders.model.Order;
import com.myecommerce.orders.service.OrderService;

@Service
public class Consumer {
		
	private OrderService orderService;
	
	@Autowired
    public Consumer(OrderService orderService) {		
		this.orderService = orderService;
	}

	@KafkaListener(topics = Producer.CREATE_ORDERS_TOPIC, groupId = "group_id", containerFactory = "createOrderKafkaListenerFactory")
    public void createOrders(List<Order> orders)  {                
		orderService.createOrderBulk(orders);		
    }
    
    @KafkaListener(topics = Producer.CANCEL_ORDERS_TOPIC, groupId = "group_id", containerFactory = "cancelOrderKafkaListenerFactory")
    public void CancelOrders(List<Long> orderIds){        
        orderService.cancelOrderBulk(orderIds);
    }
	
}
