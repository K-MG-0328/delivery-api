package com.github.mingyu.fooddeliveryapi.domain.menu.application;

import com.github.mingyu.fooddeliveryapi.domain.menu.domain.Menu;
import com.github.mingyu.fooddeliveryapi.domain.menu.domain.MenuOption;
import com.github.mingyu.fooddeliveryapi.domain.menu.domain.MenuStatus;
import com.github.mingyu.fooddeliveryapi.domain.menu.domain.dto.*;
import com.github.mingyu.fooddeliveryapi.menu.domain.dto.*;
import com.github.mingyu.fooddeliveryapi.domain.menu.domain.MenuOptionRepository;
import com.github.mingyu.fooddeliveryapi.domain.menu.domain.MenuRepository;
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

    private final MenuRepository menuRepository;
    private final MenuOptionRepository menuOptionRepository;
    private final MenuMapper menuMapper;
    private final MenuOptionMapper menuOptionMapper;

    /*메뉴 추가*/
    @Transactional
    public void addMenu(MenuCreateRequestDto request) {

        Menu menu = menuMapper.toEntity(request);
        menu.setStatus(MenuStatus.ACTIVE);
        menu.setStoreId(request.getStoreId());
        menuRepository.save(menu);

        List<MenuOption> options = new ArrayList<>();
        for (MenuOptionCreateRequestDto option : request.getOptions()) {
            MenuOption menuOption = menuOptionMapper.toEntity(option);
            menuOption.setMenuId(menu.getMenuId());
            options.add(menuOption);
        }

        menuOptionRepository.saveAll(options);
    }

    /*메뉴 삭제*/
    @Transactional
    public void deleteMenu(Long menuId) {
        List<MenuOption> options = menuOptionRepository.findByMenu_MenuId(menuId);

        menuOptionRepository.deleteAll(options);
        menuRepository.deleteById(menuId);
    }

    /*메뉴 업데이트*/
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
                option.setMenuId(dto.getMenuId());
                option.setOption(dto.getOption());
                option.setPrice(dto.getPrice());
                option.setStatus(dto.getStatus());
                Options.add(option);
            }
        }

        menuOptionRepository.saveAll(Options);
    }

    /*단일 메뉴 목록 가져오기*/
    public MenuResponseDto getMenu(Long menuId) {
        Menu menu = menuRepository.findById(menuId).orElseThrow(() -> new RuntimeException("메뉴를 찾을 수 없습니다."));
        List<MenuOption> options = menuOptionRepository.findByMenu(menu);

        MenuResponseDto menuResponseDto = menuMapper.toDto(menu);
        List<MenuOptionResponseDto> menuOptionResponseDtos = menuOptionMapper.toDtoList(options);
        menuResponseDto.setOptions(menuOptionResponseDtos);

        return menuResponseDto;
    }

    /*가게 메뉴 목록 가져오기*/
    public MenuListResponseDto searchMenus(MenuSearchCondition request) {
        List<Menu> menus = menuRepository.findByStore_StoreId(request.getStoreId());
        List<MenuOption> allOptions = menuOptionRepository.findByMenuIn(menus);

        // 옵션을 MenuId 기준으로 묶기
        Map<Long, List<MenuOption>> optionsByMenuId = allOptions.stream()
                .collect(Collectors.groupingBy(o -> o.getMenuId()));

        List<MenuResponseDto> responseDto = new ArrayList<>();

        for (Menu menu : menus) {
            MenuResponseDto menuDto = menuMapper.toDto(menu);

            List<MenuOption> options = optionsByMenuId.getOrDefault(menu.getMenuId(), Collections.emptyList());
            List<MenuOptionResponseDto> optionDto = menuOptionMapper.toDtoList(options);

            menuDto.setOptions(optionDto);
            responseDto.add(menuDto);
        }

        return new MenuListResponseDto(responseDto);
    }
}
