package com.github.mingyu.fooddeliveryapi.cart.domain;

import com.github.mingyu.fooddeliveryapi.cart.domain.exception.InvalidCartException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CartValidator {

    public static void validateCart(Cart cart) {
        validateCart(cart.getCartId(), cart.getUserId(), cart.getStoreId(), cart.getStatus(),  cart.getItems(), cart.getTotalPrice());
    }

    //장바구니 검증
    public static void validateCart(String cartId, String userId, String storeId, CartStatus status, List<CartItem> items, Integer totalPrice) {
        List<String> errors = new ArrayList<>();

        if (cartId == null) errors.add("장바구니 ID가 존재하지 않습니다.");
        if (userId == null) errors.add("유저 ID가 존재하지 않습니다.");
        if (storeId == null) errors.add("가게 ID가 존재하지 않습니다.");
        if (status == null) errors.add("장바구니 상태가 존재하지 않습니다.");

        if (items == null || items.isEmpty()) {
            errors.add("장바구니가 비어있습니다.");
        } else {
            validateItems(items, errors);
        }

        validatePrice(totalPrice, items, errors);

        if (!errors.isEmpty()) {
            throw new InvalidCartException(errors);
        }
    }

    //장바구니 목록 검증
    public static void validateItems(List<CartItem> items, List<String> errors) {
        for (CartItem item : items) {
            if (item.getItemId() == null) errors.add("아이템 ID가 존재하지 않습니다.");
            if (item.getMenuId() == null) errors.add("메뉴 ID가 존재하지 않습니다.");
            if (item.getName() == null) errors.add("아이템 이름이 존재하지 않습니다.");
            if (item.getPrice() == null || item.getPrice() < 0) errors.add("아이템 가격은 0 이상이어야 합니다.");
            if (item.getQuantity() == null || item.getQuantity() < 1) errors.add("수량은 1개 이상이어야 합니다.");

            if (item.getOptions() != null && !item.getOptions().isEmpty()) {
                validateOptions(item.getOptions(), errors);
            }
        }
    }

    //아이템 옵션 목록 검증
    public static void validateOptions(List<CartItemOption> options, List<String> errors) {
        for (CartItemOption option : options) {
            if (option.getItemId() == null) errors.add("옵션의 아이템 ID가 존재하지 않습니다.");
            if (option.getOptionName() == null) errors.add("옵션 이름이 존재하지 않습니다.");
            if (option.getPrice() == null || option.getPrice() < 0) errors.add("옵션 가격은 0 이상이어야 합니다.");
        }
    }

    public static void validatePrice(Integer totalPrice, List<CartItem> items, List<String> errors) {
        int sum = 0;
        for (CartItem item : items) {
            int price = item.getPrice();
            int quantity = item.getQuantity();
            int optionPrice = item.getOptions().stream()
                    .mapToInt(CartItemOption::getPrice)
                    .sum();
            sum += (price + optionPrice) * quantity;
        }

        if (!Objects.equals(totalPrice, sum)) {
            errors.add("총 금액이 일치하지 않습니다. 예상 금액: " + sum + ", 입력 금액: " + totalPrice);
        }
    }
}
