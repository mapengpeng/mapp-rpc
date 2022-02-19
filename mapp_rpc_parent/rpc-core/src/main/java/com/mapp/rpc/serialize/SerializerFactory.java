package com.mapp.rpc.serialize;

import com.mapp.rpc.enums.SerializerEnum;

/**
 * 序列化工厂
 * @author: mapp
 * @date: 2022-02-18 09:33:07
 */
public class SerializerFactory {
    public static Serializer create(SerializerEnum type) {

        if (type == SerializerEnum.JDK) {
            return new JdkSerializer();
        }else if (type == SerializerEnum.JSON) {
            return new JsonSerializer();
        }
        throw new IllegalArgumentException("不支持的序列化方式");
    }
}