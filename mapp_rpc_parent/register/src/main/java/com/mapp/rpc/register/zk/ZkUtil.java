package com.mapp.rpc.register.zk;

import com.mapp.rpc.register.core.RegistInstance;
import com.mapp.rpc.register.core.RegisterServiceChche;
import com.mapp.rpc.register.core.ServiceInstance;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * zk操作工具类
 *
 * @author: mapp
 * @date: 2022-02-16 14:59:20
 */
@Slf4j
public class ZkUtil {

    private static final String ROOT_PATH = "/rpc";

    public static CuratorFramework getClient(RegistInstance registInstance) {
        CuratorFramework client = CuratorFrameworkFactory.builder()
                .connectString(registInstance.getHost() + ":" + registInstance.getPort())
                .sessionTimeoutMs(60000)
                .connectionTimeoutMs(15000)
                .retryPolicy(new ExponentialBackoffRetry(3000, 10))
                .build();
        client.start();

        return client;
    }

    /**
     * 服务注册
     *
     * @param serviceInstance
     * @param registInstance
     * @return
     */
    public static boolean regist(ServiceInstance serviceInstance, RegistInstance registInstance) {
        CuratorFramework client = getClient(registInstance);
        try {
            String servicePath = buildPath(serviceInstance.getServiceName());
            // 服务名称注册为永久节点
            if (client.checkExists().forPath(servicePath) == null) {
                client.create().creatingParentsIfNeeded().forPath(servicePath);
            }
            // 注册地址
            String s = client.create().creatingParentsIfNeeded().forPath(servicePath + "/" + serviceInstance.getAddress());
            log.info("service {} regist success, path: {}", serviceInstance.getServiceName(), s);
        } catch (Exception e) {
            log.error("服务注册失败, service: {}, {}", serviceInstance.getServiceName(), e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * 服务移除
     *
     * @param serviceInstance
     * @param registInstance
     * @return
     */
    public static boolean clear(ServiceInstance serviceInstance, RegistInstance registInstance) {
        CuratorFramework client = getClient(registInstance);
        try {
            client.delete().deletingChildrenIfNeeded().forPath(buildPath(serviceInstance.getServiceName() + "/" + serviceInstance.getAddress()));
            log.info("service {} delete success", serviceInstance.getServiceName());
        } catch (Exception e) {
            log.error("服务删除失败, service: {}， {}", serviceInstance.getServiceName(), e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * 获取服务地址
     *
     * @param serviceName
     * @param registInstance
     * @return
     */
    public static Set<String> get(String serviceName, RegistInstance registInstance) {
        // 先从缓存获取
        Set<String> set = RegisterServiceChche.get(serviceName);
        if (set != null && set.size() > 0) {
            return set;
        }
        CuratorFramework client = getClient(registInstance);
        try {
            List<String> list = client.getChildren().forPath(buildPath(serviceName));
            // 加入缓存
            for (String address : list) {
                RegisterServiceChche.put(new ServiceInstance(serviceName, address));
            }
            // 只要不是从缓存中获取的服务地址，则都要给节点加上监听事件
            final PathChildrenCache pathChildrenCache = new PathChildrenCache(client, buildPath(serviceName), true);
            // 注册监听
            pathChildrenCache.getListenable().addListener(new PathChildrenCacheListener() {
                public void childEvent(CuratorFramework curatorFramework, PathChildrenCacheEvent pathChildrenCacheEvent) throws Exception {

                    // 获取事件类型
                    PathChildrenCacheEvent.Type type = pathChildrenCacheEvent.getType();
                    // 完整路径 rpc/serviceClanssName/address
                    String path = pathChildrenCacheEvent.getData().getPath();
                    // 获取address
                    path = getAddressPath(path);
                    // 判断事件
                    if (type.equals(PathChildrenCacheEvent.Type.CHILD_REMOVED)) {
                        log.info(serviceName + "节点下删除了服务 address ： {}", path);
                        RegisterServiceChche.remove(new ServiceInstance(serviceName, path));
                    } else if (type.equals(PathChildrenCacheEvent.Type.CHILD_ADDED)) {
                        log.info(serviceName + "节点下新增了服务 address ： {}", path);
                        RegisterServiceChche.put(new ServiceInstance(serviceName, path));
                    }
                }
            });
            // 3.开启监听
            pathChildrenCache.start();

            return new HashSet<>(list);

        } catch (Exception e) {
            log.error("服务获取失败, service: {}, {}", serviceName, e.getMessage());
        }
        return null;
    }

    public static String buildPath(String serviceName) {
        return ROOT_PATH + "/" + serviceName;
    }

    public static String getAddressPath(String path) {
        return path.substring(path.lastIndexOf("/") + 1);
    }
}