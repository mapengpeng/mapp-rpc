package com.mapp.rpc.register.zk;

import com.mapp.rpc.register.core.Discover;
import com.mapp.rpc.register.core.RegistInstance;
import com.mapp.rpc.register.core.ServiceInstance;

import java.util.Set;

/**
 * zk实现的服务发现
 * @author: mapp
 * @date: 2022-02-16 16:14:36
 */
public class ZookeeperDiscover implements Discover {


    @Override
    public Set<String> discover(String serviceName, RegistInstance registInstance) {
        return ZkUtil.get(serviceName, registInstance);
    }
}