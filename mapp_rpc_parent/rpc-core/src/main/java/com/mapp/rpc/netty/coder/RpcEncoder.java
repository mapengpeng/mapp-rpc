package com.mapp.rpc.netty.coder;

import com.mapp.rpc.serialize.Serializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 消息编码器
 * @author: mapp
 * @date: 2022-02-10 17:58:31
 */
public class RpcEncoder extends MessageToByteEncoder<Object> {

    private Serializer serializer;
    private Class<?> clazz;

    public RpcEncoder(Serializer serializer, Class<?> clazz) {
        this.serializer = serializer;
        this.clazz = clazz;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, Object obj, ByteBuf out) throws Exception {
        if (clazz.isInstance(obj)) {
            byte[] bytes = serializer.serialize(obj);
            out.writeInt(bytes.length);
            out.writeBytes(bytes);
        }
    }
}