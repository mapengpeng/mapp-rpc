package com.mapp.rpc.netty.coder;

import com.mapp.rpc.serialize.Serializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * 消息解码器
 * @author: mapp
 * @date: 2022-02-10 17:58:31
 */
public class RpcDecoder extends ByteToMessageDecoder{

    private Serializer serializer;
    private Class<?> clazz;

    public RpcDecoder(Serializer serializer, Class<?> clazz) {
        this.serializer = serializer;
        this.clazz = clazz;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        // 不足四位，非法数据
        if (in.readableBytes() < 4) {
            return;
        }
        // readIndex打上标记
        in.markReaderIndex();
        // 数据长度
        int length = in.readInt();
        if (length < 0) {
            ctx.close();
        }
        if (in.readableBytes() < length) {
            in.resetReaderIndex();
            return;
        }
        byte[] bytes = new byte[length];
        in.readBytes(bytes);
        Object obj = serializer.deserialize(bytes, clazz);
        out.add(obj);
    }
}