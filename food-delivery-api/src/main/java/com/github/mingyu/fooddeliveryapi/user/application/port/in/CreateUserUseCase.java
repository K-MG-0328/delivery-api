package com.github.mingyu.fooddeliveryapi.user.application.port.in;

import com.github.mingyu.fooddeliveryapi.user.application.port.in.command.UserCreateCommand;

public interface CreateUserUseCase {
    void createUser(UserCreateCommand command);
}
