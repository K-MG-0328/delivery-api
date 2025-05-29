package com.github.mingyu.fooddeliveryapi.domain.store.domain;

public class StoreValidator {
    public static void validate(
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
        // 이름
        if (name == null || name.trim().isEmpty() || name.length() > 255) {
            throw new IllegalArgumentException("가게 이름은 필수이며 1~255자 이내여야 합니다.");
        }
        // 카테고리
        if (category == null || category.trim().isEmpty()) {
            throw new IllegalArgumentException("카테고리는 필수입니다.");
        }
        // 주소
        if (address == null || address.trim().isEmpty()) {
            throw new IllegalArgumentException("주소는 필수입니다.");
        }
        // 전화번호
        if (phone == null || phone.trim().isEmpty() || !phone.matches("^\\d{2,4}-\\d{3,4}-\\d{4}$")) {
            throw new IllegalArgumentException("전화번호 형식이 올바르지 않습니다. 예: 02-1234-5678");
        }
        // 최소 주문 금액
        if (minDeliveryPrice == null || minDeliveryPrice < 0) {
            throw new IllegalArgumentException("최소 주문 금액은 0 이상이어야 합니다.");
        }
        // 배달 팁
        if (deliveryTip == null || deliveryTip < 0) {
            throw new IllegalArgumentException("배달 팁은 0 이상이어야 합니다.");
        }
        // 배달 시간
        if (deliveryTime == null) {
            throw new IllegalArgumentException("배달 시간 정보는 필수입니다.");
        } else {
            if (deliveryTime.getMinMinutes() == null || deliveryTime.getMinMinutes() < 0 ||
                    deliveryTime.getMaxMinutes() == null || deliveryTime.getMaxMinutes() < deliveryTime.getMinMinutes()) {
                throw new IllegalArgumentException("배달 시간(min/max)이 올바르지 않습니다.");
            }
        }
        // 평점
        if (ratings == null || ratings < 0.0 || ratings > 5.0) {
            throw new IllegalArgumentException("평점은 0.0 이상 5.0 이하로 입력해야 합니다.");
        }
        // 상태
        if (status == null) {
            throw new IllegalArgumentException("가게 상태는 필수입니다.");
        }
        // 배달 지역
        if (deliveryAreas == null || deliveryAreas.trim().isEmpty()) {
            throw new IllegalArgumentException("배달 지역은 필수입니다.");
        }
    }
}
