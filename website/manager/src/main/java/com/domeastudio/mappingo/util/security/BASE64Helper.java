package com.domeastudio.mappingo.util.security;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * Created by domea on 16-4-14.
 */
public class BASE64Helper {
    /**
     * BASE64解密
     *
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] decryptBASE64(String key) throws Exception {
        return (new BASE64Decoder()).decodeBuffer(key);
    }

    /**
     * BASE64加密
     *
     * @param key
     * @return
     * @throws Exception
     */
    public static String encryptBASE64(byte[] key) throws Exception {
        return (new BASE64Encoder()).encodeBuffer(key);
    }

}
