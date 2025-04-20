package com.github.mingyu.fooddeliveryapi.repository;

import com.github.mingyu.fooddeliveryapi.entity.QStore;
import com.github.mingyu.fooddeliveryapi.entity.Store;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class StoreRepositoryImpl implements StoreRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Store> findByNameAndCategory(String name, String category, String userAddress) {
        QStore store = QStore.store;

        return queryFactory.selectFrom(store)
                .where(
                        StringUtils.hasText(name) ? store.name.containsIgnoreCase(name) : null,
                        StringUtils.hasText(category) ? store.category.eq(category) : null,
                        StringUtils.hasText(userAddress) ? store.deliveryAreas.contains(userAddress) : null
                )
                .fetch();
    }
}
