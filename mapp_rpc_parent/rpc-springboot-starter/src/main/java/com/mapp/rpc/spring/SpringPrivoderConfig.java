package com.mapp.rpc.spring;

import com.mapp.rpc.anno.RpcService;
import com.mapp.rpc.core.ConfigConstant;
import com.mapp.rpc.core.ServiceCache;
import com.mapp.rpc.exception.RpcException;
import com.mapp.rpc.netty.server.NettyServer;
import com.mapp.rpc.register.core.Register;
import com.mapp.rpc.register.core.ServiceInstance;
import com.mapp.rpc.register.core.factory.RegistInstanceFactory;
import com.mapp.rpc.register.core.factory.RegisteFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.Map;

/**
 * @author: mapp
 * @date: 2022-02-10 17:02:21
 */
public class SpringPrivoderConfig implements ApplicationContextAware, InitializingBean, DisposableBean {

    @Autowired
    private RpcConfig rpcConfig;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        rpcConfig.initConstant();
        // 扫描有RpcService注解的service
        Map<String, Object> rpcServiceMap = applicationContext.getBeansWithAnnotation(RpcService.class);
        rpcServiceMap.forEach((key, value) -> {
            // 必须是接口
            if (value.getClass().getInterfaces() == null) {
                throw new RpcException(value.getClass().getName() + "is not Interface");
            }
            String serviceName = value.getClass().getInterfaces()[0].getName();
            // 注册接口
            ServiceCache.addService(serviceName, value);

            // 注册服务，使用注册中心
            Register register = RegisteFactory.create(ConfigConstant.register);
            register.regist(new ServiceInstance(serviceName, ConfigConstant.serverAddress),
                    RegistInstanceFactory.create(ConfigConstant.registerAddress));
        });
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        // 启动netty服务
        new NettyServer().start();
    }

    @Override
    public void destroy() throws Exception {
        // 删除服务
        Register register = RegisteFactory.create(ConfigConstant.register);
        for (String serviceName : ServiceCache.getAllService()) {
            register.clear(new ServiceInstance(serviceName, ConfigConstant.serverAddress),
                    RegistInstanceFactory.create(ConfigConstant.registerAddress));
        }
    }
}