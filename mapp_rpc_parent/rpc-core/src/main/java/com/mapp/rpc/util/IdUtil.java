package com.mapp.rpc.util;

import java.util.UUID;

/**
 * @author: mapp
 * @date: 2022-02-11 10:45:48
 */
public class IdUtil {

    public static String getId() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}