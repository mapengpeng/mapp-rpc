package com.mapp.rpc.register.core;

import java.util.Set;

/**
 * 服务发现接口
 *
 * @author mapp
 */
public interface Discover {

    Set<String> discover(String serviceName, RegistInstance registInstance);
}
