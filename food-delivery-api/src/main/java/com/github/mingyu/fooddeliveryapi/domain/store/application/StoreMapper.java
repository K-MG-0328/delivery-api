package com.github.mingyu.fooddeliveryapi.domain.store.application;


import com.github.mingyu.fooddeliveryapi.domain.store.presentation.dto.StoreCreateRequest;
import com.github.mingyu.fooddeliveryapi.domain.store.presentation.dto.StoreResponse;
import com.github.mingyu.fooddeliveryapi.domain.store.presentation.dto.StoreUpdateRequest;
import com.github.mingyu.fooddeliveryapi.domain.store.domain.Store;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StoreMapper {

    Store toEntity(StoreCreateRequest dto);

    void updateFromDto(StoreUpdateRequest dto, @MappingTarget Store store);

    StoreResponse toDto(Store store);

    List<StoreResponse> toDtoList(List<Store> stores);
}
