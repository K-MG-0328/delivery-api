package com.github.mingyu.fooddeliveryapi.domain.menu.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
public class MenuOption {

    protected MenuOption() {}

    public MenuOption(String menuOptionId, String optionName, Integer price, MenuOptionStatus status) {
        this.menuOptionId = menuOptionId;
        this.optionName = optionName;
        this.price = price;
        this.status = status;
    }

    @Id
    private String menuOptionId; //UUID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id")
    private Menu menu;

    @Column(nullable = false)
    private String optionName;

    @Column(nullable = false)
    private Integer price;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 255)
    private MenuOptionStatus status;

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

    public void addMenu(Menu menu) {
        this.menu = menu;
    }

    public void changeOption(String name, int price, MenuOptionStatus status) {
        this.optionName = name;
        this.price = price;
        this.status = status;
    }
}
