package com.cbiko.ecommerce.service;

import com.cbiko.ecommerce.config.MessageStrings;
import com.cbiko.ecommerce.dto.user.SignInDto;
import com.cbiko.ecommerce.dto.user.SignInResponseDto;
import com.cbiko.ecommerce.dto.user.SignUpResponseDto;
import com.cbiko.ecommerce.dto.user.SignupDto;
import com.cbiko.ecommerce.exceptions.AuthenticationFailException;
import com.cbiko.ecommerce.exceptions.CustomException;
import com.cbiko.ecommerce.model.AuthenticationToken;
import com.cbiko.ecommerce.model.User;
import com.cbiko.ecommerce.repository.TokenRepository;
import com.cbiko.ecommerce.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    EmailService emailService;

    @Autowired
    TokenRepository comfirmationTokenRepository;

    Logger logger = LoggerFactory.getLogger(UserService.class);

   /* public ResponseEntity<?> signUp(User user)  throws CustomException {
        // Check to see if the current email address has already been registered.
        if (Objects.nonNull(userRepository.findByEmail(user.getEmail()))) {
            // If the email address has been registered then throw an exception.
            throw new CustomException("User already exists");
        }
        // first encrypt the password
        String encryptedPassword = user.getPassword();
        try {
            encryptedPassword = hashPassword(user.getPassword());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            logger.error("hashing password failed {}", e.getMessage());
        }

        User currentUser = new User(user.getFirstName(), user.getLastName(), user.getEmail(), encryptedPassword ,user.getRole());

            // save the User
            userRepository.save(currentUser);

            /////////////////////////// Email ekleme kısı düzenle sıkıntı var burda

            AuthenticationToken authToken = new AuthenticationToken(currentUser);
            comfirmationTokenRepository.save(authToken);

            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(user.getEmail());
            mailMessage.setSubject("Complete Registration!");
            mailMessage.setText("To confirm your account, please click here : "
                    +"http://localhost:8080/confirm-account?token="+authToken.getToken());
            emailService.sendEmail(mailMessage);

            System.out.println("Confirmation Token: " + authToken.getToken());

            return ResponseEntity.ok("Verify email by the link sent on your email address");



    }*/
   public SignUpResponseDto signUp(SignupDto signupDto) throws CustomException {
       // Check to see if the current email address has already been registered.
       if (Objects.nonNull(userRepository.findByEmail(signupDto.getEmail()))) {
           // If the email address has been registered then throw an exception.
           throw new CustomException("User already exists");
       }

       // Check if the email address is in a valid format
       String emailPattern = "^[A-Za-z0-9+_.-]+@(.+)$";
       if (!signupDto.getEmail().matches(emailPattern)) {
           throw new CustomException("Invalid email format");
       }

       // first encrypt the password
       String encryptedPassword = signupDto.getPassword();
       try {
           encryptedPassword = hashPassword(signupDto.getPassword());
       } catch (NoSuchAlgorithmException e) {
           e.printStackTrace();
           logger.error("hashing password failed {}", e.getMessage());
       }

       User user = new User(signupDto.getFirstName(), signupDto.getLastName(), signupDto.getEmail(), encryptedPassword);
       try {
           // save the User
           userRepository.save(user);
           // generate token for user
           final AuthenticationToken authenticationToken = new AuthenticationToken(user);
           // save token in database
           authenticationService.saveConfirmationToken(authenticationToken);
           // success in creating
           return new SignUpResponseDto("success", "user created successfully");
       } catch (Exception e) {
           // handle signup error
           throw new CustomException(e.getMessage());
       }
   }

    String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes());
        byte[] digest = md.digest();
        String myHash = DatatypeConverter
                .printHexBinary(digest).toUpperCase();
        return myHash;
    }

    public SignInResponseDto signIn(SignInDto signInDto) throws AuthenticationFailException, CustomException {
        // first find User by email
        User user = userRepository.findByEmail(signInDto.getEmail());

        if(!Objects.nonNull(user)){
            throw new AuthenticationFailException("user not present");
        }
        try {
            // check if password is right
            if (!user.getPassword().equals(hashPassword(signInDto.getPassword()))){
                // passwords do not match
                throw  new AuthenticationFailException(MessageStrings.WRONG_PASSWORD);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            logger.error("hashing password failed {}", e.getMessage());
            throw new CustomException(e.getMessage());
        }



        return new SignInResponseDto ("success", user.getId());
    }


    public ResponseEntity<?> confirmEmail(String confirmationToken) {
        AuthenticationToken token = comfirmationTokenRepository.findTokenByToken(confirmationToken);
        User user = userRepository.findByEmail(token.getUser().getEmail());
        if(token != null)
        {

            userRepository.save(user);
            return ResponseEntity.ok("Email verified successfully!");
        }
        userRepository.delete(user);
        comfirmationTokenRepository.delete(token);
        return ResponseEntity.badRequest().body("Error: Couldn't verify email");

    }


    public ResponseEntity<?> deleteUserById(Integer userId) throws CustomException {
        // Check if the user exists in the repository
        User existingUser = userRepository.findById(userId).orElse(null);
        AuthenticationToken token = comfirmationTokenRepository.findTokenByUser(existingUser);
        if (existingUser == null) {
            throw new CustomException("User not found");
        }

        // Delete the user
        comfirmationTokenRepository.delete(token);
        userRepository.delete(existingUser);

        return ResponseEntity.ok("User deleted successfully");
    }

    public ResponseEntity<?> updateUser(Integer userId, User updatedUser) throws CustomException {
        // Check if the user exists in the repository
        User existingUser = userRepository.findById(userId).orElse(null);
        if (existingUser == null) {
            throw new CustomException("User not found");
        }

        // Update the user's information with the new data
        existingUser.setFirstName(updatedUser.getFirstName());
        existingUser.setLastName(updatedUser.getLastName());
        existingUser.setEmail(updatedUser.getEmail());
        // ... diğer alanları da güncelleyebilirsiniz

        // Save the updated user
        userRepository.save(existingUser);

        return ResponseEntity.ok("User updated successfully");
    }

    public ResponseEntity<?> addUser(User user){

        userRepository.save(user);
        return ResponseEntity.ok("User added successfully");
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(int userId) {
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isPresent()) {
            return userOptional.get();
        } else {
            // Kullanıcı bulunamadı, isteğe bağlı olarak hata işleme yapabilirsiniz.
            throw new RuntimeException("Kullanıcı bulunamadı: " + userId);
        }
    }



}
