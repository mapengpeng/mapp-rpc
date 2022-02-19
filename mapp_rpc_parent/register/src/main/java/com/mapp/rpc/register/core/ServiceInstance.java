package com.mapp.rpc.register.core;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 服务信息
 * @author: mapp
 * @date: 2022-02-16 14:48:54
 */
@Data
@AllArgsConstructor
public class ServiceInstance {

    private String serviceName;
    private String address;
}