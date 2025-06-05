package com.github.mingyu.fooddeliveryapi.user.application.port.in;

import com.github.mingyu.fooddeliveryapi.user.adapter.in.web.response.UserResponse;

public interface GetUserProfileUseCase {
    UserResponse getUser(String userId);
}
