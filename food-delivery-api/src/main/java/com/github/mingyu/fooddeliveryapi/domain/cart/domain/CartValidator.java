package com.github.mingyu.fooddeliveryapi.domain.cart.domain;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class CartValidator {

    public static void validateCart(Cart cart) {
        validateCart(cart.getCartId(), cart.getUserId(), cart.getStoreId(), cart.getStatus(),  cart.getItems(), cart.getTotalPrice());
    }

    //장바구니 검증
    public static void validateCart(String cartId, Long userId, String storeId, CartStatus status, List<CartItem> items, Integer totalPrice) {
        // 메뉴 id 검증
        if (cartId == null) {
            throw new IllegalArgumentException("장바구니 id가 존재하지않습니다.");
        }
        // 유저 id 검증
        if (userId == null) {
            throw new IllegalArgumentException("유저 아이디가 존재하지않습니다.");
        }
        // 가게 id 검증
        if (storeId == null) {
            throw new IllegalArgumentException("가게 id가 존재하지않습니다.");
        }
        // 장바구니 상태 검증
        if (status == null) {
            throw new IllegalArgumentException("장바구니 상태가 존재하지않습니다.");
        }

        //아이템 검증
        if(items.isEmpty()) {
            throw new RuntimeException("장바구니가 비어있습니다.");
        }
        validateItems(items);

        //총 가격 검증
        validatePrice(totalPrice, items);
    }

    //장바구니 목록 검증
    public static void validateItems(List<CartItem> items){

        for (CartItem item : items) {

            String itemId = item.getItemId();
            String menuId = item.getMenuId();
            String name = item.getName();
            Integer price = item.getPrice();
            Integer quantity = item.getQuantity();


            if (itemId == null) {
                throw new IllegalArgumentException("아이템 id가 존재하지않습니다.");
            }

            if (menuId == null) {
                throw new IllegalArgumentException("메뉴 id가 존재하지않습니다.");
            }

            if (name == null) {
                throw new IllegalArgumentException("아이템 명이 존재하지않습니다.");
            }

            if (price == null) {
                throw new IllegalArgumentException("가격은 0 이상이어야 합니다. ");
            }

            if (quantity == null) {
                throw new IllegalArgumentException("개수는 1개 이상이어야 합니다. ");
            }

            if(!item.getOptions().isEmpty()) {
                validateOptions(item.getOptions());
            }
        }
    }

    //아이템 옵션 목록 검증
    public static void validateOptions(List<CartItemOption> options){

        for(CartItemOption option : options) {

            String itemId = option.getItemId();
            String optionName = option.getOptionName();
            Integer price = option.getPrice();


            if (itemId == null) {
                throw new IllegalArgumentException("아이템 id가 존재하지않습니다.");
            }

            if (optionName == null) {
                throw new IllegalArgumentException("옵션 명이 존재하지않습니다.");
            }

            if (price == null) {
                throw new IllegalArgumentException("옵션 가격은 0 이상이어야 합니다.");
            }
        }
    }

    public static void validatePrice(Integer totalPrice, List<CartItem> items){

       int sum = 0;
       for (CartItem item : items) {
           int price = item.getPrice();
           int quantity = item.getQuantity();
           int optionPrice = 0;
           if(!item.getOptions().isEmpty()) {
               for(CartItemOption option : item.getOptions()) {
                   optionPrice += option.getPrice();
               }
           }
           sum+=(price+optionPrice)*quantity;
       }

       if(totalPrice != sum) {
           throw new RuntimeException("금액이 일치하지 않습니다.");
       }
    }
}
