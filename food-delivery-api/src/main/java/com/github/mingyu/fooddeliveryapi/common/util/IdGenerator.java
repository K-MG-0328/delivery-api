package com.github.mingyu.fooddeliveryapi.common.util;

import java.util.UUID;

public class IdGenerator {

    private IdGenerator() {}

    public static String uuid() {
        return UUID.randomUUID().toString();
    }
}
