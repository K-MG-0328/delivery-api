package com.github.mingyu.fooddeliveryapi.domain.store.application;


import com.github.mingyu.fooddeliveryapi.domain.store.application.dto.StoreParam;
import com.github.mingyu.fooddeliveryapi.domain.store.domain.Store;
import com.github.mingyu.fooddeliveryapi.domain.store.presentation.dto.StoreCreateRequest;
import com.github.mingyu.fooddeliveryapi.domain.store.presentation.dto.StoreResponse;
import com.github.mingyu.fooddeliveryapi.domain.store.presentation.dto.StoreSearchCondition;
import com.github.mingyu.fooddeliveryapi.domain.store.presentation.dto.StoreUpdateRequest;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StoreMapper {

    StoreParam toStoreParam(Store store);
    StoreParam toStoreParam(StoreCreateRequest request);
    StoreParam toStoreParam(StoreUpdateRequest request);
    StoreParam toStoreParam(StoreSearchCondition request);

    List<StoreParam> toStoreParam(List<Store> stores);
    List<StoreResponse> toStoreResponse(List<StoreParam>  params);

    StoreResponse toStoreResponse(StoreParam param);
}
