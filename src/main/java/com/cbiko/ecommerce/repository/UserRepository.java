package com.cbiko.ecommerce.repository;

import com.cbiko.ecommerce.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByEmail(String email);

    User deleteUserByEmail(String email);

    User findUserById(Integer id);
}