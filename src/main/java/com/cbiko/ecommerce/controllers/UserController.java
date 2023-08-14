package com.cbiko.ecommerce.controllers;

import com.cbiko.ecommerce.dto.user.SignInDto;
import com.cbiko.ecommerce.dto.user.SignInResponseDto;
import com.cbiko.ecommerce.dto.user.SignupDto;
import com.cbiko.ecommerce.exceptions.AuthenticationFailException;
import com.cbiko.ecommerce.exceptions.CustomException;
import com.cbiko.ecommerce.dto.user.SignUpResponseDto;
import com.cbiko.ecommerce.model.User;
import com.cbiko.ecommerce.repository.UserRepository;
import com.cbiko.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/user")
@RestController
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;  // added by me

    @PostMapping("/signup")
    public SignUpResponseDto Signup(@RequestBody SignupDto signupDto) throws CustomException {
        return userService.signUp(signupDto);
    }

    @RequestMapping(value="/confirm-account", method= {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<?> confirmUserAccount(@RequestParam("token")String confirmationToken) {
        return userService.confirmEmail(confirmationToken);
    }

    @PostMapping("/login")
    public SignInResponseDto signIn(@RequestBody SignInDto signInDto) {
        try {
            SignInResponseDto response = userService.signIn(signInDto);
            return response;
        } catch (CustomException customException) {
            customException.printStackTrace();
            return new SignInResponseDto("error", 0,"CustomException");
        } catch (AuthenticationFailException authException) {
            authException.printStackTrace();
            return new SignInResponseDto("error", 0,"AuthenticationFailException");
        } catch (Exception e) {
            e.printStackTrace();
            return new SignInResponseDto("error", 0,"Exception");
        }
    }
    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<?> deleteUserById(@PathVariable Integer userId) {
        try {
            userService.deleteUserById(userId);
            return ResponseEntity.ok("User deleted successfully");
        } catch (CustomException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<String> updateUser(@PathVariable Integer userId, @RequestBody User updatedUser) {
        try {
            ResponseEntity<?> responseEntity = userService.updateUser(userId, updatedUser);
            return ResponseEntity.ok("User updated successfully");
        } catch (CustomException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/add")
    public ResponseEntity<?> addUser(@RequestBody User user) {
        return userService.addUser(user);
    }


    @GetMapping("/")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public User getUserById(@PathVariable int userId) {
        return userRepository.findUserById(userId);

    }
}