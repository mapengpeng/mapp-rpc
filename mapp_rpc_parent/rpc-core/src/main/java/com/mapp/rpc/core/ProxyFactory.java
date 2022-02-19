package com.mapp.rpc.core;

import com.mapp.rpc.netty.client.NettyClient;
import com.mapp.rpc.protocol.RpcRequest;
import com.mapp.rpc.util.IdUtil;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 服务接口代理生成
 * @author: mapp
 * @date: 2022-02-10 17:08:57
 */
@SuppressWarnings("all")
public class ProxyFactory {

    private static Map<Class<?>, Object> cache = new ConcurrentHashMap<>();

    public static <T> T getProxy(Class<T> tClass) {

        return (T) cache.computeIfAbsent(tClass, key -> {
            return Proxy.newProxyInstance(ProxyFactory.class.getClassLoader(), new Class[]{tClass}, new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                    RpcRequest rpcRequest = new RpcRequest();
                    rpcRequest.setRequestId(IdUtil.getId());
                    rpcRequest.setClassName(tClass.getName());
                    rpcRequest.setMethodName(method.getName());
                    rpcRequest.setParameters(args);
                    rpcRequest.setParameterTypes(method.getParameterTypes());

                    // 通过netty发送数据
                    NettyClient.send(rpcRequest);
                    SyncFuture future = new SyncFuture();
                    SyncFutureCache.put(rpcRequest.getRequestId(), future);
                    // 同步等待获取结果
                    return future.get(3000).getData();
                }
            });
        });
    }
}
