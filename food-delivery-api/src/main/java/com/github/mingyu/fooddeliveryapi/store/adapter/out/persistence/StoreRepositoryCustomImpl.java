package com.github.mingyu.fooddeliveryapi.store.adapter.out.persistence;

import com.github.mingyu.fooddeliveryapi.store.domain.StoreStatus;
import com.github.mingyu.fooddeliveryapi.store.domain.Store;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class StoreRepositoryCustomImpl implements StoreRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Store> findByNameAndCategory(String name, String category, String userAddress) {
        QStore store = QStore.store;

        return queryFactory.selectFrom(store)
                .where(
                        StringUtils.hasText(name) ? store.name.containsIgnoreCase(name) : null,
                        StringUtils.hasText(category) ? store.category.eq(category) : null,
                        StringUtils.hasText(userAddress) ? store.deliveryAreas.contains(userAddress) : null,
                        store.status.ne(StoreStatus.CLOSED)
                )
                .fetch();
    }
}
