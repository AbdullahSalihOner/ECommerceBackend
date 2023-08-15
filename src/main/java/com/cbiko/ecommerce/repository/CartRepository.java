package com.cbiko.ecommerce.repository;

import com.cbiko.ecommerce.model.Cart;
import com.cbiko.ecommerce.model.Product;
import com.cbiko.ecommerce.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository  extends JpaRepository<Cart,Integer> {
    List<Cart> findAllByUserOrderByCreatedDateDesc(User user);
    void deleteByUser(User user);
    List<Cart> findAllByUser(User user);

    Cart findByProductAndUser(Product product, User user);
}
