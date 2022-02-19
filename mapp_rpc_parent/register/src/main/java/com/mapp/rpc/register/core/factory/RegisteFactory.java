package com.mapp.rpc.register.core.factory;

import com.mapp.rpc.register.core.Register;
import com.mapp.rpc.register.core.enums.RegisteEnmu;
import com.mapp.rpc.register.zk.ZookeeperRegister;

/**
 * @author: mapp
 * @date: 2022-02-17 18:18:54
 */
public class RegisteFactory {

    public static Register create(RegisteEnmu type) {
        if (type == RegisteEnmu.ZK) {
            return new ZookeeperRegister();
        }
        throw new RuntimeException("不支持的注册中心");
    }
}