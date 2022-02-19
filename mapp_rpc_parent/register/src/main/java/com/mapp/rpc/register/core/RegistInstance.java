package com.mapp.rpc.register.core;

import lombok.Data;

/**
 * 注册中心
 * @author: mapp
 * @date: 2022-02-16 14:57:05
 */
@Data
public class RegistInstance {

    private String host;
    private int port;
    private String username;
    private String password;

}