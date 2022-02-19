package com.mapp.rpc.core;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 服务注册
 * @author: mapp
 * @date: 2022-02-11 10:34:07
 */
public class ServiceCache {

    private static Map<String, Object> objectMap = new ConcurrentHashMap<>();

    public static void addService(String serviceName, Object obj) {
        objectMap.putIfAbsent(serviceName, obj);
    }

    public static Object getService(String serviceName) {
        return objectMap.get(serviceName);
    }

    public static Set<String> getAllService() {
        return objectMap.keySet();
    }
}