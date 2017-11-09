package com.domeastudio.mappingo.server.microservice.gisprocess.sdk.tools.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChangeShpfileCharset {
    private static Logger logger = LoggerFactory.getLogger(GeoJson2Shp.class);

    private static class SingletonHolder {
        private static final ChangeShpfileCharset INSTANCE = new ChangeShpfileCharset();
    }

    private ChangeShpfileCharset() {
    }

    /**
     * 获取当前实例
     * @return 获取当前对象的实例，是线程安全的
     */
    public static final ChangeShpfileCharset getInstance() {
        return ChangeShpfileCharset.SingletonHolder.INSTANCE;
    }

   // private
}
