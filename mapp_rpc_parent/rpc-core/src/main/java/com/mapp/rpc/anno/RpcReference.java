package com.mapp.rpc.anno;


import java.lang.annotation.*;

/**
 * 消费者 service注入
 *
 * @author mapp
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface RpcReference {
}
