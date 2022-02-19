package com.mapp.rpc.netty.server;

import com.mapp.rpc.core.ConfigConstant;
import com.mapp.rpc.netty.coder.RpcDecoder;
import com.mapp.rpc.netty.coder.RpcEncoder;
import com.mapp.rpc.protocol.RpcRequest;
import com.mapp.rpc.protocol.RpcResponse;
import com.mapp.rpc.serialize.JdkSerializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * netty服务端
 * @author: mapp
 * @date: 2022-02-10 17:40:44
 */
@Slf4j
public class NettyServer {


    private NioEventLoopGroup boss = new NioEventLoopGroup();
    private NioEventLoopGroup worker = new NioEventLoopGroup();
    // 服务端网络处理器
    private NettyServerHandler serverHandler = new NettyServerHandler();

    public void start() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                // 便于调试
                LoggingHandler loggingHandler = new LoggingHandler(LogLevel.DEBUG);
                try {
                    ServerBootstrap serverBootstrap = new ServerBootstrap();
                    serverBootstrap.channel(NioServerSocketChannel.class);
                    serverBootstrap.group(boss, worker);
                    serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new IdleStateHandler(10, 0, 0));
                            ch.pipeline().addLast(loggingHandler);
                            // 添加编码器
                            ch.pipeline().addLast(new RpcEncoder(new JdkSerializer(), RpcResponse.class));
                            // 添加解码器
                            ch.pipeline().addLast(new RpcDecoder(new JdkSerializer(), RpcRequest.class));
                            // 网络处理器
                            ch.pipeline().addLast(serverHandler);

                        }
                    });
                    Channel channel = serverBootstrap.bind(Integer.parseInt(ConfigConstant.serverAddress.split(":")[1])).sync().channel();
                    channel.closeFuture().sync();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    log.error("server error", e);
                } finally {
                    boss.shutdownGracefully();
                    worker.shutdownGracefully();
                }
            }
        }).start();

    }

    public void stop() {
        try {
            boss.shutdownGracefully().sync();
            worker.shutdownGracefully().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }



}