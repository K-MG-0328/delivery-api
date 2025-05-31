package com.github.mingyu.fooddeliveryapi.domain.order.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@AllArgsConstructor
public class Order {

    protected Order() {}

    public Order(String orderId, Long userId, StoreInfo storeInfo, OrderStatus status) {
        this.orderId = orderId;
        this.userId = userId;
        this.storeInfo = storeInfo;
        this.status = status;
    }

    @Id
    private String orderId;

    @Column(nullable = false)
    private Long userId;

    @Embedded
    StoreInfo storeInfo;

    @Column(nullable = true, length = 255)
    private PaymentMethod paymentMethod;

    @Column(nullable = false)
    private Integer totalPrice = 0;

    @Column(nullable = true, length = 255)
    private String requests;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 255)
    private OrderStatus status;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items = new ArrayList<>();

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

    public void addItem(OrderItem item) {
        item.addOrder(this);
        items.add(item);
    }

    public void calculateTotalPrice() {
        totalPrice = items.stream().mapToInt(OrderItem::getPrice).sum();
    }

    public void changeStatus(OrderStatus status) {
        this.status = status;
    }

    public void addPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public void addRequests(String requests) {
        this.requests = requests;
    }
}
