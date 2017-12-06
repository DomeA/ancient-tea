package com.domeastudio.mappingo.util.security;


import com.domeastudio.mappingo.util.security.base.Byte2StringHelper;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by domea on 16-4-2.
 */
public class MD5SHAHelper {
    /**
     * encode string
     *
     * @param algorithm
     * @param data
     * @return String
     */
    public static byte[] encrypt(ALGORITHM algorithm, byte[] data) {
        if (null == data && data.length < 1) {
            return null;
        }
        return encryptCore(algorithm, data);
    }

    private static byte[] encryptCore(ALGORITHM algorithm, byte[] data) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm.name());
            messageDigest.update(data);
            return messageDigest.digest();
        } catch (Exception e) {
            //logger.error("encryption '" + data + "' string error in " + algorithm.name() + " mode:", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * MD5加密
     *
     * @param data
     * @return String
     */
    public static byte[] encryptByMD5(byte[] data) {
        if (null == data && data.length < 1) {
            return null;
        }
        return encryptCore(ALGORITHM.MD5, data);
    }

    public static byte[] encryptByMD5(InputStream inputStream) throws NoSuchAlgorithmException, IOException {
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        byte[] dataBytes = new byte[1024];
        int nread = 0;
        while ((nread = inputStream.read(dataBytes)) != -1) {
            messageDigest.update(dataBytes, 0, nread);
        }
        return messageDigest.digest();
    }

    /**
     * MD5加密
     *
     * @param data
     * @return String
     */
    public static String toString(byte[] data) {
        return Byte2StringHelper.getFormattedText(data);
    }

    //
    public static void main(String[] args) {
        System.out.println("空字符串 MD5  :"
                + Byte2StringHelper.getFormattedText(MD5SHAHelper.encryptByMD5("".getBytes())));
        System.out.println("空格 MD5  :"
                + Byte2StringHelper.getFormattedText(MD5SHAHelper.encrypt(ALGORITHM.MD5, " ".getBytes())));
        System.out.println("空字符串 SHA1 :"
                + Byte2StringHelper.getFormattedText(MD5SHAHelper.encrypt(ALGORITHM.SHA, "".getBytes())));
        System.out.println("空格 SHA1 :"
                + Byte2StringHelper.getFormattedText(MD5SHAHelper.encrypt(ALGORITHM.SHA1, " ".getBytes())));
    }
}
