package com.github.mingyu.fooddeliveryapi.store.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
public class Store {

    protected Store() {}

    // 전체 필드 생성자
    public Store(
            String storeId,
            String name,
            String category,
            String address,
            String phone,
            Integer minDeliveryPrice,
            Integer deliveryTip,
            DeliveryTime deliveryTime,
            Double ratings,
            StoreStatus status,
            String deliveryAreas
    ) {
        this.storeId = storeId;
        this.name = name;
        this.category = category;
        this.address = address;
        this.phone = phone;
        this.minDeliveryPrice = minDeliveryPrice;
        this.deliveryTip = deliveryTip;
        this.deliveryTime = deliveryTime;
        this.ratings = ratings;
        this.status = status;
        this.deliveryAreas = deliveryAreas;
    }

    @Id
    private String storeId;                   // 가게 ID (고유 식별자)

    @Column(nullable = false, length = 255)
    private String name;                    // 가게 이름

    @Column(nullable = false)
    private String category;                // 음식 카테고리 (예: 한식, 중식, 피자 등)

    @Column(nullable = false)
    private String address;                 // 가게 주소

    @Column(nullable = false)
    private String phone;                   // 가게 전화번호

    @Column(nullable = false)
    private Integer minDeliveryPrice;       // 최소 주문 금액

    @Column(nullable = false)
    private Integer deliveryTip;            // 배달 팁

    @Embedded
    private DeliveryTime deliveryTime;      //배달 최대/최소 시간

    @Column(nullable = false)
    private Double ratings;                 // 가게 평점

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StoreStatus status;             // 가게 상태 (예: ACTIVE, INACTIVE..)

    @Column(nullable = false)
    private String deliveryAreas;           // 배달 지역

    private LocalDateTime createdDate;      // 생성일시

    private LocalDateTime modifiedDate;     // 수정일시

    @PrePersist
    protected void onCreate() {
        createdDate = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        modifiedDate = LocalDateTime.now();
    }

    public void update(
            String name,
            String category,
            String address,
            String phone,
            Integer minDeliveryPrice,
            Integer deliveryTip,
            DeliveryTime deliveryTime,
            String deliveryAreas,
            StoreStatus status
    ) {
        this.name = name;
        this.category = category;
        this.address = address;
        this.phone = phone;
        this.minDeliveryPrice = minDeliveryPrice;
        this.deliveryTip = deliveryTip;
        this.deliveryTime = deliveryTime;
        this.deliveryAreas = deliveryAreas;
        this.status = status;
    }

    public void changeStatus(StoreStatus status) {
        this.status = status;
    }
}
