package com.tiger.chatroom.util;


/**
 * @ClassName ByteUtil
 * @Description TODO
 * @Author zeng.h
 * @Date 2019/8/21 17:30
 * @Version 1.0
 **/
public class ByteUtil {

    private final static byte[] hex = "0123456789ABCDEF".getBytes();

    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    /**
     * 16进制字符串转字节数组
     * @description
     * @author zeng.h
     * @date 2019/8/21 17:32
     * @param hexString: 16进制字符串
     * @return byte[] 如果字符串为空，则返回字节数组长度为0
     */
    public static byte[] hexStrToByte(String hexString) {
        if (hexString == null || hexString.trim().length() == 0) {
            return new byte[0];
        }
        hexString = hexString.toUpperCase().trim();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }

    /**
     * 字节数组转16进制字符串
     * @description
     * @author zeng.h
     * @date 2019/8/21 17:33
     * @param bytes: 字节数组
     * @return java.lang.String 如果字节数组为空或者长度为0，则返回的字符串长度为0
     */
    public static String byte2HexStr(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return "";
        }
        byte[] buff = new byte[2 * bytes.length];
        for (int i = 0; i < bytes.length; i++) {
            buff[2 * i] = hex[(bytes[i] >> 4) & 0x0f];
            buff[2 * i + 1] = hex[bytes[i] & 0x0f];
        }
        return new String(buff);
    }
}
