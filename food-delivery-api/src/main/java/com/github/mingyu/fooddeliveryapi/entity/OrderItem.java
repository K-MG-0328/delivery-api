package com.github.mingyu.fooddeliveryapi.entity;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {

    private Long menuId;
    private int quantity;
    private int price;

    @ElementCollection
    @CollectionTable(name = "order_item_options", joinColumns = @JoinColumn(name = "order_id"))
    private List<OrderItemOption> options = new ArrayList<>();

    public void addOption(OrderItemOption option) {
        options.add(option);
    }
}
