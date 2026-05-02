package com.github.mingyu.fooddeliveryapi.domain.order.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class OrderItem {

    protected OrderItem() {}

    public OrderItem(String itemId, String menuId, String name, Integer price, Integer quantity) {
        this.itemId = itemId;
        this.menuId = menuId;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    @Id
    private String itemId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @Column(nullable = false)
    private String menuId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false)
    private Integer quantity;

    @ElementCollection
    @CollectionTable(name = "option", joinColumns = @JoinColumn(name = "item_id"))
    private List<OrderItemOption> options = new ArrayList<>();

    public void addOrder(Order order) {
        this.order = order;
    }

    public void addOptions(List<OrderItemOption> options) {
        this.options.addAll(options);
    }

    public int getPrice() {
        int itemPrice = price + options.stream().mapToInt(OrderItemOption::getPrice).sum();
        return itemPrice;
    }
}
