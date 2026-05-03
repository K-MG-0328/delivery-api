package com.github.mingyu.fooddeliveryapi.user.adapter.in.web;

import com.github.mingyu.fooddeliveryapi.common.WebAdapter;
import com.github.mingyu.fooddeliveryapi.user.adapter.in.web.request.UserCreateRequest;
import com.github.mingyu.fooddeliveryapi.user.adapter.in.web.request.UserUpdateRequest;
import com.github.mingyu.fooddeliveryapi.user.adapter.in.web.response.UserResponse;
import com.github.mingyu.fooddeliveryapi.user.application.port.in.command.UserCreateCommand;
import com.github.mingyu.fooddeliveryapi.user.application.port.in.command.UserUpdateCommand;
import com.github.mingyu.fooddeliveryapi.user.application.service.UserService;
import com.github.mingyu.fooddeliveryapi.user.domain.exception.InvalidUserException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@WebAdapter
@Tag(name = "User API", description = "회원 관련 API (가입, 조회, 수정, 탈퇴)")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "회원 가입", description = "새로운 사용자를 등록합니다.")
    @PostMapping("/user")
    public ResponseEntity<Void> createUser(@RequestBody UserCreateRequest request) {
        userService.createUser(new UserCreateCommand(
                request.getName(),
                request.getEmail(),
                request.getPassword(),
                request.getPhone(),
                request.getAddress()
        ));

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "사용자 ID로 조회", description = "userId에 해당하는 사용자 정보를 반환합니다.")
    @GetMapping("/user/{userId}")
    public ResponseEntity<UserResponse> getUser(@PathVariable String userId) {
        UserResponse user = userService.getUser(userId);
        return ResponseEntity.ok(user);
    }

    @Operation(summary = "회원 정보 수정", description = "사용자의 정보를 수정합니다.")
    @PutMapping("/user/{userId}")
    public ResponseEntity<Void> updateUser(@RequestBody UserUpdateRequest request) {
        userService.updateUser(new UserUpdateCommand(
                request.getUserId(),
                request.getName(),
                request.getEmail(),
                request.getPassword(),
                request.getPhone(),
                request.getAddress()
        ));
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "회원 탈퇴", description = "사용자를 탈퇴(비활성화) 처리합니다.")
    @PatchMapping("/user/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable String userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(InvalidUserException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidUser(InvalidUserException e) {
        return ResponseEntity.badRequest().body(Map.of(
                "message", e.getMessage(),
                "errors", e.getErrors()
        ));
    }
}
