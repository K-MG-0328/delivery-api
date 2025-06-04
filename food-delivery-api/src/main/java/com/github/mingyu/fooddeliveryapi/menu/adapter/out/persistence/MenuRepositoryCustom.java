package com.github.mingyu.fooddeliveryapi.menu.adapter.out.persistence;

import com.github.mingyu.fooddeliveryapi.menu.domain.Menu;

public interface MenuRepositoryCustom {
    void bulkDelete(Menu menu);
}
