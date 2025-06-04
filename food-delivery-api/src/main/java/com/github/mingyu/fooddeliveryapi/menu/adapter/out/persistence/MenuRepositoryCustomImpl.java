package com.github.mingyu.fooddeliveryapi.menu.adapter.out.persistence;

import com.github.mingyu.fooddeliveryapi.menu.domain.Menu;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MenuRepositoryCustomImpl implements MenuRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public void bulkDelete(Menu menu) {
        QMenu qMenu = QMenu.menu;
        deleteMenuOption(menu);
        return queryFactory
                .delete(qMenu)
                .where(
                        qMenu.menuId.eq(menu.getMenuId())
                )
                .execute();
    }

    private void deleteMenuOption(Menu menu) {
        QMenuOption qMenuOption = QMenuOption.menuOption;

        return queryFactory
                .delete(qMenuOption)
                .where(
                        qMenuOption.menu.eq(menu)
                )
                .execute();
    }
}
