package com.domeastudio.mappingo.util.security;

import com.domeastudio.mappingo.util.security.base.Byte2StringHelper;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigInteger;

/**
 * Created by domea on 16-4-14.
 */
public class HMACHelper {

    /**
     * 初始化HMAC密钥
     *
     * @return
     * @throws Exception
     */
    public static String getKey(ALGORITHM algorithm) throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(algorithm.name());
        SecretKey secretKey = keyGenerator.generateKey();
        //logger.info("get HMAC algorithm (operator '" + algorithm.name() + "') key:" + BASE64Helper.encryptBASE64(secretKey.getEncoded()));
        return BASE64Helper.encryptBASE64(secretKey.getEncoded());
    }

    /**
     * HMAC加密
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] encrypt(byte[] data, String key, ALGORITHM algorithm) throws Exception {
        if (null == data && data.length < 1) {
            return null;
        }
        //logger.info("original string:" + data);
        //logger.info("secret key:" + key);
        //logger.info("algorithm operator:" + algorithm.name());
        SecretKey secretKey = new SecretKeySpec(BASE64Helper.decryptBASE64(key), algorithm.name());
        Mac mac = Mac.getInstance(secretKey.getAlgorithm());
        mac.init(secretKey);
        //logger.info("Get HMAC encrypted string:" + BASE64Helper.encryptBASE64(secretKey.getEncoded()));
        return mac.doFinal(data);
    }

    public static void main(String[] args) throws Exception {
        String key = HMACHelper.getKey(ALGORITHM.HmacSHA1);
        System.out.println("key:" + key);
        System.out.println("空字符串 HMAC  :"
                + Byte2StringHelper.getFormattedText(HMACHelper.encrypt("abc".getBytes(), key, ALGORITHM.HmacSHA256)));
        System.out.println("空字符串 HMAC  :"
                + new BigInteger(HMACHelper.encrypt("abc".getBytes(), key, ALGORITHM.HmacSHA256)).toString(16));
    }
}
