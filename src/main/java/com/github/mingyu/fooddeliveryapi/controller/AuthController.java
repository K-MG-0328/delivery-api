package com.github.mingyu.fooddeliveryapi.controller;

import com.github.mingyu.fooddeliveryapi.dto.auth.AuthRequestDto;
import com.github.mingyu.fooddeliveryapi.dto.auth.AuthResponseDto;
import com.github.mingyu.fooddeliveryapi.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    // 로그인 처리
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody AuthRequestDto authRequestDto) {
        AuthResponseDto response = authService.login(authRequestDto);
        return ResponseEntity.ok(response);
    }


}
