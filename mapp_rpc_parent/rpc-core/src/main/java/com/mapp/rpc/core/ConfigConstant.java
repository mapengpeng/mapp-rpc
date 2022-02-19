package com.mapp.rpc.core;

import com.mapp.rpc.enums.SerializerEnum;
import com.mapp.rpc.register.core.enums.LoadBalanceEnmu;
import com.mapp.rpc.register.core.enums.RegisteEnmu;

/**
 * @author: mapp
 * @date: 2022-02-17 18:07:39
 */
public class ConfigConstant {

    public static String serverAddress;
    public static String registerAddress;
    public static LoadBalanceEnmu loadBalance;
    public static RegisteEnmu register;
    public static SerializerEnum serializer;
}