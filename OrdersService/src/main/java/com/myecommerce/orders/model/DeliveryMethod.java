package com.myecommerce.orders.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "delivery_method")
public class DeliveryMethod {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "delivery_id")
	private long deliveryId;
	
	@Column(name = "delivery_type")
	private String deliveryType;
	
	@Column(name = "charge")
	private double deliveryPrice;
	
	

	public long getDeliveryId() {
		return deliveryId;
	}

	public void setDeliveryId(long deliveryId) {
		this.deliveryId = deliveryId;
	}

	public String getDeliveryType() {
		return deliveryType;
	}

	public void setDeliveryType(String deliveryType) {
		this.deliveryType = deliveryType;
	}

	public double getDeliveryPrice() {
		return deliveryPrice;
	}

	public void setDeliveryPrice(double deliveryPrice) {
		this.deliveryPrice = deliveryPrice;
	}
	
	

}
