package com.github.mingyu.fooddeliveryapi.domain.cart.infrastructure;

import com.github.mingyu.fooddeliveryapi.domain.cart.domain.Cart;
import com.github.mingyu.fooddeliveryapi.entity.QCart;
import com.github.mingyu.fooddeliveryapi.domain.cart.domain.CartStatus;
import com.github.mingyu.fooddeliveryapi.domain.cart.domain.CartRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CartRepositoryImpl implements CartRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Cart> findByUserIdAndInStatus(Long userId, List<CartStatus> status) {
        QCart cart = QCart.cart;

        return queryFactory.selectFrom(cart)
                .where(
                        cart.userId.eq(userId),
                        cart.status.in(status)
                )
                .fetch();
    }
}
