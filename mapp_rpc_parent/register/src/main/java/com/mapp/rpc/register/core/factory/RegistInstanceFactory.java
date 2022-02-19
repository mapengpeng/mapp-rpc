package com.mapp.rpc.register.core.factory;

import com.mapp.rpc.register.core.RegistInstance;

/**
 * @author: mapp
 * @date: 2022-02-18 09:25:42
 */
public class RegistInstanceFactory {

    public static RegistInstance create(String address) {
        String[] split = address.split(":");
        if (split.length != 2) {
            throw new IllegalArgumentException("address 格式不正确");
        }
        RegistInstance registInstance = new RegistInstance();
        registInstance.setHost(split[0]);
        registInstance.setPort(Integer.parseInt(split[1]));
        return registInstance;
    }
}