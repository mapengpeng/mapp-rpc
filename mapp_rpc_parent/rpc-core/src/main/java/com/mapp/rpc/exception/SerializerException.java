package com.mapp.rpc.exception;

/**
 * @author: mapp
 * @date: 2022-02-11 09:31:46
 */
public class SerializerException extends RuntimeException {

    private String message;

    public SerializerException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}