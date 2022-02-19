package com.mapp.rpc.register.core;


import java.util.Set;

/**
 * 负载均衡接口
 *
 * @author mapp
 */
public interface LoadBalancer {

    /**
     * 获取服务
     * @param serviceName
     * @param address
     * @return
     */
    String route(String serviceName, Set<String> address);
}
