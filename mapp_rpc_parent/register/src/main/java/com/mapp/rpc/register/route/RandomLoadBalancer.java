package com.mapp.rpc.register.route;

import com.mapp.rpc.register.core.LoadBalancer;

import java.util.Random;
import java.util.Set;

/**
 * 随机算法
 * @author: mapp
 * @date: 2022-02-16 16:30:57
 */
public class RandomLoadBalancer implements LoadBalancer {

    @Override
    public String route(String serviceName, Set<String> address) {
        Random random = new Random();
        String[] instances = address.toArray(new String[address.size()]);
        return instances[random.nextInt(address.size())];
    }
}