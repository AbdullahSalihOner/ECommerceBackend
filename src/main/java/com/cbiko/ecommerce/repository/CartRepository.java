package com.cbiko.ecommerce.repository;

import com.cbiko.ecommerce.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository  extends JpaRepository<Cart,Integer> {

}
