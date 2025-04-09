package com.github.mingyu.fooddeliveryapi.mapper;


import com.github.mingyu.fooddeliveryapi.dto.store.StoreCreateRequestDto;
import com.github.mingyu.fooddeliveryapi.dto.store.StoreResponseDto;
import com.github.mingyu.fooddeliveryapi.dto.store.StoreUpdateRequestDto;
import com.github.mingyu.fooddeliveryapi.entity.Store;
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
