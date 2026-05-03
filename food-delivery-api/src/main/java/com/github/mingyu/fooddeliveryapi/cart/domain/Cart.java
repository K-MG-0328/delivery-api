package com.github.mingyu.fooddeliveryapi.cart.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
public class Cart {

    protected Cart() {}

    public Cart(String cartId, String userId, String storeId, CartStatus status) {
        this.cartId = cartId;
        this.userId = userId;
        this.storeId = storeId;
        this.status = status;
        this.totalPrice = 0;
    }

    @Id
    private String cartId;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private String storeId;

    @Column(nullable = false)
    private CartStatus status;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> items = new ArrayList<>();

    @Column(nullable = false)
    private Integer totalPrice;

    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    @PrePersist
    protected void onCreate() {
        calculateTotalPrice();
        createdDate = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        calculateTotalPrice();
        modifiedDate = LocalDateTime.now();
    }

    public void addItem(CartItem item) {
        item.addCart(this);
        items.add(item);
    }

    public void calculateTotalPrice() {
        totalPrice = items.stream().mapToInt(CartItem::getPrice).sum();
    }

    public void removeItem(CartItem item) {
        items.remove(item);
    }

    public void changeStatus(CartStatus status) {
        this.status = status;
    }
}