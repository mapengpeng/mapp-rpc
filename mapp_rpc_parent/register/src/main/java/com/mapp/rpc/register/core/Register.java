package com.mapp.rpc.register.core;

/**
 * 服务注册接口
 *
 * @author mapp
 */
public interface Register {

    void regist(ServiceInstance serviceInstance, RegistInstance registInstance);

    void clear(ServiceInstance serviceInstance, RegistInstance registInstance);
}
