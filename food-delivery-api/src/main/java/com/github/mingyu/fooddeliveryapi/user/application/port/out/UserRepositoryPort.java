package com.github.mingyu.fooddeliveryapi.user.application.port.out;

import com.github.mingyu.fooddeliveryapi.user.domain.User;

public interface UserRepositoryPort {
    User findById(String id);
    boolean existsByEmail(String email);
    void save(User user);
}
