package com.github.mingyu.fooddeliveryapi.domain.user.application;

import com.github.mingyu.fooddeliveryapi.domain.user.domain.dto.UserRequestDto;
import com.github.mingyu.fooddeliveryapi.domain.user.domain.dto.UserResponseDto;
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
    public UserResponseDto createUser(UserRequestDto userDto) {
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new RuntimeException("이미 존재하는 이메일입니다.");
        }
        User user = userMapper.toEntity(userDto);

        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        user = userRepository.save(user);
        UserResponseDto userResponseDto = userMapper.toDto(user);
        return userResponseDto;
    }

    @Transactional(readOnly = true)
    public UserResponseDto getUser(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));

        UserResponseDto userResponseDto = userMapper.toDto(user);
        return userResponseDto;
    }

    @Transactional(readOnly = true)
    public UserResponseDto getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다.: " + email));
        UserResponseDto userResponseDto = userMapper.toDto(user);
        return userResponseDto;
    }

    @Transactional
    public UserResponseDto updateUser(Long userId, UserRequestDto userDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));

        userMapper.updateFromDto(userDto, user);
        userRepository.save(user);
        UserResponseDto userResponseDto = userMapper.toDto(user);

        return userResponseDto;
    }

    @Transactional
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));

        user.setStatus(UserStatus.DELETED);
        userRepository.save(user);
    }
}
