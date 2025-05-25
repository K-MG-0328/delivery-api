package com.github.mingyu.fooddeliveryapi.domain.user.application;

import com.github.mingyu.fooddeliveryapi.domain.user.presentation.dto.UserRequest;
import com.github.mingyu.fooddeliveryapi.domain.user.presentation.dto.UserResponse;
import com.github.mingyu.fooddeliveryapi.domain.user.domain.User;
import com.github.mingyu.fooddeliveryapi.domain.user.domain.UserStatus;
import com.github.mingyu.fooddeliveryapi.domain.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserResponse createUser(UserRequest userDto) {
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new RuntimeException("이미 존재하는 이메일입니다.");
        }
        User user = userMapper.toEntity(userDto);

        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        user = userRepository.save(user);
        UserResponse userResponse = userMapper.toDto(user);
        return userResponse;
    }

    @Transactional(readOnly = true)
    public UserResponse getUser(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));

        UserResponse userResponse = userMapper.toDto(user);
        return userResponse;
    }

    @Transactional(readOnly = true)
    public UserResponse getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다.: " + email));
        UserResponse userResponse = userMapper.toDto(user);
        return userResponse;
    }

    @Transactional
    public UserResponse updateUser(Long userId, UserRequest userDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));

        userMapper.updateFromDto(userDto, user);
        userRepository.save(user);
        UserResponse userResponse = userMapper.toDto(user);

        return userResponse;
    }

    @Transactional
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));

        user.setStatus(UserStatus.DELETED);
        userRepository.save(user);
    }
}
