package com.github.mingyu.fooddeliveryapi.domain.cart.domain;

import com.github.mingyu.fooddeliveryapi.common.util.IdCreator;
import com.github.mingyu.fooddeliveryapi.domain.cart.application.dto.*;

import java.util.ArrayList;
import java.util.List;

public class CartFactory {

    private CartFactory() {}

    public static Cart createCart(CartCommand command) {

        Cart cart = new Cart();

        if (command instanceof SingleCartParam addParam) {
            List<CartItemParam> items = new ArrayList<>();
            items.add(addParam.getItem());
            cart = createCart(addParam.getUserId(), addParam.getStoreId(), items);
        }

        if (command instanceof CartParam param) {
            cart = createCart(param.getUserId(), param.getStoreId(), param.getItems());
        }

        return cart;
    }

    public static Cart createCart(Long userId, String storeId, List<CartItemParam> items) {

        String cartId = IdCreator.randomUuid();
        Cart cart = new Cart(cartId, userId, storeId, CartStatus.ACTIVE);

        if(items.isEmpty()){
           throw new RuntimeException("장바구니가 비어있습니다.");
        }

        List<CartItem> cartItems = createCartItems(items);
        for (CartItem cartItem : cartItems) {
            cart.addItem(cartItem);
        }

        return cart;
    }

    public static CartItem createCartItem(CartItemParam item) {
        String itemId = IdCreator.randomUuid();
        CartItem cartItem = new CartItem(itemId, item.getMenuId(), item.getName(), item.getPrice(), item.getQuantity());
        List<CartItemOption> cartItemOptions = createCartItemOptions(itemId, item.getOptions());
        cartItem.addOptions(cartItemOptions);
        return cartItem;
    }

    public static List<CartItem> createCartItems(List<CartItemParam> items) {
        String itemId = IdCreator.randomUuid();
        List<CartItem> cartItems = new ArrayList<>();
        for (CartItemParam param : items) {
            CartItem cartItem = new CartItem(itemId, param.getMenuId(), param.getName(), param.getPrice(), param.getQuantity());
            List<CartItemOption> cartItemOptions = createCartItemOptions(itemId, param.getOptions());
            cartItem.addOptions(cartItemOptions);
            cartItems.add(cartItem);
        }
        return cartItems;
    }

    public static List<CartItemOption> createCartItemOptions(String itemId, List<CartItemOptionParam> options) {
        List<CartItemOption> cartItemOptions = new ArrayList<>();
        for (CartItemOptionParam param : options) {
            CartItemOption cartItemOption = new CartItemOption(itemId, param.getOptionName(), param.getPrice());
            cartItemOptions.add(cartItemOption);
        }
        return cartItemOptions;
    }
}
