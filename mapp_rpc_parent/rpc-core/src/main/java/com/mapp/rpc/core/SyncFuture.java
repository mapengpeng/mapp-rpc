package com.mapp.rpc.core;

import com.mapp.rpc.protocol.RpcResponse;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

/**
 * 同步获取响应
 *
 * 多线程同步
 * @author: mapp
 * @date: 2022-02-11 15:32:58
 */
public class SyncFuture {

    private RpcResponse response;

    public RpcResponse get() throws InterruptedException, ExecutionException {
        synchronized (this) {
            if (response == null) {
                this.wait(6000);
            }
        }
        return response;
    }

    public RpcResponse get(long timeout) throws InterruptedException, ExecutionException, TimeoutException {
        synchronized (this) {
            if (response == null) {
                this.wait(timeout);
            }
        }

        return response;
    }

    public void setResponse(RpcResponse response) {
        synchronized (this) {
            this.response = response;
            this.notifyAll();
        }
    }
}