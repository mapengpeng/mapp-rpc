package com.mapp.rpc.consumer.conrtoller;

import com.mapp.rpc.anno.RpcReference;
import com.mapp.rpc.api.service.HelloService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: mapp
 * @date: 2022-02-11 17:25:53
 */
@RestController
public class TestController {

    // 声明rpc远程调用
    @RpcReference
    private HelloService helloService;

    @RequestMapping("/sayHello")
    public String sayHello(String msg) {
        return helloService.sayHello(msg);
    }
}