package com.mapp.rpc.spring;

import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author: mapp
 * @date: 2022-02-18 16:44:21
 */
@Configuration
@ComponentScan("com.mapp.rpc.spring")
public class RpcAutoConfig {

    @Bean
    @ConditionalOnExpression("#{'true'.equals(environment['rpc.provider-enable'])}")
    public SpringPrivoderConfig springPrivoderConfig() {
        return new SpringPrivoderConfig();
    }

    @Bean
    @ConditionalOnExpression("#{'true'.equals(environment['rpc.comsumer-enable'])}")
    public SpringConsumerConfig springConsumerConfig() {
        return new SpringConsumerConfig();
    }
}