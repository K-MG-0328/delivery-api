package com.github.mingyu.fooddeliveryapi.security;

import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;
import java.util.Base64;

public class jwtTokenProviderTest {

    @Test
    void createSecretKey() {
        SecretKey key = Jwts.SIG.HS256.key().build();
        String base64SecretKey = Base64.getEncoder().encodeToString(key.getEncoded());
        System.out.println("Secret Key: " + base64SecretKey);
    }
}
