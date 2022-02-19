package com.mapp.rpc.protocol;

import lombok.Data;

import java.io.Serializable;

/**
 * 请求体
 * @author: mapp
 * @date: 2022-02-10 17:36:33
 */
@Data
public class RpcRequest implements Serializable {

    // 请求ID，唯一
    private String requestId;
    // class名称
    private String className;
    // 方法名称
    private String methodName;
    // 方法参数
    private Object[] parameters;
    // 方法参数类型
    private Class[] parameterTypes;
}