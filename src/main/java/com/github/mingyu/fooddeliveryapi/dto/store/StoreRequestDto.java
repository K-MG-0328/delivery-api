package com.github.mingyu.fooddeliveryapi.dto.store;

import com.github.mingyu.fooddeliveryapi.entity.Menu;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class StoreRequestDto {

    private Long storeId;           // 가게 ID (고유 식별자)
    private String name;            // 가게 이름
    private String category;        // 음식 카테고리 (예: 한식, 중식, 피자 등)
    private String address;         // 가게 주소
    private String phone;           // 가게 전화번호
    private int minDeliveryPrice;   // 최소 주문 금액
    private int deliveryTip;        // 배달 팁
    private int minDeliveryTime;    // 최소 배달 예상 시간 (분 단위)
    private int maxDeliveryTime;    // 최대 배달 예상 시간 (분 단위)
    private double ratings;         // 가게 평점
    private String status;          // 가게 상태 (예: 활성화, 비활성화)
    private LocalDateTime createdDate;  // 생성일
    private LocalDateTime modifiedDate; // 수정일
    private List<Menu> menus = new ArrayList<>(); // 메뉴 목록
    private String deliveryAreas;           // 배달 지역

    private String userAddress;     // 사용자 주소
}
