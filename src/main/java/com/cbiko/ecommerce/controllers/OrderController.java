package com.cbiko.ecommerce.controllers;


import com.cbiko.ecommerce.dto.order.UserOrderInfo;
import com.cbiko.ecommerce.exceptions.ProductNotExistException;
import com.cbiko.ecommerce.model.Order;
import com.cbiko.ecommerce.model.User;
import com.cbiko.ecommerce.repository.UserRepository;
import com.cbiko.ecommerce.service.CartService;
import com.cbiko.ecommerce.service.OrderService;
import com.cbiko.ecommerce.service.ProductService;
import com.cbiko.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/order")

public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private CartService cartService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService; // Kullanıcı servisinize göre güncelleyebilirsiniz

    @Autowired
    private ProductService productService; // Ürün servisinize göre güncelleyebilirsiniz

    @PostMapping("/createFromCart")
    public ResponseEntity<String> createOrderFromCart(@RequestParam("userId") Integer userId) {
        try {
            orderService.createOrderFromCart(userId);
            return ResponseEntity.ok("Sipariş başarıyla oluşturuldu.");
        } catch (ProductNotExistException e) {
            return ResponseEntity.badRequest().body("Ürün bulunamadı.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Order>> getUserOrders(@PathVariable Integer userId) {
        User user = userRepository.findById(userId).orElse(null);
        List<Order> orderDtoList = orderService.getUserOrders(user);

        return new ResponseEntity<>(orderDtoList, HttpStatus.OK);
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<String> deleteOrder(@PathVariable Integer orderId) {
        try {
            orderService.deleteOrder(orderId);
            return ResponseEntity.ok("Sipariş başarıyla silindi.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Sipariş silinirken bir hata oluştu: " + e.getMessage());
        }
    }


}

