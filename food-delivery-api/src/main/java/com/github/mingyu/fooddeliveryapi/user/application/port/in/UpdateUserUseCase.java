package com.github.mingyu.fooddeliveryapi.user.application.port.in;

import com.github.mingyu.fooddeliveryapi.user.application.port.in.command.UserUpdateCommand;

public interface UpdateUserUseCase {
    void updateUser(UserUpdateCommand command);
}
