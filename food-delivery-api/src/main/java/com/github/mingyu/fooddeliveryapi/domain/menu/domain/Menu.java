package com.github.mingyu.fooddeliveryapi.domain.menu.domain;

import com.github.mingyu.fooddeliveryapi.common.util.IdGenerator;
import com.github.mingyu.fooddeliveryapi.domain.menu.application.dto.MenuOptionParam;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    public void updateOptions(List<MenuOptionParam> newOptionParams) {
        // 1. 현재 옵션을 Map으로 변환
        Map<String, MenuOption> currentOptionsById = this.options.stream()
                .filter(o -> o.getMenuOptionId() != null)
                .collect(Collectors.toMap(MenuOption::getMenuOptionId, o -> o));

        // 2. 최종 옵션 목록 생성
        List<MenuOption> finalOptions = new ArrayList<>();

        for (MenuOptionParam param : newOptionParams) {
            if (param.getMenuOptionId() != null && currentOptionsById.containsKey(param.getMenuOptionId())) {
                // 수정 케이스
                MenuOption option = currentOptionsById.get(param.getMenuOptionId());
                option.changeOption(param.getOptionName(), param.getPrice(), param.getStatus());
                finalOptions.add(option);
                currentOptionsById.remove(param.getMenuOptionId());
            } else {
                // 추가 케이스
                MenuOption option = new MenuOption(IdGenerator.uuid(), param.getOptionName(), param.getPrice(), param.getStatus());
                option.addMenu(this);
                finalOptions.add(option);
            }
        }

        // 3. 삭제 케이스: currentOptionsById에 남아 있는 것들
        for (MenuOption deletedOption : currentOptionsById.values()) {
            this.options.remove(deletedOption);
        }

        // 4. 옵션 컬렉션 교체 (추가/수정)
        this.options.clear();
        this.options.addAll(finalOptions);
    }
}