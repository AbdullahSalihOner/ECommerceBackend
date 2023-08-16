package com.cbiko.ecommerce.repository;

import com.cbiko.ecommerce.model.Order;
import com.cbiko.ecommerce.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemsRepository extends JpaRepository<OrderItem,Integer> {
    OrderItem findByOrder(Order order);
}
