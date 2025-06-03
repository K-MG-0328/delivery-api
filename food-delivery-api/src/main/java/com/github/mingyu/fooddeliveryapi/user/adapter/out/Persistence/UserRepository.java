package com.github.mingyu.fooddeliveryapi.user.adapter.out.Persistence;

import com.github.mingyu.fooddeliveryapi.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}
