package com.github.mingyu.fooddeliveryapi.cart.adapter.out;

import com.github.mingyu.fooddeliveryapi.cart.adapter.out.persistence.CartRepository;
import com.github.mingyu.fooddeliveryapi.cart.application.port.out.CartRepositoryPort;
import com.github.mingyu.fooddeliveryapi.cart.domain.Cart;
import com.github.mingyu.fooddeliveryapi.cart.domain.CartStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CartRepositoryAdapter implements CartRepositoryPort {
    private final CartRepository cartRepository;

    @Override
    public void deleteCart(Cart cart) {
        cartRepository.deleteCart(cart);
    }

    @Override
    public List<Cart> findByUserIdAndStatus(String userId, CartStatus cartStatus) {
        return cartRepository.findByUserIdAndStatus(userId, cartStatus);
    }
}
