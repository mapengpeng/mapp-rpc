package com.mapp.rpc.netty.client;

import com.mapp.rpc.core.SyncFuture;
import com.mapp.rpc.core.SyncFutureCache;
import com.mapp.rpc.protocol.RpcResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;

/**
 * 客户单接收到消息处理Handler
 * @author: mapp
 * @date: 2022-02-11 10:52:29
 */
@Slf4j
public class NettyClientHandler extends ChannelInboundHandlerAdapter {

    public void channelActive(ChannelHandlerContext ctx)   {
        log.info("已连接到RPC服务器.{}",ctx.channel().remoteAddress());
    }

    public void channelInactive(ChannelHandlerContext ctx)   {
        InetSocketAddress address =(InetSocketAddress) ctx.channel().remoteAddress();
        log.info("与RPC服务器断开连接."+address);
        ctx.channel().close();
    }

    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg != null) {
            RpcResponse response = (RpcResponse) msg;
            String requestId = response.getRequestId();
            // 根据requestId 获取 SyncFuture
            SyncFuture future = SyncFutureCache.get(requestId);
            if (future == null) {
                return;
            }
            // 设置response 让客户端等待的线程释放锁，继续执行
            future.setResponse(response);
            // 移除SyncFuture
            SyncFutureCache.remove(requestId);
        }
    }

    public void userEventTriggered(ChannelHandlerContext ctx, Object evt)throws Exception {

        if (evt instanceof IdleStateEvent){
            log.info("已超过30秒未与RPC服务器进行读写操作!将发送心跳消息...");
        }else{
            super.userEventTriggered(ctx,evt);
        }
    }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause){
        ctx.channel().close();
    }
}