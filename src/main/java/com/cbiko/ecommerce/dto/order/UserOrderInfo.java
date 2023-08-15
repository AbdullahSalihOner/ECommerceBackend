package com.cbiko.ecommerce.dto.order;

import com.cbiko.ecommerce.model.OrderItem;

import java.util.Date;
import java.util.List;

public class UserOrderInfo {

    private Integer orderId;
    private Date orderDate;
    private List<OrderItem> orderItems;
    private Double totalPrice;

    public UserOrderInfo() {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.orderItems = orderItems;
        this.totalPrice = totalPrice;
    }

    // Getters and setters

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
