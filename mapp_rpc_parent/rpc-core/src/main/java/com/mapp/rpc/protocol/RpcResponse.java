package com.mapp.rpc.protocol;

import lombok.Data;

import java.io.Serializable;

/**
 * 返回体
 * @author: mapp
 * @date: 2022-02-10 17:36:33
 */
@Data
public class RpcResponse implements Serializable {
    // 请求ID，唯一
    private String requestId;
    private Object data;
}