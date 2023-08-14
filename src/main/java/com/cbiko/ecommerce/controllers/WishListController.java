package com.cbiko.ecommerce.controllers;

import com.cbiko.ecommerce.common.ApiResponse;
import com.cbiko.ecommerce.dto.product.ProductDto;
import com.cbiko.ecommerce.exceptions.AuthenticationFailException;
import com.cbiko.ecommerce.model.Product;
import com.cbiko.ecommerce.model.User;
import com.cbiko.ecommerce.model.WishList;
import com.cbiko.ecommerce.repository.ProductRepository;
import com.cbiko.ecommerce.repository.UserRepository;
import com.cbiko.ecommerce.service.AuthenticationService;
import com.cbiko.ecommerce.service.WishListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/wishlist")
public class WishListController {
    @Autowired
    WishListService wishListService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    UserRepository userRepository;  // added by me


    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addWishList(@RequestBody ProductDto productDto, @RequestParam("UserId") int UserId) throws AuthenticationFailException {

        User user = userRepository.findUserById(UserId);

        Product product = productRepository.getById(productDto.getId());




        // save wish list
        WishList wishList = new WishList(user, product);
        wishListService.createWishList(wishList);

        return new ResponseEntity(new ApiResponse(true, "Added to wishlist"), HttpStatus.CREATED);
    }

    @GetMapping("/{UserId}")
    public ResponseEntity<List<ProductDto>> getWishList(@RequestParam("UserId") int UserId) throws AuthenticationFailException {
        // first authenticate if the token is valid

        // then fetch the user linked to the token
        User user = userRepository.findUserById(UserId);
        // first retrieve the wishlist items
        List<WishList> wishLists = wishListService.readWishList(user);

        List<ProductDto> products = new ArrayList<>();
        for (WishList wishList : wishLists) {
            // change each product to product DTO
            products.add(new ProductDto(wishList.getProduct()));
        }
        // send the response to user
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @DeleteMapping("delete/{wishListId}")
    public ResponseEntity<ApiResponse> deleteWishList(@PathVariable Integer wishListId,@RequestParam("UserId") int UserId) {
        // first authenticate if the token is valid
        // then fetch the user linked to the token
        User user = userRepository.findUserById(UserId);

        // find the wish list item to remove
        WishList wishList = wishListService.getWishListByUserAndProductId(user.getId(), wishListId);

        if (wishList != null) {
            // delete the wish list item
            wishListService.deleteWishList(wishList.getId());
            return new ResponseEntity<>(new ApiResponse(true, "Removed from wishlist"), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ApiResponse(false, "Item not found in wishlist"), HttpStatus.NOT_FOUND);
        }

    }

}
