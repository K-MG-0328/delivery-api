package com.github.mingyu.fooddeliveryapi.order.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QOrderItemOption is a Querydsl query type for OrderItemOption
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QOrderItemOption extends BeanPath<OrderItemOption> {

    private static final long serialVersionUID = -1170681423L;

    public static final QOrderItemOption orderItemOption = new QOrderItemOption("orderItemOption");

    public final StringPath itemId = createString("itemId");

    public final StringPath optionName = createString("optionName");

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    public QOrderItemOption(String variable) {
        super(OrderItemOption.class, forVariable(variable));
    }

    public QOrderItemOption(Path<? extends OrderItemOption> path) {
        super(path.getType(), path.getMetadata());
    }

    public QOrderItemOption(PathMetadata metadata) {
        super(OrderItemOption.class, metadata);
    }

}

