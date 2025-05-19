package com.github.mingyu.fooddeliveryapi.entity;

import com.github.mingyu.fooddeliveryapi.enums.MenuStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "menu")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long menuId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "storeId", nullable = false)
    private Store store;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(nullable = false)
    private int price;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MenuStatus status;

    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    @ElementCollection
    @CollectionTable(name = "menu_options", joinColumns = @JoinColumn(name = "menu_id"))
    private List<MenuOption> options = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        createdDate = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        modifiedDate = LocalDateTime.now();
    }

    public void addOption(MenuOption option) {
        options.add(option);
    }
}