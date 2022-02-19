package com.mapp.rpc.netty.client;

import com.mapp.rpc.core.ConfigConstant;
import com.mapp.rpc.exception.RpcException;
import com.mapp.rpc.netty.coder.RpcDecoder;
import com.mapp.rpc.netty.coder.RpcEncoder;
import com.mapp.rpc.protocol.RpcRequest;
import com.mapp.rpc.protocol.RpcResponse;
import com.mapp.rpc.register.core.Discover;
import com.mapp.rpc.register.core.LoadBalancer;
import com.mapp.rpc.register.core.RegistInstance;
import com.mapp.rpc.register.core.Register;
import com.mapp.rpc.register.core.factory.DiscoverFactory;
import com.mapp.rpc.register.core.factory.LoadBalanceFactory;
import com.mapp.rpc.register.core.factory.RegistInstanceFactory;
import com.mapp.rpc.register.route.RandomLoadBalancer;
import com.mapp.rpc.register.zk.ZookeeperDiscover;
import com.mapp.rpc.register.zk.ZookeeperRegister;
import com.mapp.rpc.serialize.JdkSerializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Set;

/**
 * @author: mapp
 * @date: 2022-02-11 10:47:44
 */
@Slf4j
public class NettyClient {

    private static NioEventLoopGroup group = new NioEventLoopGroup();
    private static Bootstrap bootstrap;
    private static Discover discover;
    private static LoadBalancer loadBalancer;

    static {
        initClient();
        discover = DiscoverFactory.create(ConfigConstant.register);
        loadBalancer = LoadBalanceFactory.create(ConfigConstant.loadBalance);
    }

    public static void initClient() {

            LoggingHandler loggingHandler = new LoggingHandler();
            bootstrap = new Bootstrap();
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.group(group);
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(loggingHandler);
                    // 添加编码器
                    ch.pipeline().addLast(new RpcEncoder(new JdkSerializer(), RpcRequest.class));
                    // 添加解码器
                    ch.pipeline().addLast(new RpcDecoder(new JdkSerializer(), RpcResponse.class));
                    ch.pipeline().addLast(new NettyClientHandler());
                }
            })
            .option(ChannelOption.TCP_NODELAY, true)
            .option(ChannelOption.SO_KEEPALIVE, true)
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000);
    }

    /**
     * 获取一个连接
     * @param address
     * @return
     * @throws InterruptedException
     */
    public static Channel getConnection(SocketAddress address) throws InterruptedException {
        ChannelFuture future = bootstrap.connect(address);
        Channel channel = future.sync().channel();
        return channel;
    }

    /**
     * 发送消息
     * @param request
     * @throws InterruptedException
     */
    public static void send(RpcRequest request) throws InterruptedException {
        RegistInstance registInstance = RegistInstanceFactory.create(ConfigConstant.registerAddress);

        Set<String> addressSset = NettyClient.discover.discover(request.getClassName(), registInstance);
        if (addressSset == null || addressSset.size() == 0) {
            throw new RpcException("没有可用的服务节点！");
        }
        String address = loadBalancer.route(request.getClassName(), addressSset);
        String[] split = address.split(":");
        Channel channel = getConnection(new InetSocketAddress(split[0], Integer.parseInt(split[1])));
        channel.writeAndFlush(request);
    }
}