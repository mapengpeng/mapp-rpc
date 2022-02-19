package com.mapp.rpc.register.core.factory;


import com.mapp.rpc.register.core.LoadBalancer;
import com.mapp.rpc.register.core.enums.LoadBalanceEnmu;
import com.mapp.rpc.register.route.RandomLoadBalancer;


/**
 * 负载均衡工厂
 *
 * @author mapp
 */
public class LoadBalanceFactory {

    public static LoadBalancer create(LoadBalanceEnmu type) {
        if (type == LoadBalanceEnmu.RANDOM) {
            return new RandomLoadBalancer();
        }

        throw new RuntimeException("不支持的负载均衡");
    }
}
