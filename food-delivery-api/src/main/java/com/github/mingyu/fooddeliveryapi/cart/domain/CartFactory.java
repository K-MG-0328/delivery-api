package com.github.mingyu.fooddeliveryapi.cart.domain;

import com.github.mingyu.fooddeliveryapi.cart.application.port.in.command.AddItemCommand;
import com.github.mingyu.fooddeliveryapi.cart.domain.vo.Item;
import com.github.mingyu.fooddeliveryapi.cart.domain.vo.ItemOption;
import com.github.mingyu.fooddeliveryapi.common.util.IdCreator;

import java.util.ArrayList;
import java.util.List;

public class CartFactory {

    private CartFactory() {}

    public static Cart createCart(AddItemCommand command) {
        return createCart(command.getUserId(), command.getStoreId(), command.getItem());
    }

    public static Cart createCart(String userId, String storeId, Item item) {
        String cartId = IdCreator.randomUuid();
        Cart cart = new Cart(cartId, userId, storeId, CartStatus.ACTIVE);
        cart.addItem(createCartItem(item));

        return cart;
    }

    public static CartItem createCartItem(Item item) {
        String itemId = IdCreator.randomUuid();
        CartItem cartItem = new CartItem(itemId, item.getMenuId(), item.getName(), item.getPrice(), item.getQuantity());
        cartItem.addOptions(createCartItemOptions(itemId, item.getOptions()));
        return cartItem;
    }

    public static List<CartItemOption> createCartItemOptions(String itemId, List<ItemOption> options) {
        List<CartItemOption> cartItemOptions = new ArrayList<>();
        for (ItemOption option : options) {
            CartItemOption cartItemOption = new CartItemOption(itemId, option.getOptionName(), option.getPrice());
            cartItemOptions.add(cartItemOption);
        }
        return cartItemOptions;
    }
}
