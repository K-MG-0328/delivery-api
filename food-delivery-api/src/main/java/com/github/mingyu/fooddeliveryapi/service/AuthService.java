package com.github.mingyu.fooddeliveryapi.service;

import com.github.mingyu.fooddeliveryapi.dto.auth.AuthRequestDto;
import com.github.mingyu.fooddeliveryapi.dto.auth.AuthResponseDto;
import com.github.mingyu.fooddeliveryapi.entity.User;
import com.github.mingyu.fooddeliveryapi.repository.UserRepository;
import com.github.mingyu.fooddeliveryapi.security.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
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

        User user = userRepository.findByEmail(authRequestDto.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("유저 없음"));
        Long userId = user.getUserId();

        return new AuthResponseDto(token, authRequestDto.getEmail(), userId);
    }

    public void logout(HttpServletRequest request) {
        String token = jwtTokenProvider.resolveToken(request);
        if (token != null) {
            jwtTokenProvider.invalidateToken(token);
        }
    }

}
