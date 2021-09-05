package com.example.namnh.repository;

import com.example.namnh.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Query(value = "select * from User where user_name = :userName and password = :password", nativeQuery = true)
    Optional<User> findByUserNameAndPassword(String userName, String password);

    @Query(value = "select * from User where user_name = :username", nativeQuery = true)
    User findByUsername(String username);
}
