package org.ping.core.util;

import javax.servlet.http.HttpServletRequest;
import java.util.logging.Logger;

/**
 * Created by Ping on 14-11-20.
 */
public class HttpHelper {

    /**
     * 获取远程请求IP
     *
     * @param request
     * @return
     */
    public static String getRemoteIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        return ip;
    }

    /**
     * 获取远程MAC地址
     *
     * @param ip
     * @return
     */
    public static String getRemoteMacAddr(String ip) {
        UdpClientMacAddr udpClientMacAddr = null;
        String macAddr = "";
        try {
            udpClientMacAddr = new UdpClientMacAddr(ip);
            macAddr = udpClientMacAddr.getRemoteMacAddr();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return macAddr;
    }

    /**
     * 获取远程MAC地址
     *
     * @param request
     * @return
     */
    public static String getRemoteMacAddr(HttpServletRequest request) {
        return HttpHelper.getRemoteMacAddr(HttpHelper.getRemoteIp(request));
    }
}
