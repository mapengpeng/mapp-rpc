package com.mapp.rpc.core;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * SyncFuture 缓存
 * @author: mapp
 * @date: 2022-02-11 16:14:46
 */
public class SyncFutureCache {

    private static Map<String, SyncFuture> futureCache = new ConcurrentHashMap<>();

    public static SyncFuture get(String requestId) {
        return futureCache.get(requestId);
    }

    public static void put(String requestId, SyncFuture future) {
        futureCache.put(requestId, future);
    }

    public static void remove(String requestId) {
        futureCache.remove(requestId);
    }

}