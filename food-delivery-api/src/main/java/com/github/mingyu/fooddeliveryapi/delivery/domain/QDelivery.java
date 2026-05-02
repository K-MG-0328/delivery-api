package com.github.mingyu.fooddeliveryapi.delivery.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QDelivery is a Querydsl query type for Delivery
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDelivery extends EntityPathBase<Delivery> {

    private static final long serialVersionUID = -634705015L;

    public static final QDelivery delivery = new QDelivery("delivery");

    public final DateTimePath<java.time.LocalDateTime> completedDate = createDateTime("completedDate", java.time.LocalDateTime.class);

    public final NumberPath<Long> deliveryId = createNumber("deliveryId", Long.class);

    public final StringPath orderId = createString("orderId");

    public final DateTimePath<java.time.LocalDateTime> startedDate = createDateTime("startedDate", java.time.LocalDateTime.class);

    public final EnumPath<DeliveryState> status = createEnum("status", DeliveryState.class);

    public final StringPath storeId = createString("storeId");

    public final StringPath userId = createString("userId");

    public QDelivery(String variable) {
        super(Delivery.class, forVariable(variable));
    }

    public QDelivery(Path<? extends Delivery> path) {
        super(path.getType(), path.getMetadata());
    }

    public QDelivery(PathMetadata metadata) {
        super(Delivery.class, metadata);
    }

}

