package com.mapp.rpc.serialize;

import com.alibaba.fastjson.JSON;
import com.mapp.rpc.exception.SerializerException;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

/**
 * @author: mapp
 * @date: 2022-02-11 09:34:39
 */
@Slf4j
public class JsonSerializer implements Serializer {

    @Override
    public byte[] serialize(Object obj) {
        try {
            return JSON.toJSONString(obj).getBytes(StandardCharsets.UTF_8);
        }catch (Exception e) {
            log.error("Json Serialize Error : {}", e.getMessage());
            throw new SerializerException("Json Serialize Error");
        }
    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> tClass) {
        try {
            return JSON.parseObject(new String(bytes, StandardCharsets.UTF_8), tClass);
        }catch (Exception e) {
            log.error("Json Deserialize Error : {}", e.getMessage());
            throw new SerializerException("Json Deserialize Error");
        }
    }
}