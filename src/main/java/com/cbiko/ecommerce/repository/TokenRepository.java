package com.cbiko.ecommerce.repository;

import com.cbiko.ecommerce.model.AuthenticationToken;
import com.cbiko.ecommerce.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<AuthenticationToken, Integer> {
    AuthenticationToken findTokenByUser(User user);
    AuthenticationToken findUserByToken(String token);
    AuthenticationToken findTokenByToken(String token);
}

