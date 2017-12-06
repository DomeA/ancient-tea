package com.domeastudio.mappingo.servers.microservice.surveying.util.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.*;
import java.security.interfaces.DSAPrivateKey;
import java.security.interfaces.DSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by domea on 16-4-21.
 */
public class DSAHelper {
    private static Logger logger = LoggerFactory.getLogger(DSAHelper.class);

    /**
     * 默认密钥字节数
     * <p>
     * <pre>
     * DSA
     * Default Keysize 1024
     * Keysize must be a multiple of 64, ranging from 512 to 1024 (inclusive).
     * </pre>
     */
    private static final int KEY_SIZE = 1024;

    /**
     * 默认种子
     */
    private static final String DEFAULT_SEED = "0f22507a10bbddd07d8a3082122966e3";

    private static final String PUBLIC_KEY = "DSAPublicKey";
    private static final String PRIVATE_KEY = "DSAPrivateKey";

    /**
     * 用私钥对信息生成数字签名
     *
     * @param data       加密数据
     * @param privateKey 私钥
     * @return
     * @throws Exception
     */
    public static String sign(byte[] data, String privateKey, ALGORITHM keyAlgorithm) throws Exception {
        // 解密由base64编码的私钥
        byte[] keyBytes = BASE64Helper.decryptBASE64(privateKey);

        // 构造PKCS8EncodedKeySpec对象
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);

        // KEY_ALGORITHM 指定的加密算法
        KeyFactory keyFactory = KeyFactory.getInstance(keyAlgorithm.name());

        // 取私钥匙对象
        PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);

        // 用私钥对信息生成数字签名
        Signature signature = Signature.getInstance(keyFactory.getAlgorithm());
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
    public static Boolean verify(byte[] data, String publicKey, String sign, ALGORITHM keyAlgorithm)
            throws Exception {

        // 解密由base64编码的公钥
        byte[] keyBytes = BASE64Helper.decryptBASE64(publicKey);

        // 构造X509EncodedKeySpec对象
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);

        // ALGORITHM 指定的加密算法
        KeyFactory keyFactory = KeyFactory.getInstance(keyAlgorithm.name());

        // 取公钥匙对象
        PublicKey pubKey = keyFactory.generatePublic(keySpec);

        Signature signature = Signature.getInstance(keyFactory.getAlgorithm());
        signature.initVerify(pubKey);
        signature.update(data);

        // 验证签名是否正常
        return signature.verify(BASE64Helper.decryptBASE64(sign));
    }

    /**
     * 生成密钥
     *
     * @param seed 种子
     * @return 密钥对象
     * @throws Exception
     */
    public static Map<String, Object> getKey(String seed, ALGORITHM keyAlgorithm) throws Exception {
        KeyPairGenerator keygen = KeyPairGenerator.getInstance(keyAlgorithm.name());
        // 初始化随机产生器
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.setSeed(seed.getBytes());
        keygen.initialize(KEY_SIZE, secureRandom);

        KeyPair keys = keygen.genKeyPair();

        DSAPublicKey publicKey = (DSAPublicKey) keys.getPublic();
        DSAPrivateKey privateKey = (DSAPrivateKey) keys.getPrivate();

        Map<String, Object> map = new HashMap<String, Object>(2);
        map.put(PUBLIC_KEY, publicKey);
        map.put(PRIVATE_KEY, privateKey);

        return map;
    }

    /**
     * 默认生成密钥
     *
     * @return 密钥对象
     * @throws Exception
     */
    public static Map<String, Object> getKey(ALGORITHM keyAlgorithm) throws Exception {
        return getKey(DEFAULT_SEED, keyAlgorithm);
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

    public static void main(String[] args) throws Exception {
        String inputStr = "abc";
        byte[] data = inputStr.getBytes();

        // 构建密钥
        Map<String, Object> keyMap = DSAHelper.getKey(ALGORITHM.DSA);

        // 获得密钥
        String publicKey = DSAHelper.getPublicKey(keyMap);
        String privateKey = DSAHelper.getPrivateKey(keyMap);

        System.err.println("公钥:\r" + publicKey);
        System.err.println("私钥:\r" + privateKey);

        // 产生签名
        String sign = DSAHelper.sign(data, privateKey, ALGORITHM.DSA);
        System.err.println("签名:\r" + sign);

        // 验证签名
        boolean status = DSAHelper.verify(data, publicKey, sign, ALGORITHM.DSA);
        System.err.println("状态:\r" + status);

    }
}
