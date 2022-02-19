//package com.mapp.rpc.util;
//
//import java.net.InetAddress;
//import java.net.NetworkInterface;
//import java.net.SocketException;
//import java.util.Enumeration;
//
///**
// * @author: mapp
// * @date: 2022-02-16 16:49:01
// */
//public class IpUtil {
//
//    public static String getServerIp(){
//        String SERVER_IP = null;
//        try {
//            Enumeration netInterfaces = NetworkInterface.getNetworkInterfaces();
//            InetAddress ip = null;
//            while (netInterfaces.hasMoreElements()) {
//                NetworkInterface ni = (NetworkInterface) netInterfaces.nextElement();
//                ip = (InetAddress) ni.getInetAddresses().nextElement();
//                SERVER_IP = ip.getHostAddress();
//                if (!ip.isSiteLocalAddress() && !ip.isLoopbackAddress()
//                        && ip.getHostAddress().indexOf(":") == -1) {
//                    SERVER_IP = ip.getHostAddress();
//                    break;
//                } else {
//                    ip = null;
//                }
//            }
//        } catch (SocketException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//
//        return SERVER_IP;
//    }
//
//    public static String getAddress(int port){
//        return getServerIp() + ":" + port;
//    }
//}