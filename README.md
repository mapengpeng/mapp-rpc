# mapp-rpc
基于netty实现的rpc框架，支持注册中心，负载均衡，springboot-starter方式自动化集成。可以当做了解rpc实现原理的例子

# 使用方式
#### 声明一个服务 使用@RpcService注解声明一个服务
  
```

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
        return hello;
    }
}
```

#### 消费者中注入服务接口 使用@RpcReference
```
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
```
通过声明注解@RpcReference，引入服务接口，@RpcService声明服务
