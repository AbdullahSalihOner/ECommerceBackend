package com.cbiko.ecommerce.controllers;


import com.cbiko.ecommerce.common.ApiResponse;
import com.cbiko.ecommerce.dto.checkout.CheckoutItemDto;
import com.cbiko.ecommerce.dto.checkout.StripeResponse;
import com.cbiko.ecommerce.exceptions.AuthenticationFailException;
import com.cbiko.ecommerce.exceptions.OrderNotFoundException;
import com.cbiko.ecommerce.model.Order;
import com.cbiko.ecommerce.model.User;
import com.cbiko.ecommerce.repository.UserRepository;
import com.cbiko.ecommerce.service.AuthenticationService;
import com.cbiko.ecommerce.service.OrderService;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    UserRepository userRepository;  // added by me


    // stripe create session API
    @PostMapping("/create-checkout-session")
    public ResponseEntity<StripeResponse> checkoutList(@RequestBody List<CheckoutItemDto> checkoutItemDtoList) throws StripeException {
        // create the stripe session
        Session session = orderService.createSession(checkoutItemDtoList);
        StripeResponse stripeResponse = new StripeResponse(session.getId());
        // send the stripe session id in response
        return new ResponseEntity<StripeResponse>(stripeResponse, HttpStatus.OK);
    }

    // place order after checkout
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> placeOrder(@RequestParam("UserId") int UserId, @RequestParam("sessionId") String sessionId)
            throws AuthenticationFailException {
        // validate token

        // retrieve user
        User user = userRepository.findUserById(UserId);;
        // place the order
        orderService.placeOrder(user, sessionId);
        return new ResponseEntity<>(new ApiResponse(true, "Order has been placed"), HttpStatus.CREATED);
    }

    // get all orders
    @GetMapping("/")
    public ResponseEntity<List<Order>> getAllOrders(@RequestParam("UserId") int UserId) throws AuthenticationFailException {
        // validate token

        // retrieve user
        User user = userRepository.findUserById(UserId);
        // get orders
        List<Order> orderDtoList = orderService.listOrders(user);

        return new ResponseEntity<>(orderDtoList, HttpStatus.OK);
    }

    // get orderitems for an order
    @GetMapping("getById/{id}")
    public ResponseEntity<Object> getOrderById(@PathVariable("id") Integer id, @RequestParam("UserId") int UserId)
            throws AuthenticationFailException, OrderNotFoundException {
        // 1. validate token

        // validate token


        // 2. find user

        // retrieve user
        User user = userRepository.findUserById(UserId);


        // 3. call getOrder method of order service an pass orderId and user

        Order order = orderService.getOrder(id, user);

        // 4. display order in json response

        return new ResponseEntity<>(order, HttpStatus.OK);

    }

    @DeleteMapping("delete/{orderId}")
    public void deleteOrder(@PathVariable Integer orderId, @RequestParam User user) throws OrderNotFoundException {
        orderService.deleteOrder(orderId, user);
    }

}
