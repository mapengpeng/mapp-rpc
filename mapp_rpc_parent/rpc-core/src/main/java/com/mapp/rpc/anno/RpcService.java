package com.mapp.rpc.anno;


import java.lang.annotation.*;

/**
 * 标注service
 *
 * @author mapp
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface RpcService {
}
