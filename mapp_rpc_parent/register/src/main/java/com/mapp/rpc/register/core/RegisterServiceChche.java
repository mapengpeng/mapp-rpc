package com.mapp.rpc.register.core;

import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 服务缓存
 * @author: mapp
 * @date: 2022-02-16 15:39:24
 */
@Slf4j
public class RegisterServiceChche {

    private static Map<String, Set<String>> serviceCache = new ConcurrentHashMap<>();

    public static void put(ServiceInstance serviceInstance) {
        log.info("serviceCache 服务注册缓存新增服务 {}, {}", serviceInstance.getServiceName(), serviceInstance.getAddress());
        Set<String> addressSet = serviceCache.get(serviceInstance.getServiceName());
        if (addressSet == null) {
            addressSet = new HashSet<>();
        }
        addressSet.add(serviceInstance.getAddress());
        serviceCache.put(serviceInstance.getServiceName(), addressSet);
    }

    public static Set<String> get(String serviceName) {
        return serviceCache.get(serviceName);
    }

    public static void remove(ServiceInstance serviceInstance) {
        Set<String> addressSet = serviceCache.get(serviceInstance.getServiceName());
        if (addressSet != null && addressSet.size() > 0) {
            log.info("serviceCache 服务注册缓存删除服务 {}, {}", serviceInstance.getServiceName(), serviceInstance.getAddress());
            addressSet.remove(serviceInstance.getAddress());
        }
    }

    public static void removeAll(String serviceName) {
        Set<String> addressSet = serviceCache.get(serviceName);
        if (addressSet != null) {
            serviceCache.remove(serviceName);
        }
    }
}