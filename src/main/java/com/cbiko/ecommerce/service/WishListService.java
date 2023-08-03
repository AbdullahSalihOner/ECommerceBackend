package com.cbiko.ecommerce.service;

import com.cbiko.ecommerce.model.User;
import com.cbiko.ecommerce.model.WishList;
import com.cbiko.ecommerce.repository.WishListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishListService {

    @Autowired
    private WishListRepository wishListRepository;

    public void createWishList(WishList wishList){
        wishListRepository.save(wishList);
    }

    public List<WishList> readWishList(User user){
        return wishListRepository.findAllByUserOrderByCreatedDateDesc(user);
    }
}
