package com.mapp.rpc.register.zk;

import com.mapp.rpc.register.core.RegistInstance;
import com.mapp.rpc.register.core.Register;
import com.mapp.rpc.register.core.ServiceInstance;

/**
 * zk注册中心
 * @author: mapp
 * @date: 2022-02-16 14:58:33
 */
public class ZookeeperRegister implements Register {

    @Override
    public void regist(ServiceInstance serviceInstance, RegistInstance registInstance) {
        ZkUtil.regist(serviceInstance, registInstance);
    }

    @Override
    public void clear(ServiceInstance serviceInstance, RegistInstance registInstance) {
        ZkUtil.clear(serviceInstance, registInstance);
    }
}