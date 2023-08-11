package com.cbiko.ecommerce.controllers;

import com.cbiko.ecommerce.common.ApiResponse;
import com.cbiko.ecommerce.dto.cart.AddToCartDto;
import com.cbiko.ecommerce.dto.cart.CartDto;
import com.cbiko.ecommerce.exceptions.AuthenticationFailException;
import com.cbiko.ecommerce.exceptions.CartItemNotExistException;
import com.cbiko.ecommerce.exceptions.ProductNotExistException;
import com.cbiko.ecommerce.model.Cart;
import com.cbiko.ecommerce.model.Product;
import com.cbiko.ecommerce.model.User;
import com.cbiko.ecommerce.service.AuthenticationService;
import com.cbiko.ecommerce.service.CartService;
import com.cbiko.ecommerce.service.CategoryService;
import com.cbiko.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    CartService cartService;

    @Autowired
    ProductService productService;

    @Autowired
    AuthenticationService authenticationService;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addToCart(@RequestBody AddToCartDto addToCartDto, @RequestParam("token") String token)
            throws ProductNotExistException, AuthenticationFailException {
        // first authenticate the token
        authenticationService.authenticate(token);

        // get the user
        User user = authenticationService.getUser(token);

        // find the product to add and add item by service
        Product product = productService.getProductById(addToCartDto.getProductId());
        cartService.addToCart(addToCartDto, product, user);

        // return response
        return new ResponseEntity<>(new ApiResponse(true, "Added to cart"), HttpStatus.CREATED);

    }

    @GetMapping("/")
    public ResponseEntity<CartDto> getCartItems(@RequestParam("token") String token) throws AuthenticationFailException {
        // first authenticate the token
        authenticationService.authenticate(token);

        // get the user
        User user = authenticationService.getUser(token);

        // get items in the cart for the user.
        CartDto cartDto = cartService.listCartItems(user);

        return new ResponseEntity<CartDto>(cartDto,HttpStatus.OK);
    }


    // task delete cart item
    @DeleteMapping("/delete/{cartItemId}")
    public ResponseEntity<ApiResponse> deleteCartItem(@PathVariable("cartItemId") int cartItemId,
                                                      @RequestParam("token") String token)
            throws AuthenticationFailException, CartItemNotExistException {
        authenticationService.authenticate(token);
        User user = authenticationService.getUser(token);
        // method to be completed
        cartService.deleteCartItem(cartItemId, user);
        return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Item has been removed"), HttpStatus.OK);
    }

    @PutMapping("/update/{cartId}")
    public ResponseEntity<String> updateCartItem(@PathVariable("cartId") int cartItemId,
                                                 @RequestBody AddToCartDto addToCartDto,
                                                 @RequestParam("token") String token) throws AuthenticationFailException, CartItemNotExistException  {
        try {
            authenticationService.authenticate(token);
            User user = authenticationService.getUser(token);

            // method to be completed
            cartService.updateCart(cartItemId, addToCartDto, user);

            return ResponseEntity.ok("Cart item updated successfully");
        } catch (AuthenticationFailException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
        }
    }

}