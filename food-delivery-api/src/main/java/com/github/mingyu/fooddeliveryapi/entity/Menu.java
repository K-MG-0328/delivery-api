package com.github.mingyu.fooddeliveryapi.entity;

import com.github.mingyu.fooddeliveryapi.enums.MenuStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long menuId;

    @Column(nullable = false)
    private Long storeId;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(nullable = false)
    private Integer price;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MenuStatus status;

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