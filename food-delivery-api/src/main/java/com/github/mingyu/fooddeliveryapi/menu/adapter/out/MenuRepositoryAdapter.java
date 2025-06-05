package com.github.mingyu.fooddeliveryapi.menu.adapter.out;


import com.github.mingyu.fooddeliveryapi.common.PersistentAdapter;
import com.github.mingyu.fooddeliveryapi.menu.adapter.out.persistence.MenuRepository;
import com.github.mingyu.fooddeliveryapi.menu.application.port.out.MenuRepositoryPort;
import com.github.mingyu.fooddeliveryapi.menu.domain.Menu;
import lombok.RequiredArgsConstructor;

import java.util.List;

@PersistentAdapter
@RequiredArgsConstructor
public class MenuRepositoryAdapter implements MenuRepositoryPort {

    private final MenuRepository menuRepository;

    @Override
    public void save(Menu menu) {
        menuRepository.save(menu);
    }

    @Override
    public void delete(Menu menu) {
        menuRepository.bulkDelete(menu);
    }

    @Override
    public List<Menu> findByStore_StoreId(String storeId) {
        return menuRepository.findByStore_StoreId(storeId);
    }

    @Override
    public Menu findById(String menuId) {
        return menuRepository.findById(menuId).orElseThrow(() -> new RuntimeException("메뉴를 찾을 수 없습니다."));
    }
}
