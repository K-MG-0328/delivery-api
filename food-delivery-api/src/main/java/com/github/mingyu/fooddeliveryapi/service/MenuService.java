package com.github.mingyu.fooddeliveryapi.service;

import com.github.mingyu.fooddeliveryapi.dto.menu.*;
import com.github.mingyu.fooddeliveryapi.entity.Menu;
import com.github.mingyu.fooddeliveryapi.entity.MenuOption;
import com.github.mingyu.fooddeliveryapi.entity.Store;
import com.github.mingyu.fooddeliveryapi.enums.MenuStatus;
import com.github.mingyu.fooddeliveryapi.mapper.MenuMapper;
import com.github.mingyu.fooddeliveryapi.mapper.MenuOptionMapper;
import com.github.mingyu.fooddeliveryapi.repository.MenuRepository;
import com.github.mingyu.fooddeliveryapi.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final StoreRepository storeRepository;
    private final MenuRepository menuRepository;
    private final MenuMapper menuMapper;
    private final MenuOptionMapper menuOptionMapper;

    @Transactional
    public void addMenu(MenuCreateRequestDto request) {
        Store store = storeRepository.findById(request.getStoreId()).orElseThrow(() ->  new RuntimeException("가게를 찾을 수 없습니다."));
        Menu menu = menuMapper.toEntity(request);
        menu.setStatus(MenuStatus.ACTIVE);
        menu.setStore(store);

        for (MenuOptionCreateRequestDto option : request.getOptions()) {
            MenuOption menuOption = menuOptionMapper.toEntity(option);
            menu.addOption(menuOption);
        }

        menuRepository.save(menu);
    }

    @Transactional
    public void deleteMenu(Long menuId) {
        menuRepository.deleteById(menuId);
    }

    @Transactional
    public void updateMenu(Long menuId, MenuUpdateRequestDto request) {
        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new RuntimeException("메뉴를 찾을 수 없습니다."));

        menu.setName(request.getName());
        menu.setPrice(request.getPrice());
        menu.setStatus(request.getStatus());

        List<MenuOption> options = new ArrayList<>();

        for (MenuOptionUpdateRequestDto dto : request.getOptions()) {
                MenuOption option = new MenuOption();
                option.setOption(dto.getOption());
                option.setPrice(dto.getPrice());
                option.setStatus(dto.getStatus());
                options.add(option);
        }

        // Menu의 options 리스트 업데이트
        menu.getOptions().clear();
        menu.getOptions().addAll(options);

        menuRepository.save(menu);
    }

    public MenuResponseDto getMenu(Long menuId) {
        Menu menu = menuRepository.findById(menuId).orElseThrow(() -> new RuntimeException("메뉴를 찾을 수 없습니다."));
        MenuResponseDto menuResponseDto = menuMapper.toDto(menu);

        return menuResponseDto;
    }

    public MenuListResponseDto searchMenus(MenuSearchCondition request) {
        List<Menu> menus = menuRepository.findByStore_StoreId(request.getStoreId());

        List<MenuResponseDto> menuResponseDtos = new ArrayList<>();

        for (Menu menu : menus) {
            MenuResponseDto dto = menuMapper.toDto(menu);
            menuResponseDtos.add(dto);
        }

        return new MenuListResponseDto(menuResponseDtos);
    }
}
