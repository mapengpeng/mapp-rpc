package com.mapp.rpc.netty.server;

import com.mapp.rpc.core.ServiceCache;
import com.mapp.rpc.protocol.RpcRequest;
import com.mapp.rpc.protocol.RpcResponse;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;

/**
 * 服务端处理
 * @author: mapp
 * @date: 2022-02-11 10:28:41
 */
@ChannelHandler.Sharable
@Slf4j
public class NettyServerHandler extends SimpleChannelInboundHandler<RpcRequest> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcRequest request) throws Exception {

        RpcResponse response = new RpcResponse();

        String className = request.getClassName();
        String methodName = request.getMethodName();
        String requestId = request.getRequestId();
        Object[] parameters = request.getParameters();
        Class<?>[] parameterTypes = request.getParameterTypes();
        // 从service缓存中获取到Service
        Object serviceBean = ServiceCache.getService(className);

        Method method = serviceBean.getClass().getMethod(methodName, parameterTypes);
        // method.invoke
        Object result = method.invoke(serviceBean, parameters);

        response.setRequestId(requestId);
        response.setData(result);

        ctx.writeAndFlush(response);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            log.info("============= 5秒钟没有活跃了。。。");
             ctx.channel().close();
        }
        super.userEventTriggered(ctx, evt);
    }
}