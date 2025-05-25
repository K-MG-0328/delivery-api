package com.github.mingyu.fooddeliveryapi.domain.store.application;


import com.github.mingyu.fooddeliveryapi.domain.store.domain.dto.StoreCreateRequestDto;
import com.github.mingyu.fooddeliveryapi.domain.store.domain.dto.StoreResponseDto;
import com.github.mingyu.fooddeliveryapi.domain.store.domain.dto.StoreUpdateRequestDto;
import com.github.mingyu.fooddeliveryapi.domain.store.domain.Store;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StoreMapper {

    Store toEntity(StoreCreateRequestDto dto);

    void updateFromDto(StoreUpdateRequestDto dto, @MappingTarget Store store);

    StoreResponseDto toDto(Store store);

    List<StoreResponseDto> toDtoList(List<Store> stores);
}
