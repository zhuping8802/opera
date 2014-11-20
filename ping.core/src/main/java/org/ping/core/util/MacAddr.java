package common;

import java.io.*;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * Created by Ping on 14-11-20.
 */
public class MacAddr {
    public static String hexByte(byte b) {
        String s = "000000" + Integer.toHexString(b);
        return s.substring(s.length() - 2);
    }

    public static String getMAC() {
        Enumeration<NetworkInterface> el;
        String mac_s = "";
        try {
            el = NetworkInterface.getNetworkInterfaces();
            while (el.hasMoreElements()) {
                byte[] mac = el.nextElement().getHardwareAddress();
                if (mac == null)
                    continue;
                mac_s = hexByte(mac[0]) + "-" + hexByte(mac[1]) + "-"
                        + hexByte(mac[2]) + "-" + hexByte(mac[3]) + "-"
                        + hexByte(mac[4]) + "-" + hexByte(mac[5]);
            }
        } catch (SocketException e1) {
            e1.printStackTrace();
        }
        return mac_s;
    }
}
