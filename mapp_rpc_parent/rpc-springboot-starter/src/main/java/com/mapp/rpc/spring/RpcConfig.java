package com.mapp.rpc.spring;


import com.mapp.rpc.core.ConfigConstant;
import com.mapp.rpc.enums.SerializerEnum;
import com.mapp.rpc.register.core.enums.LoadBalanceEnmu;
import com.mapp.rpc.register.core.enums.RegisteEnmu;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


/**
 * @author: mapp
 * @date: 2022-02-17 09:10:14
 */
@Data
@ConfigurationProperties(prefix = "rpc")
@Component
@Order(1)
public class RpcConfig {
    // 服务地址，ip : port
    private String serverAddress;
    // 注册中心地址 ip : port
    private String registerAddress;
    private boolean providerEnable;
    private boolean comsumerEnable;
    // 负载均衡
    private LoadBalanceEnmu loadBalance = LoadBalanceEnmu.RANDOM;
    // 注册中心
    private RegisteEnmu register = RegisteEnmu.ZK;
    // 序列化方式
    private SerializerEnum serializer = SerializerEnum.JDK;

    public void initConstant() {
        ConfigConstant.serverAddress = this.getServerAddress();
        ConfigConstant.registerAddress = this.getRegisterAddress();
        ConfigConstant.loadBalance = this.getLoadBalance();
        ConfigConstant.register = this.getRegister();
        ConfigConstant.serializer = this.getSerializer();
    }
}