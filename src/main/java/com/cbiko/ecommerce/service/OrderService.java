package com.cbiko.ecommerce.service;

import com.cbiko.ecommerce.dto.cart.CartDto;
import com.cbiko.ecommerce.dto.cart.CartItemDto;
import com.cbiko.ecommerce.dto.order.UserOrderInfo;
import com.cbiko.ecommerce.exceptions.ProductNotExistException;
import com.cbiko.ecommerce.model.*;
import com.cbiko.ecommerce.repository.CartRepository;
import com.cbiko.ecommerce.repository.OrderItemsRepository;
import com.cbiko.ecommerce.repository.OrderRepository;
import com.cbiko.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Transactional
@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    OrderItemsRepository orderItemsRepository;

    @Autowired
    CartService cartService;

    @Autowired
    private ProductService productService; // ProductService'e göre sipariş ürünleri kontrolü yapılabilir

    @Autowired
    CartRepository cartRepository;
    @Transactional
    public void createOrderFromCart(Integer userId) throws ProductNotExistException {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            throw new IllegalArgumentException("Geçersiz kullanıcı ID");
        }

        CartDto cartDto = cartService.listCartItems(user);
        List<CartItemDto> cartItemDtoList = cartDto.getcartItems();
        if (cartItemDtoList.isEmpty()) {
            throw new IllegalArgumentException("Kullanıcının sepeti boş.");
        }

        try {
            // Create order and save it
            Order newOrder = new Order();
            newOrder.setUser(user);
            newOrder.setTotalPrice(cartDto.getTotalCost());
            newOrder.setCreatedDate(new Date());
            orderRepository.save(newOrder);

            for (CartItemDto cartItemDto : cartItemDtoList) {
                OrderItem orderItem = new OrderItem();
                orderItem.setCreatedDate(new Date());
                orderItem.setOrder(newOrder);
                orderItem.setPrice(cartItemDto.getProduct().getPrice());
                orderItem.setQuantity(cartItemDto.getQuantity());
                orderItem.setProduct(cartItemDto.getProduct());
                orderItem.setProductName(cartItemDto.getProduct().getName());


                orderItemsRepository.save(orderItem);
            }

            cartService.deleteUserCartItems(user);
        } catch (Exception e) {
            throw new RuntimeException("Sipariş oluşturulurken bir hata oluştu.", e);
        }
    }

    public List<Order> getUserOrders(User user){

        try {
            return orderRepository.findAllByUserId(user.getId());
        } catch (Exception e) {
            throw new RuntimeException("Kullanıcının siparişleri getirilirken bir hata oluştu.", e);
        }
        

    }

    @Transactional
    public void deleteOrder(Integer orderId) {
        try {
            Order orderToDelete = orderRepository.findById(orderId).orElse(null);
            OrderItem orderItemToDelete = orderItemsRepository.findByOrder(orderToDelete);

            if (orderToDelete == null) {
                throw new IllegalArgumentException("Geçersiz sipariş ID");
            }

            orderItemsRepository.delete(orderItemToDelete);
            orderRepository.delete(orderToDelete);

        } catch (Exception e) {
            throw new RuntimeException("Sipariş silinirken bir hata oluştu.", e);
        }
    }



}

