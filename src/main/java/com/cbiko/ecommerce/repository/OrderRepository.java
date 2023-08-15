package com.cbiko.ecommerce.repository;

import com.cbiko.ecommerce.model.Order;
import com.cbiko.ecommerce.model.User;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository  extends JpaRepository<Order, Integer> {
    List<Order> findAllByUserOrderByCreatedDateDesc(User user);

    List<Order> findAllByUserId(Integer userId);

}
