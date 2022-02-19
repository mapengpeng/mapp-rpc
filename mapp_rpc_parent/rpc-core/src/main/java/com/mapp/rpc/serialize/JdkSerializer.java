package com.mapp.rpc.serialize;

import com.mapp.rpc.exception.SerializerException;
import lombok.extern.slf4j.Slf4j;

import java.io.*;

/**
 * @author: mapp
 * @date: 2022-02-11 09:17:36
 */
@Slf4j
public class JdkSerializer implements Serializer {

    @Override
    public byte[] serialize(Object obj) {
        ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
        try (ObjectOutputStream out = new ObjectOutputStream(arrayOutputStream)) {
            out.writeObject(obj);
            return arrayOutputStream.toByteArray();
        } catch (IOException e) {
            log.error("Jdk Serializer Exception : {}", e.getMessage());
            throw new SerializerException("Jdk Serializer Exception");
        }
    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> tClass) {
        ByteArrayInputStream arrayInputStream = new ByteArrayInputStream(bytes);
        try (ObjectInputStream inputStream = new ObjectInputStream(arrayInputStream)) {
            return (T) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            log.error("Jdk Deserialize Exception : {}", e.getMessage());
            throw new SerializerException("Jdk Deserialize Exception");
        }
    }
}