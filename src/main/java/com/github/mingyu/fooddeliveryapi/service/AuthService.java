package com.github.mingyu.fooddeliveryapi.service;

import com.github.mingyu.fooddeliveryapi.dto.auth.AuthRequestDto;
import com.github.mingyu.fooddeliveryapi.dto.auth.AuthResponseDto;
import com.github.mingyu.fooddeliveryapi.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthResponseDto login(AuthRequestDto authRequestDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequestDto.getEmail(), authRequestDto.getPassword())
        );

        List<String> roles = authentication.getAuthorities().stream()
                .map(grantedAuthority -> grantedAuthority.getAuthority())
                .collect(Collectors.toList());

        String token = jwtTokenProvider.createToken(authRequestDto.getEmail(), roles);
        return new AuthResponseDto(token, authRequestDto.getEmail());
    }

}
