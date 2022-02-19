# mapp-rpc
基于netty实现的rpc框架，包含了zookeeper实现的注册中心，内置负载均衡，springboot-starter方式自动化集成。使用方式就像dubbo一样。使用非常简单。
代码量少，注释完善，可以当做了解rpc实现原理的例子。

# 使用方式
## 服务端
只需要在application.yml中加入
```
rpc:
  provider-enable: true   # 声明服务提供者
  server-address: localhost:7000   # netty服务器地址
  register: zk   # 注册中心类型
  register-address: 192.168.110.133:2181  # zookee地址
  serializer: jdk             # 序列化方式
```

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

## 客户端
只需要在application.yml中加入
```
rpc:
  comsumer-enable: true   # 声明消费者
  register: zk   # 注册中心类型
  register-address: 192.168.110.133:2181  # zookee地址
  serializer: jdk             # 序列化方式
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
## 代码说明文档
参照- [手写一个RPC框架（一）介绍及实现原理](http://pipima.top/post/641)
