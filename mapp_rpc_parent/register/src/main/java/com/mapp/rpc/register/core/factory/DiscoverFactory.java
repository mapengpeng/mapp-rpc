package com.mapp.rpc.register.core.factory;

import com.mapp.rpc.register.core.Discover;
import com.mapp.rpc.register.core.enums.RegisteEnmu;
import com.mapp.rpc.register.zk.ZookeeperDiscover;

/**
 * @author: mapp
 * @date: 2022-02-18 11:43:22
 */
public class DiscoverFactory {

    public static Discover create(RegisteEnmu type) {
        if (type == RegisteEnmu.ZK) {
            return new ZookeeperDiscover();
        }
        throw new RuntimeException("不支持的注册中心");
    }
}