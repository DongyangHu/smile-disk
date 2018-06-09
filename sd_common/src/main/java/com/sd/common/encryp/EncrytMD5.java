package com.sd.common.encryp;

import java.security.MessageDigest;


/**
 * MD5加密工具类
 */
public class EncrytMD5 {
    
    /**
     * MD5 32位小写
     * @param s
     * @return
     */
    public static String MD5Lowercase(String sourceStr) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] bytes = md.digest(sourceStr.getBytes("utf-8"));
            return toHexLowercase(bytes);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    /**
     * MD5 32位大写
     * @param s
     * @return
     */
    public static String MD5Capital(String sourceStr) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] bytes = md.digest(sourceStr.getBytes("utf-8"));
            return toHexCapital(bytes);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    
    private static String toHexLowercase (byte[] bytes) {
        final char[] HEX_DIGITS = "0123456789abcdef".toCharArray();
        StringBuilder ret = new StringBuilder(bytes.length * 2);
        for (int i=0; i<bytes.length; i++) {
            ret.append(HEX_DIGITS[(bytes[i] >> 4) & 0x0f]);
            ret.append(HEX_DIGITS[bytes[i] & 0x0f]);
        }
        return ret.toString();
    }
    
    private static String toHexCapital (byte[] bytes) {
        final char[] HEX_DIGITS = "0123456789ABCDEF".toCharArray();
        StringBuilder ret = new StringBuilder(bytes.length * 2);
        for (int i=0; i<bytes.length; i++) {
            ret.append(HEX_DIGITS[(bytes[i] >> 4) & 0x0f]);
            ret.append(HEX_DIGITS[bytes[i] & 0x0f]);
        }
        return ret.toString();
    }
    
}
