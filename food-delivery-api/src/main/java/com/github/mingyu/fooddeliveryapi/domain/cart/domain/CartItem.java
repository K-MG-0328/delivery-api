package com.github.mingyu.fooddeliveryapi.domain.cart.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class CartItem {

    protected CartItem() {}

    public CartItem(String itemId, String menuId, String name, Integer price, Integer quantity) {
        this.itemId = itemId;
        this.menuId = menuId;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    @Id
    private String itemId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @Column(nullable = false)
    private String menuId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false)
    private Integer quantity;

    @ElementCollection
    @CollectionTable(name = "option", joinColumns = @JoinColumn(name = "cart_item_id"))
    private List<CartItemOption> options = new ArrayList<>();

    public void addCart(Cart cart) {
        this.cart = cart;
    }

    public void addOptions(List<CartItemOption> options) {
        this.options.addAll(options);
    }

    public void changeOptions(List<CartItemOption> options) {
        this.options.clear();
        this.options.addAll(options);
    }

    public void changeQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        int itemPrice = price + options.stream().mapToInt(CartItemOption::getPrice).sum();
        return itemPrice;
    }
}