package com.cbiko.ecommerce.repository;

import com.cbiko.ecommerce.model.User;
import com.cbiko.ecommerce.model.WishList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishListRepository  extends JpaRepository<WishList,Integer> {
    List<WishList> findAllByUserOrderByCreatedDateDesc(User user);

    WishList findByUser_IdAndProduct_Id(Integer userId, Integer wishListId);
}
