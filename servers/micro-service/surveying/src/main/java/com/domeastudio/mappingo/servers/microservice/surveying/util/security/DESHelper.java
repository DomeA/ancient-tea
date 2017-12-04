package com.domeastudio.mappingo.servers.microservice.surveying.util.security;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.Key;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by domea on 16-4-14.
 * <p>
 * DES安全编码组件
 * <p>
 * <pre>
 * 支持 DES、DESede(TripleDES,就是3DES)、AES、Blowfish、RC2、RC4(ARCFOUR)
 * DES          		key size must be equal to 56
 * DESede(TripleDES) 	key size must be equal to 112 or 168
 * AES          		key size must be equal to 128, 192 or 256,but 192 and 256 bits may not be available
 * Blowfish     		key size must be multiple of 8, and can only range from 32 to 448 (inclusive)
 * RC2          		key size must be between 40 and 1024 bits
 * RC4(ARCFOUR) 		key size must be between 40 and 1024 bits
 * 具体内容 需要关注 JDK Document http://.../docs/technotes/guides/security/SunProviders.html
 * </pre>
 */
public class DESHelper {
    private static Logger logger = LoggerFactory.getLogger(DESHelper.class);

    /**
     * ALGORITHM 算法 <br>
     * 可替换为以下任意一种算法，同时key值的size相应改变。
     *
     * <pre>
     * DES          		key size must be equal to 56
     * DESede(TripleDES) 	key size must be equal to 112 or 168
     * AES          		key size must be equal to 128, 192 or 256,but 192 and 256 bits may not be available
     * Blowfish     		key size must be multiple of 8, and can only range from 32 to 448 (inclusive)
     * RC2          		key size must be between 40 and 1024 bits
     * RC4(ARCFOUR) 		key size must be between 40 and 1024 bits
     * </pre>
     *
     * 在Key toKey(byte[] key)方法中使用下述代码
     * <code>SecretKey secretKey = new SecretKeySpec(key, ALGORITHM);</code> 替换
     * <code>
     * DESKeySpec dks = new DESKeySpec(key);
     * SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
     * SecretKey secretKey = keyFactory.generateSecret(dks);
     * </code>
     */

    /**
     * 转换密钥<br>
     *
     * @param key
     * @return
     * @throws Exception
     */
    private static Key toKeyByDES(byte[] key, ALGORITHM algorithm) throws Exception {
        if (null == key && key.length < 1) {
            return null;
        }
        DESKeySpec dks = new DESKeySpec(key);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(algorithm.name());
        SecretKey secretKey = keyFactory.generateSecret(dks);
        return secretKey;
    }

    /**
     * 转换密钥<br>
     *
     * @param key
     * @return
     * @throws Exception
     */
    private static Key toKey(byte[] key, ALGORITHM algorithm) throws Exception {
        if (null == key && key.length < 1) {
            return null;
        }
        SecretKey secretKey = new SecretKeySpec(key, algorithm.name());
        return secretKey;
    }

    /**
     * 解密
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] decrypt(byte[] data, String key, ALGORITHM algorithm) throws Exception {
        if (null == data && data.length < 1) {
            return null;
        }
        Key k;
        if (algorithm.equals(ALGORITHM.DES)) {
            k = toKeyByDES(BASE64Helper.decryptBASE64(key), algorithm);
        } else {
            k = toKey(BASE64Helper.decryptBASE64(key), algorithm);
        }
        Cipher cipher = Cipher.getInstance(algorithm.name());
        cipher.init(Cipher.DECRYPT_MODE, k);

        return cipher.doFinal(data);
    }

    /**
     * 加密
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
        Key k;
        if (algorithm.equals(ALGORITHM.DES)) {
            k = toKeyByDES(BASE64Helper.decryptBASE64(key), algorithm);
        } else {
            k = toKey(BASE64Helper.decryptBASE64(key), algorithm);
        }
        Cipher cipher = Cipher.getInstance(algorithm.name());
        cipher.init(Cipher.ENCRYPT_MODE, k);
        return cipher.doFinal(data);
    }

    /**
     * 生成密钥
     *
     * @return
     * @throws Exception
     */
    public static String getKey(ALGORITHM algorithm) throws Exception {
        return getKey(null, algorithm);
    }

    /**
     * 生成密钥
     *
     * @param seed
     * @return
     * @throws Exception
     */
    public static String getKey(String seed, ALGORITHM algorithm) throws Exception {
        SecureRandom secureRandom;
        logger.info("");
        if (seed != null) {
            secureRandom = new SecureRandom(BASE64Helper.decryptBASE64(seed));
        } else {
            secureRandom = new SecureRandom();
        }
        KeyGenerator keyGenerator = KeyGenerator.getInstance(algorithm.name());
        keyGenerator.init(secureRandom);
        SecretKey secretKey = keyGenerator.generateKey();
        return BASE64Helper.encryptBASE64(secretKey.getEncoded());
    }

    public static void main(String[] args) throws Exception {
        String inputStr = "DES";
        String key = DESHelper.getKey(ALGORITHM.DES);
        System.err.println("原文:\t" + inputStr);

        System.err.println("密钥:\t" + key);

        byte[] inputData = inputStr.getBytes();
        inputData = DESHelper.encrypt(inputData, key, ALGORITHM.DES);

        System.err.println("加密后:\t" + BASE64Helper.encryptBASE64(inputData));

        byte[] outputData = DESHelper.decrypt(inputData, key, ALGORITHM.DES);
        String outputStr = new String(outputData);
        //String outputStr = new String(outputData);

        System.err.println("解密后:\t" + outputStr);

    }
}
