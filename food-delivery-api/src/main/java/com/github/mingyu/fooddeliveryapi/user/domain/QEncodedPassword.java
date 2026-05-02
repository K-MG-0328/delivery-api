package com.github.mingyu.fooddeliveryapi.user.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QEncodedPassword is a Querydsl query type for EncodedPassword
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QEncodedPassword extends BeanPath<EncodedPassword> {

    private static final long serialVersionUID = 60580125L;

    public static final QEncodedPassword encodedPassword1 = new QEncodedPassword("encodedPassword1");

    public final StringPath encodedPassword = createString("encodedPassword");

    public QEncodedPassword(String variable) {
        super(EncodedPassword.class, forVariable(variable));
    }

    public QEncodedPassword(Path<? extends EncodedPassword> path) {
        super(path.getType(), path.getMetadata());
    }

    public QEncodedPassword(PathMetadata metadata) {
        super(EncodedPassword.class, metadata);
    }

}

