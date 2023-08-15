package com.cbiko.ecommerce.service;

import com.cbiko.ecommerce.dto.cart.AddToCartDto;
import com.cbiko.ecommerce.dto.cart.CartDto;
import com.cbiko.ecommerce.dto.cart.CartItemDto;
import com.cbiko.ecommerce.exceptions.CartItemNotExistException;
import com.cbiko.ecommerce.model.Cart;
import com.cbiko.ecommerce.model.Product;
import com.cbiko.ecommerce.model.User;
import com.cbiko.ecommerce.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {
    @Autowired
    CartRepository cartRepository;

    public void addToCart(AddToCartDto addToCartDto, Product product, User user) {
        Cart existingCart = cartRepository.findByProductAndUser(product, user);

        if (existingCart != null) {
            existingCart.setQuantity(existingCart.getQuantity() + addToCartDto.getQuantity());
            cartRepository.save(existingCart);
        } else {
            Cart cart = new Cart(product, addToCartDto.getQuantity(), user);
            cartRepository.save(cart);
        }
    }

    public CartDto listCartItems(User user) {
        // first get all the cart items for user
        List<Cart> cartList = cartRepository.findAllByUserOrderByCreatedDateDesc(user);

        // convert cart to cartItemDto
        List<CartItemDto> cartItems = new ArrayList<>();
        for (Cart cart:cartList){
            CartItemDto cartItemDto = new CartItemDto(cart);
            cartItems.add(cartItemDto);
        }

        // calculate the total price
        double totalCost = 0;
        for (CartItemDto cartItemDto :cartItems){
            totalCost += cartItemDto.getProduct().getPrice() * cartItemDto.getQuantity();
        }

        // return cart DTO
        return new CartDto(cartItems,totalCost);
    }

    public void deleteCartItem(int cartItemId, User user) throws CartItemNotExistException {
        //TODO

        // first check if cartItemId is valid else throw an CartItemNotExistException

        Optional<Cart> optionalCart = cartRepository.findById(cartItemId);

        if (!optionalCart.isPresent()) {
            throw new CartItemNotExistException("cartItemId not valid");
        }

        // next check if the cartItem belongs to the user else throw CartItemNotExistException exception

        Cart cart = optionalCart.get();

        if (cart.getUser() != user) {
            throw new CartItemNotExistException("cart item does not belong to user");
        }

        cartRepository.deleteById(cartItemId);
        // delete the cart item
    }

    public void updateCart(Integer cartId, AddToCartDto newCart, User user) {
        Cart cart = cartRepository.findById(cartId).orElse(null);

        if (cart != null) {
            cart.setQuantity(newCart.getQuantity());
            cartRepository.save(cart);
        }
    }

    public void deleteUserCartItems(User user) {
        cartRepository.deleteByUser(user);
    }
}