package com.github.mingyu.fooddeliveryapi.domain.store.domain;

import com.github.mingyu.fooddeliveryapi.domain.store.application.dto.StoreParam;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
public class Store {

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

    public void update(StoreParam param) {

        this.compare(param);
        this.name = param.getName();
        this.category = param.getCategory();
        this.address = param.getAddress();
        this.phone = param.getPhone();
        this.minDeliveryPrice = param.getMinDeliveryPrice();
        this.deliveryTip = param.getDeliveryTip();
        this.deliveryTime = param.getDeliveryTime();
        this.ratings = param.getRatings();
        this.status = param.getStatus();
        this.deliveryAreas = param.getDeliveryAreas();
    }

    public void compare(StoreParam param){
        if(!Objects.equals(this.storeId, param.getStoreId())){
            throw new RuntimeException("같은 가게가 아닙니다.");
        }
    }

    public void changeStatus(StoreStatus status) {
        this.status = status;
    }
}
