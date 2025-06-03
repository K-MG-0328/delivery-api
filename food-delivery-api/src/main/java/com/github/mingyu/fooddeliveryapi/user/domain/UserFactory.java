package com.github.mingyu.fooddeliveryapi.user.domain;

import com.github.mingyu.fooddeliveryapi.common.util.IdCreator;
import com.github.mingyu.fooddeliveryapi.user.application.port.in.command.UserCreateCommand;

public class UserFactory {

    private UserFactory() {}

    public static User create(UserCreateCommand command, EncodedPassword encodedPassword) {
        return create(
                command.getName(),
                command.getEmail(),
                encodedPassword,
                command.getPhone(),
                command.getAddress()
        );
    }

    public static User create(String name, String email, EncodedPassword password, String phone, String currentAddress) {
        String userId = IdCreator.nameUuid(email);
        UserInfo userInfo = new UserInfo(name, phone, currentAddress);

        return new User(userId, password, email, userInfo, UserRole.USER, UserStatus.ACTIVE);
    }
}
