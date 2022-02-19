package com.mapp.rpc.provider.service;

import com.mapp.rpc.anno.RpcService;
import com.mapp.rpc.api.service.HelloService;
import com.mapp.rpc.core.ConfigConstant;
import org.springframework.stereotype.Service;

/**
 * 服务实现
 * @author: mapp
 * @date: 2022-02-11 17:20:58
 */
@RpcService
@Service
public class HelloServiceImpl implements HelloService {


    @Override
    public String sayHello(String hello) {
        return hello + "请求的服务端：" + ConfigConstant.serverAddress;
    }
}