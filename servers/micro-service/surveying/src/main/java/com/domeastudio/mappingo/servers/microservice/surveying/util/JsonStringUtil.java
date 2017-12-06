package com.domeastudio.mappingo.servers.microservice.surveying.util;

import org.json.JSONObject;

import java.util.*;

public class JsonStringUtil {
    /***
     * json字符串转java List
     * @param jsonStr
     * @return
     * @throws Exception
     */
    public static Map<String, String> toMap(String jsonStr) throws Exception {
        JSONObject jsonObject = new JSONObject(jsonStr);
        Map<String, String> result = new HashMap<>();
        Iterator iterator = jsonObject.keys();
        String key = null;
        String value = null;
        while (iterator.hasNext()) {
            key = (String) iterator.next();
            value = jsonObject.getString(key);
            result.put(key, value);
        }
        return result;
    }
}
