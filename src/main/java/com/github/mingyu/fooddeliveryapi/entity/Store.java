package com.github.mingyu.fooddeliveryapi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "stores")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long storeId;                   // 가게 ID (고유 식별자)

    @Column(nullable = false, length = 255)
    private String name;                    // 가게 이름

    @Column(nullable = false)
    private String category;                // 음식 카테고리 (예: 한식, 중식, 피자 등)

    @Column(nullable = true)
    private String address;                 // 가게 주소

    @Column(nullable = true)
    private String phone;                   // 가게 전화번호

    @Column(nullable = false)
    private int minDeliveryPrice;           // 최소 주문 금액

    @Column(nullable = false)
    private int deliveryTip;                // 배달 팁

    @Column(nullable = false)
    private int minDeliveryTime;            // 최소 배달 예상 시간 (분 단위)

    @Column(nullable = false)
    private int maxDeliveryTime;            // 최대 배달 예상 시간 (분 단위)

    @Column(nullable = false)
    private double ratings;                 // 가게 평점

    @Column(nullable = false)
    private String status;                  // 가게 상태 (예: OPEN, CLOSE)

    @Column(nullable = false)
    private String deliveryAreas;           // 배달 지역

    private LocalDateTime createdDate;      // 생성일시

    private LocalDateTime modifiedDate;     // 수정일시

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Menu> menus = new ArrayList<>(); // 메뉴 목록

    @PrePersist
    protected void onCreate() {
        createdDate = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        modifiedDate = LocalDateTime.now();
    }
}
