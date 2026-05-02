package com.github.mingyu.fooddeliveryapi.store.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QDeliveryTime is a Querydsl query type for DeliveryTime
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QDeliveryTime extends BeanPath<DeliveryTime> {

    private static final long serialVersionUID = -1073586375L;

    public static final QDeliveryTime deliveryTime = new QDeliveryTime("deliveryTime");

    public final NumberPath<Integer> maxMinutes = createNumber("maxMinutes", Integer.class);

    public final NumberPath<Integer> minMinutes = createNumber("minMinutes", Integer.class);

    public QDeliveryTime(String variable) {
        super(DeliveryTime.class, forVariable(variable));
    }

    public QDeliveryTime(Path<? extends DeliveryTime> path) {
        super(path.getType(), path.getMetadata());
    }

    public QDeliveryTime(PathMetadata metadata) {
        super(DeliveryTime.class, metadata);
    }

}

