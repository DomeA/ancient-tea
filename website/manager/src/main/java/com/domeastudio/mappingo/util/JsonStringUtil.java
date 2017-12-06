package com.domeastudio.mappingo.util;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

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
