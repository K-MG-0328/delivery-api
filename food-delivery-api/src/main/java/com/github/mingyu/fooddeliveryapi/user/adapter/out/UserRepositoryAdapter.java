package com.github.mingyu.fooddeliveryapi.user.adapter.out;

import com.github.mingyu.fooddeliveryapi.common.PersistentAdapter;
import com.github.mingyu.fooddeliveryapi.user.adapter.out.Persistence.UserRepository;
import com.github.mingyu.fooddeliveryapi.user.application.port.out.UserRepositoryPort;
import com.github.mingyu.fooddeliveryapi.user.domain.User;
import lombok.RequiredArgsConstructor;

@PersistentAdapter
@RequiredArgsConstructor
public class UserRepositoryAdapter implements UserRepositoryPort {

    private final UserRepository userRepository;

    @Override
    public User findById(String id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }
}
