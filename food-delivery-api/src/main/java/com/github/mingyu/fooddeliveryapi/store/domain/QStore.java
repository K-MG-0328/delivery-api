package com.github.mingyu.fooddeliveryapi.store.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QStore is a Querydsl query type for Store
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QStore extends EntityPathBase<Store> {

    private static final long serialVersionUID = -285977207L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QStore store = new QStore("store");

    public final StringPath address = createString("address");

    public final StringPath category = createString("category");

    public final DateTimePath<java.time.LocalDateTime> createdDate = createDateTime("createdDate", java.time.LocalDateTime.class);

    public final StringPath deliveryAreas = createString("deliveryAreas");

    public final QDeliveryTime deliveryTime;

    public final NumberPath<Integer> deliveryTip = createNumber("deliveryTip", Integer.class);

    public final NumberPath<Integer> minDeliveryPrice = createNumber("minDeliveryPrice", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> modifiedDate = createDateTime("modifiedDate", java.time.LocalDateTime.class);

    public final StringPath name = createString("name");

    public final StringPath phone = createString("phone");

    public final NumberPath<Double> ratings = createNumber("ratings", Double.class);

    public final EnumPath<StoreStatus> status = createEnum("status", StoreStatus.class);

    public final StringPath storeId = createString("storeId");

    public QStore(String variable) {
        this(Store.class, forVariable(variable), INITS);
    }

    public QStore(Path<? extends Store> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QStore(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QStore(PathMetadata metadata, PathInits inits) {
        this(Store.class, metadata, inits);
    }

    public QStore(Class<? extends Store> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.deliveryTime = inits.isInitialized("deliveryTime") ? new QDeliveryTime(forProperty("deliveryTime")) : null;
    }

}

