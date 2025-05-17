package com.github.mingyu.fooddeliveryapi.service;

import com.github.mingyu.fooddeliveryapi.dto.menu.*;
import com.github.mingyu.fooddeliveryapi.entity.Menu;
import com.github.mingyu.fooddeliveryapi.entity.MenuOption;
import com.github.mingyu.fooddeliveryapi.entity.Store;
import com.github.mingyu.fooddeliveryapi.enums.MenuStatus;
import com.github.mingyu.fooddeliveryapi.mapper.MenuMapper;
import com.github.mingyu.fooddeliveryapi.mapper.MenuOptionMapper;
import com.github.mingyu.fooddeliveryapi.repository.MenuOptionRepository;
import com.github.mingyu.fooddeliveryapi.repository.MenuRepository;
import com.github.mingyu.fooddeliveryapi.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final StoreRepository storeRepository;
    private final MenuRepository menuRepository;
    private final MenuOptionRepository menuOptionRepository;
    private final MenuMapper menuMapper;
    private final MenuOptionMapper menuOptionMapper;

    @Transactional
    public void addMenu(MenuCreateRequestDto request) {
        Store store = storeRepository.findById(request.getStoreId()).orElseThrow(() ->  new RuntimeException("가게를 찾을 수 없습니다."));
        Menu menu = menuMapper.toEntity(request);
        menu.setStatus(MenuStatus.ACTIVE);
        menu.setStore(store);
        List<MenuOption> options = new ArrayList<>();

        for (MenuOptionCreateRequestDto option : request.getOptions()) {
            MenuOption menuOption = menuOptionMapper.toEntity(option);
            menuOption.setMenu(menu);
            options.add(menuOption);
        }

        menuRepository.save(menu);
        menuOptionRepository.saveAll(options);
    }

    @Transactional
    public void deleteMenu(Long menuId) {
        List<MenuOption> options = menuOptionRepository.findByMenu_MenuId(menuId);

        menuOptionRepository.deleteAll(options);
        menuRepository.deleteById(menuId);
    }

    @Transactional
    public void updateMenu(Long menuId, MenuUpdateRequestDto request) {
        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new RuntimeException("메뉴를 찾을 수 없습니다."));

        menu.setName(request.getName());
        menu.setPrice(request.getPrice());
        menu.setStatus(request.getStatus());

        menuRepository.save(menu);

        List<MenuOption> menuOptions = menuOptionRepository.findByMenu_MenuId(menuId);
        Map<Long, MenuOption> currentOptionsById = menuOptions.stream()
                .collect(Collectors.toMap(MenuOption::getMenuOptionId, o -> o));

        List<MenuOption> Options = new ArrayList<>();

        for (MenuOptionUpdateRequestDto dto : request.getOptions()) {
            if (dto.getMenuOptionId() != null && currentOptionsById.containsKey(dto.getMenuOptionId())) {
                // 기존 옵션 수정
                MenuOption option = currentOptionsById.get(dto.getMenuOptionId());
                option.setOption(dto.getOption());
                option.setPrice(dto.getPrice());
                option.setStatus(dto.getStatus());
                Options.add(option);
            } else {
                // 새 옵션 추가
                MenuOption option = new MenuOption();
                option.setOption(dto.getOption());
                option.setPrice(dto.getPrice());
                option.setStatus(dto.getStatus());
                option.setMenu(menu);
                Options.add(option);
            }
        }

        menuOptionRepository.saveAll(Options);
    }

    public MenuResponseDto getMenu(Long menuId) {
        Menu menu = menuRepository.findById(menuId).orElseThrow(() -> new RuntimeException("메뉴를 찾을 수 없습니다."));
        List<MenuOption> options = menuOptionRepository.findByMenu(menu);

        MenuResponseDto menuResponseDto = menuMapper.toDto(menu);
        List<MenuOptionResponseDto> menuOptionResponseDtos = menuOptionMapper.toDtoList(options);
        menuResponseDto.setOptions(menuOptionResponseDtos);

        return menuResponseDto;
    }

    public MenuListResponseDto searchMenus(MenuSearchCondition request) {
        List<Menu> menus = menuRepository.findByStore_StoreId(request.getStoreId());
        List<MenuOption> allOptions = menuOptionRepository.findByMenuIn(menus);

        // 옵션을 MenuId 기준으로 묶기
        Map<Long, List<MenuOption>> optionsByMenuId = allOptions.stream()
                .collect(Collectors.groupingBy(o -> o.getMenu().getMenuId()));

        List<MenuResponseDto> result = new ArrayList<>();

        for (Menu menu : menus) {
            MenuResponseDto dto = menuMapper.toDto(menu);

            List<MenuOption> options = optionsByMenuId.getOrDefault(menu.getMenuId(), Collections.emptyList());
            List<MenuOptionResponseDto> optionDtos = menuOptionMapper.toDtoList(options);

            dto.setOptions(optionDtos);
            result.add(dto);
        }

        return new MenuListResponseDto(result);
    }
}
