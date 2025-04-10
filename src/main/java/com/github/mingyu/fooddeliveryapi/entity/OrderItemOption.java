package com.github.mingyu.fooddeliveryapi.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class OrderItemOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderItemOptionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orderItemId")
    private OrderItem orderItem;

    private String optionName;

    private int price;

    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    @PrePersist
    protected void onCreate() {
        createdDate = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        modifiedDate = LocalDateTime.now();
    }

}
