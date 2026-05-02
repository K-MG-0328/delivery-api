package com.github.mingyu.fooddeliveryapi.order.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QStoreInfo is a Querydsl query type for StoreInfo
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QStoreInfo extends BeanPath<StoreInfo> {

    private static final long serialVersionUID = 240958410L;

    public static final QStoreInfo storeInfo = new QStoreInfo("storeInfo");

    public final StringPath storeAddress = createString("storeAddress");

    public final StringPath storeId = createString("storeId");

    public final StringPath storeName = createString("storeName");

    public final StringPath storePhone = createString("storePhone");

    public QStoreInfo(String variable) {
        super(StoreInfo.class, forVariable(variable));
    }

    public QStoreInfo(Path<? extends StoreInfo> path) {
        super(path.getType(), path.getMetadata());
    }

    public QStoreInfo(PathMetadata metadata) {
        super(StoreInfo.class, metadata);
    }

}

