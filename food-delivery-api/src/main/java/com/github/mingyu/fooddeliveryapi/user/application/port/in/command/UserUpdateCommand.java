package com.github.mingyu.fooddeliveryapi.user.application.port.in.command;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserUpdateCommand {
    private final String userId;
    private final String name;
    private final String email;
    private final String password;
    private final String phone;
    private final String address;
}
