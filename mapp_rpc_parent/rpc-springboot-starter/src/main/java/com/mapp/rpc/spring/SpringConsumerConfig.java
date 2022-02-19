package com.mapp.rpc.spring;

import com.mapp.rpc.anno.RpcReference;
import com.mapp.rpc.core.ProxyFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;

import java.lang.reflect.Field;

/**
 * @author: mapp
 * @date: 2022-02-10 17:02:21
 */
public class SpringConsumerConfig extends InstantiationAwareBeanPostProcessorAdapter implements InitializingBean {

    @Autowired
    private RpcConfig rpcConfig;

    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) {

        Field[] fields = bean.getClass().getDeclaredFields();

        for (Field field : fields) {
            // 找到 有 RpcReference 注解的字段，及注入的service接口
            RpcReference rpcReference = field.getAnnotation(RpcReference.class);
            if (rpcReference != null) {
                Class<?> type = field.getType();
                Object proxy = ProxyFactory.getProxy(type);
                field.setAccessible(true);
                // 将RpcReference注解的field替换为代理类
                try {
                    field.set(bean, proxy);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        return super.postProcessAfterInstantiation(bean, beanName);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        rpcConfig.initConstant();
    }
}