package com.github.mingyu.fooddeliveryapi.repository;

import com.github.mingyu.fooddeliveryapi.entity.QStore;
import com.github.mingyu.fooddeliveryapi.entity.Store;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

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
                        name != null ? store.name.containsIgnoreCase(name) : null,
                        category != null ? store.category.eq(category) : null,
                        userAddress != null ? store.deliveryAreas.contains(userAddress) : null
                )
                .fetch();
    }
}
