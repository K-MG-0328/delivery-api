package com.github.mingyu.fooddeliveryapi.entity;

import com.github.mingyu.fooddeliveryapi.enums.MenuOptionStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MenuOption {

    @Column(name = "optionName", nullable = false)
    private String option;

    @Column(nullable = false)
    private int price;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 255)
    private MenuOptionStatus status;

}
