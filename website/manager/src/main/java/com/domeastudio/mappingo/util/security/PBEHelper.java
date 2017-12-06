package com.domeastudio.mappingo.util.security;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import java.math.BigInteger;
import java.security.Key;
import java.util.Random;

/**
 * Created by domea on 16-4-16.
 */
public class PBEHelper {
    /**
     * 支持以下任意一种算法
     * <pre>
     * PBEWithMD5AndDES
     * PBEWithMD5AndTripleDES
     * PBEWithSHA1AndDESede
     * PBEWithSHA1AndRC2_40
     * </pre>
     */
    /**
     * 盐初始化
     *
     * @return
     * @throws Exception
     */
    public static byte[] getSalt() throws Exception {
        byte[] salt = new byte[8];
        Random random = new Random();
        random.nextBytes(salt);
        return salt;
    }

    /**
     * 转换密钥<br>
     *
     * @param password
     * @return
     * @throws Exception
     */
    private static Key toKey(String password, ALGORITHM algorithm) throws Exception {
        PBEKeySpec keySpec = new PBEKeySpec(password.toCharArray());
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(algorithm.name());
        SecretKey secretKey = keyFactory.generateSecret(keySpec);
        return secretKey;
    }

    /**
     * 加密
     *
     * @param data     数据
     * @param password 密码
     * @param salt     盐
     * @return
     * @throws Exception
     */
    public static byte[] encrypt(byte[] data, String password, byte[] salt, ALGORITHM algorithm)
            throws Exception {

        Key key = toKey(password, algorithm);

        PBEParameterSpec paramSpec = new PBEParameterSpec(salt, 100);
        Cipher cipher = Cipher.getInstance(algorithm.name());
        cipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);

        return cipher.doFinal(data);

    }

    /**
     * 解密
     *
     * @param data     数据
     * @param password 密码
     * @param salt     盐
     * @return
     * @throws Exception
     */
    public static byte[] decrypt(byte[] data, String password, byte[] salt, ALGORITHM algorithm)
            throws Exception {

        Key key = toKey(password, algorithm);

        PBEParameterSpec paramSpec = new PBEParameterSpec(salt, 100);
        Cipher cipher = Cipher.getInstance(algorithm.name());
        cipher.init(Cipher.DECRYPT_MODE, key, paramSpec);
        return cipher.doFinal(data);
    }

    public static void main(String[] args) throws Exception {
        String inputStr = "abc";
        System.err.println("原文: " + inputStr);
        byte[] input = inputStr.getBytes();

        String pwd = "efg";
        System.err.println("密码: " + pwd);

        byte[] salt = PBEHelper.getSalt();
        System.err.println("盐: " + new BigInteger(salt).toString(16));

        byte[] data = PBEHelper.encrypt(input, pwd, salt, ALGORITHM.PBEWithMD5AndDES);

        System.err.println("加密后: " + BASE64Helper.encryptBASE64(data));

        byte[] output = PBEHelper.decrypt(data, pwd, salt, ALGORITHM.PBEWithMD5AndDES);
        String outputStr = new String(output);

        System.err.println("解密后: " + outputStr);
    }
}
