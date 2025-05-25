package com.github.mingyu.fooddeliveryapi.domain.menu.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
public class Menu {

    public Menu(String menuId, String storeId, String name, Integer price, MenuStatus status, List<MenuOption> options) {
        this.menuId = menuId;
        this.storeId = storeId;
        this.name = name;
        this.price = price;
        this.status = status;
        this.options = options;
    }

    @Id
    private String menuId; //UUID

    @Column(nullable = false)
    private String storeId;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(nullable = false)
    private Integer price;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MenuStatus status;

    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MenuOption> options;

    @PrePersist
    protected void onCreate() {
        createdDate = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        modifiedDate = LocalDateTime.now();
    }

    public void addMenuOption(MenuOption menuOption) {
        options.add(menuOption);
        menuOption.addMenu(this);
    }

    public void addMenuOptionList(List<MenuOption> menuOptions) {
        for (MenuOption menuOption : menuOptions) {
            this.addMenuOption(menuOption);
        }
    }
}