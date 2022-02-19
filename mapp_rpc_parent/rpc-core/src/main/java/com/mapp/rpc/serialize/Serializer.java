package com.mapp.rpc.serialize;

/**
 * 序列化接口
 */
public interface Serializer {

    /**
     * 序列化
     * @return
     */
    byte[] serialize(Object obj);

    /**
     * 反序列化
     * @param bytes
     * @return
     */
    <T> T deserialize(byte[] bytes, Class<T> tClass);
}
