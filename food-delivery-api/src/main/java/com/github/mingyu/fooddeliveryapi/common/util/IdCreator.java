package com.github.mingyu.fooddeliveryapi.common.util;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class IdCreator {

    private IdCreator() {}

    public static String randomUuid() {
        return UUID.randomUUID().toString();
    }

    public static String nameUuid(Object data) {
        return UUID.nameUUIDFromBytes(
                data.toString().getBytes(StandardCharsets.UTF_8)
        ).toString();
    }
}
