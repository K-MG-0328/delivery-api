package com.github.mingyu.fooddeliveryapi.domain.user.presentation;

import com.github.mingyu.fooddeliveryapi.domain.user.domain.dto.UserRequestDto;
import com.github.mingyu.fooddeliveryapi.domain.user.domain.dto.UserResponseDto;
import com.github.mingyu.fooddeliveryapi.domain.user.application.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Tag(name = "User API", description = "회원 관련 API (가입, 조회, 수정, 탈퇴)")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "회원 가입", description = "새로운 사용자를 등록합니다.")
    @PostMapping("/user")
    public ResponseEntity<UserResponseDto> createUser(@RequestBody UserRequestDto userDto) {
        UserResponseDto createdUser = userService.createUser(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @Operation(summary = "사용자 ID로 조회", description = "userId에 해당하는 사용자 정보를 반환합니다.")
    @GetMapping("/user/id/{userId}")
    public ResponseEntity<UserResponseDto> getUser(@PathVariable Long userId) {
        UserResponseDto user = userService.getUser(userId);
        return ResponseEntity.ok(user);
    }

    @Operation(summary = "이메일로 사용자 조회", description = "이메일을 기반으로 사용자 정보를 반환합니다.")
    @GetMapping("/user/email/{email}")
    public ResponseEntity<UserResponseDto> getUserByEmail(@PathVariable String email) {
        UserResponseDto user = userService.getUserByEmail(email);
        return ResponseEntity.ok(user);
    }

    @Operation(summary = "회원 정보 수정", description = "사용자의 정보를 수정합니다.")
    @PutMapping("/user/{userId}")
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable Long userId, @RequestBody UserRequestDto userDto) {
        UserResponseDto updatedUser = userService.updateUser(userId, userDto);
        return ResponseEntity.ok(updatedUser);
    }

    @Operation(summary = "회원 탈퇴", description = "사용자를 탈퇴(비활성화) 처리합니다.")
    @PatchMapping("/user/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }
}
