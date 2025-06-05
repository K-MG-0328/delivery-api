package com.github.mingyu.fooddeliveryapi.store.domain;

import com.github.mingyu.fooddeliveryapi.store.domain.exception.InvalidStoreException;

import java.util.ArrayList;
import java.util.List;

public class StoreValidator {

    public static void validate(Store store) {
        validate(
                store.getName(),
                store.getCategory(),
                store.getAddress(),
                store.getPhone(),
                store.getMinDeliveryPrice(),
                store.getDeliveryTip(),
                store.getDeliveryTime(),
                store.getRatings(),
                store.getStatus(),
                store.getDeliveryAreas()
        );
    }

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
        List<String> errors = new ArrayList<>();

        // 이름
        if (name == null || name.trim().isEmpty() || name.length() > 255) {
            errors.add("가게 이름은 필수이며 1~255자 이내여야 합니다.");
        }

        // 카테고리
        if (category == null || category.trim().isEmpty()) {
            errors.add("카테고리는 필수입니다.");
        }

        // 주소
        if (address == null || address.trim().isEmpty()) {
            errors.add("주소는 필수입니다.");
        }

        // 전화번호
        if (phone == null || phone.trim().isEmpty() || !phone.matches("^\\d{2,4}-\\d{3,4}-\\d{4}$")) {
            errors.add("전화번호 형식이 올바르지 않습니다. 예: 02-1234-5678");
        }

        // 최소 주문 금액
        if (minDeliveryPrice == null || minDeliveryPrice < 0) {
            errors.add("최소 주문 금액은 0 이상이어야 합니다.");
        }

        // 배달 팁
        if (deliveryTip == null || deliveryTip < 0) {
            errors.add("배달 팁은 0 이상이어야 합니다.");
        }

        // 배달 시간
        if (deliveryTime == null) {
            errors.add("배달 시간 정보는 필수입니다.");
        } else {
            if (deliveryTime.getMinMinutes() == null || deliveryTime.getMinMinutes() < 0) {
                errors.add("배달 최소 시간(minMinutes)은 0 이상이어야 합니다.");
            }
            if (deliveryTime.getMaxMinutes() == null || deliveryTime.getMaxMinutes() < deliveryTime.getMinMinutes()) {
                errors.add("배달 최대 시간(maxMinutes)은 최소 시간보다 크거나 같아야 합니다.");
            }
        }

        // 평점
        if (ratings == null || ratings < 0.0 || ratings > 5.0) {
            errors.add("평점은 0.0 이상 5.0 이하로 입력해야 합니다.");
        }

        // 상태
        if (status == null) {
            errors.add("가게 상태는 필수입니다.");
        }

        // 배달 지역
        if (deliveryAreas == null || deliveryAreas.trim().isEmpty()) {
            errors.add("배달 지역은 필수입니다.");
        }

        if (!errors.isEmpty()) {
            throw new InvalidStoreException(errors);
        }
    }
}
