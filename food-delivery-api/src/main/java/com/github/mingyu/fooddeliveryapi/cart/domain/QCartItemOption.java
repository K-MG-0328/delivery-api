package com.github.mingyu.fooddeliveryapi.cart.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCartItemOption is a Querydsl query type for CartItemOption
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QCartItemOption extends BeanPath<CartItemOption> {

    private static final long serialVersionUID = -871903127L;

    public static final QCartItemOption cartItemOption = new QCartItemOption("cartItemOption");

    public final StringPath itemId = createString("itemId");

    public final StringPath optionName = createString("optionName");

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    public QCartItemOption(String variable) {
        super(CartItemOption.class, forVariable(variable));
    }

    public QCartItemOption(Path<? extends CartItemOption> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCartItemOption(PathMetadata metadata) {
        super(CartItemOption.class, metadata);
    }

}

