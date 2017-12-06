package com.domeastudio.mappingo.util.security;

import javax.crypto.Cipher;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by domea on 16-4-16.
 */
public class RSAHelper {

    private static final String PUBLIC_KEY = "RSAPublicKey";
    private static final String PRIVATE_KEY = "RSAPrivateKey";

    /**
     * 用私钥对信息生成数字签名
     *
     * @param data       加密数据
     * @param privateKey 私钥
     * @return
     * @throws Exception
     */
    public static String sign(byte[] data, String privateKey, ALGORITHM keyAlgorithm, ALGORITHM signatureAlgorithm) throws Exception {
        // 解密由base64编码的私钥
        byte[] keyBytes = BASE64Helper.decryptBASE64(privateKey);

        // 构造PKCS8EncodedKeySpec对象
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);

        // KEY_ALGORITHM 指定的加密算法
        KeyFactory keyFactory = KeyFactory.getInstance(keyAlgorithm.name());

        // 取私钥匙对象
        PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);

        // 用私钥对信息生成数字签名
        Signature signature = Signature.getInstance(signatureAlgorithm.name());
        signature.initSign(priKey);
        signature.update(data);

        return BASE64Helper.encryptBASE64(signature.sign());
    }

    /**
     * 校验数字签名
     *
     * @param data      加密数据
     * @param publicKey 公钥
     * @param sign      数字签名
     * @return 校验成功返回true 失败返回false
     * @throws Exception
     */
    public static Boolean verify(byte[] data, String publicKey, String sign, ALGORITHM keyAlgorithm, ALGORITHM signatureAlgorithm)
            throws Exception {

        // 解密由base64编码的公钥
        byte[] keyBytes = BASE64Helper.decryptBASE64(publicKey);

        // 构造X509EncodedKeySpec对象
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);

        // KEY_ALGORITHM 指定的加密算法
        KeyFactory keyFactory = KeyFactory.getInstance(keyAlgorithm.name());

        // 取公钥匙对象
        PublicKey pubKey = keyFactory.generatePublic(keySpec);

        Signature signature = Signature.getInstance(signatureAlgorithm.name());
        signature.initVerify(pubKey);
        signature.update(data);

        // 验证签名是否正常
        return signature.verify(BASE64Helper.decryptBASE64(sign));
    }

    /**
     * 解密<br>
     * 用私钥解密
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPrivateKey(byte[] data, String key, ALGORITHM keyAlgorithm)
            throws Exception {
        // 对密钥解密
        byte[] keyBytes = BASE64Helper.decryptBASE64(key);

        // 取得私钥
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(keyAlgorithm.name());
        Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);

        // 对数据解密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, privateKey);

        return cipher.doFinal(data);
    }

    /**
     * 解密<br>
     * 用公钥解密
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPublicKey(byte[] data, String key, ALGORITHM keyAlgorithm)
            throws Exception {
        // 对密钥解密
        byte[] keyBytes = BASE64Helper.decryptBASE64(key);

        // 取得公钥
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(keyAlgorithm.name());
        Key publicKey = keyFactory.generatePublic(x509KeySpec);

        // 对数据解密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, publicKey);

        return cipher.doFinal(data);
    }

    /**
     * 加密<br>
     * 用公钥加密
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPublicKey(byte[] data, String key, ALGORITHM keyAlgorithm)
            throws Exception {
        // 对公钥解密
        byte[] keyBytes = BASE64Helper.decryptBASE64(key);

        // 取得公钥
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(keyAlgorithm.name());
        Key publicKey = keyFactory.generatePublic(x509KeySpec);

        // 对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);

        return cipher.doFinal(data);
    }

    /**
     * 加密<br>
     * 用私钥加密
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPrivateKey(byte[] data, String key, ALGORITHM keyAlgorithm)
            throws Exception {
        // 对密钥解密
        byte[] keyBytes = BASE64Helper.decryptBASE64(key);

        // 取得私钥
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(keyAlgorithm.name());
        Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);

        // 对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);

        return cipher.doFinal(data);
    }

    /**
     * 取得私钥
     *
     * @param keyMap
     * @return
     * @throws Exception
     */
    public static String getPrivateKey(Map<String, Object> keyMap)
            throws Exception {
        Key key = (Key) keyMap.get(PRIVATE_KEY);

        return BASE64Helper.encryptBASE64(key.getEncoded());
    }

    /**
     * 取得公钥
     *
     * @param keyMap
     * @return
     * @throws Exception
     */
    public static String getPublicKey(Map<String, Object> keyMap)
            throws Exception {
        Key key = (Key) keyMap.get(PUBLIC_KEY);

        return BASE64Helper.encryptBASE64(key.getEncoded());
    }

    /**
     * 初始化密钥
     *
     * @return
     * @throws Exception
     */
    public static Map<String, Object> getKey(ALGORITHM keyAlgorithm) throws Exception {
        KeyPairGenerator keyPairGen = KeyPairGenerator
                .getInstance(keyAlgorithm.name());
        keyPairGen.initialize(1024);

        KeyPair keyPair = keyPairGen.generateKeyPair();

        // 公钥
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();

        // 私钥
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

        Map<String, Object> keyMap = new HashMap<String, Object>(2);

        keyMap.put(PUBLIC_KEY, publicKey);
        keyMap.put(PRIVATE_KEY, privateKey);
        return keyMap;
    }

    public static void main(String[] args) throws Exception {
        String publicKey;
        String privateKey;
        Map<String, Object> keyMap = RSAHelper.getKey(ALGORITHM.RSA);

        publicKey = RSAHelper.getPublicKey(keyMap);
        privateKey = RSAHelper.getPrivateKey(keyMap);
        System.err.println("公钥: \n\r" + publicKey);
        System.err.println("私钥： \n\r" + privateKey);

        System.err.println("公钥加密——私钥解密");
        String inputStr = "abc";
        byte[] data = inputStr.getBytes();

        byte[] encodedData = RSAHelper.encryptByPublicKey(data, publicKey, ALGORITHM.RSA);

        byte[] decodedData = RSAHelper.decryptByPrivateKey(encodedData,
                privateKey, ALGORITHM.RSA);


        String outputStr = new String(decodedData);
        System.err.println("加密前: " + inputStr + "\n\r" + "解密后: " + outputStr);

        System.err.println("私钥加密——公钥解密");
        String inputStr1 = "sign";
        byte[] data1 = inputStr1.getBytes();

        byte[] encodedData1 = RSAHelper.encryptByPrivateKey(data1, privateKey, ALGORITHM.RSA);

        byte[] decodedData1 = RSAHelper
                .decryptByPublicKey(encodedData1, publicKey, ALGORITHM.RSA);

        String outputStr1 = new String(decodedData1);
        System.err.println("加密前: " + inputStr1 + "\n\r" + "解密后: " + outputStr1);


        System.err.println("私钥签名——公钥验证签名");
        // 产生签名
        String sign = RSAHelper.sign(encodedData1, privateKey, ALGORITHM.RSA, ALGORITHM.MD5withRSA);
        System.err.println("签名:\r" + sign);

        // 验证签名
        boolean status = RSAHelper.verify(encodedData1, publicKey, sign, ALGORITHM.RSA, ALGORITHM.MD5withRSA);
        System.err.println("状态:\r" + status);
    }

}
