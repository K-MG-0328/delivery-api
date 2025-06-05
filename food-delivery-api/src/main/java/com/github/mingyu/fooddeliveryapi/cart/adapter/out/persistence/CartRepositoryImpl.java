package com.github.mingyu.fooddeliveryapi.cart.adapter.out.persistence;

import com.github.mingyu.fooddeliveryapi.cart.domain.Cart;
import com.github.mingyu.fooddeliveryapi.cart.domain.CartStatus;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CartRepositoryImpl implements CartRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Cart> findByUserIdAndInStatus(String userId, List<CartStatus> status) {
        QCart cart = QCart.cart;

        return queryFactory.selectFrom(cart)
                .where(
                        cart.userId.eq(userId),
                        cart.status.in(status)
                )
                .fetch();
    }

    @Override
    public long deleteCart(Cart cart) {
        QCart qCart = QCart.cart;
        deleteCartItems(cart);
        return queryFactory
                .delete(qCart)
                .where(
                        qCart.cartId.eq(cart.getCartId())
                )
                .execute();
    }

    public long deleteCartItems(Cart cart) {
        QCartItem qCartItem = QCartItem.cartItem;

        return queryFactory
                .delete(qCartItem)
                .where(
                        qCartItem.cart.eq(cart)
                )
                .execute();
    }
}
