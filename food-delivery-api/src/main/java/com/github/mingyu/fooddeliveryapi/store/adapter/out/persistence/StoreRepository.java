package com.github.mingyu.fooddeliveryapi.store.adapter.out.persistence;

import com.github.mingyu.fooddeliveryapi.store.domain.Store;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, String>, StoreRepositoryCustom {
}
