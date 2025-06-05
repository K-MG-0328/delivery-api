package com.github.mingyu.fooddeliveryapi.user.application.service;

import com.github.mingyu.fooddeliveryapi.common.UseCase;
import com.github.mingyu.fooddeliveryapi.user.adapter.in.web.response.UserResponse;
import com.github.mingyu.fooddeliveryapi.user.application.port.in.CreateUserUseCase;
import com.github.mingyu.fooddeliveryapi.user.application.port.in.DeleteUserUseCase;
import com.github.mingyu.fooddeliveryapi.user.application.port.in.GetUserProfileUseCase;
import com.github.mingyu.fooddeliveryapi.user.application.port.in.UpdateUserUseCase;
import com.github.mingyu.fooddeliveryapi.user.application.port.in.command.UserCreateCommand;
import com.github.mingyu.fooddeliveryapi.user.application.port.in.command.UserUpdateCommand;
import com.github.mingyu.fooddeliveryapi.user.application.port.out.UserRepositoryPort;
import com.github.mingyu.fooddeliveryapi.user.domain.*;
import com.github.mingyu.fooddeliveryapi.user.domain.exception.InactiveUserException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
public class UserService
        implements CreateUserUseCase, UpdateUserUseCase, DeleteUserUseCase, GetUserProfileUseCase {

    private final UserRepositoryPort userRepositoryPort;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void createUser(UserCreateCommand command) {
        if (userRepositoryPort.existsByEmail(command.getEmail())) {
            throw new RuntimeException("이미 존재하는 이메일입니다.");
        }

        EncodedPassword encodedPassword = EncodedPassword.fromEncoded(passwordEncoder.encode(command.getPassword()));
        User user = UserFactory.create(command, encodedPassword);

        //검증로직
        UserValidator.validate(user);
        userRepositoryPort.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse getUser(String userId) {

        User user = userRepositoryPort.findById(userId);

        if(user.getStatus() != UserStatus.ACTIVE) {
            throw new InactiveUserException();
        }

        return new UserResponse(
            user.getUserInfo().getName(),
                user.getEmail(),
                user.getUserInfo().getPhone(),
                user.getRole(),
                user.getUserInfo().getAddress()
        );
    }

    @Override
    @Transactional
    public void updateUser(UserUpdateCommand command) {
        User user = userRepositoryPort.findById(command.getUserId());

        EncodedPassword newPassword = EncodedPassword.fromEncoded(passwordEncoder.encode(command.getPassword()));
        user.changePassword(newPassword);
        user.getUserInfo().changePhone(command.getPhone());
        user.getUserInfo().changeAddress(command.getAddress());

        userRepositoryPort.save(user);
    }

    @Override
    @Transactional
    public void deleteUser(String userId) {
        User user = userRepositoryPort.findById(userId);
        user.changeStatus(UserStatus.INACTIVE);
        userRepositoryPort.save(user);
    }
}
