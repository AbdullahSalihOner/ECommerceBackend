package com.cbiko.ecommerce.service;

import com.cbiko.ecommerce.exceptions.WishListNotFoundException;
import com.cbiko.ecommerce.model.User;
import com.cbiko.ecommerce.model.WishList;
import com.cbiko.ecommerce.repository.WishListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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



    public void deleteWishList(Integer wishListId) throws WishListNotFoundException {
        Optional<WishList> optionalWishList = wishListRepository.findById(wishListId);

        if (optionalWishList.isEmpty()) {
            throw new WishListNotFoundException("WishList not found");
        }

        WishList wishList = optionalWishList.get();
        wishListRepository.delete(wishList);
    }



    public WishList getWishListByUserAndProductId(Integer userId, Integer wishListId) {
        return wishListRepository.findByUser_IdAndProduct_Id(userId, wishListId);
    }

}
