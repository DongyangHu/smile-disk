package com.sd.common.random;

import java.util.UUID;

public class UUIDProvider {
    /**
     * 生成UUID
     * @return
     */
    public static String createUUID() {
        String uuid = UUID.randomUUID().toString();
        return uuid.replaceAll("-", "");
    }
}
