package com.cbiko.ecommerce.repository;

import com.cbiko.ecommerce.model.Category;
import com.cbiko.ecommerce.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findByCategory(Category category);

}
