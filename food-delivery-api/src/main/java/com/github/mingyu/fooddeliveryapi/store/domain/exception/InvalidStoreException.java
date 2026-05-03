package com.github.mingyu.fooddeliveryapi.store.domain.exception;

import java.util.List;

public class InvalidStoreException extends RuntimeException {

    private final List<String> errors;

    public InvalidStoreException(List<String> errors) {
        super("입력 값에 오류가 존재합니다.");
        this.errors = errors;
    }

    public List<String> getErrors() {
        return errors;
    }
}
