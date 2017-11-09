package com.domeastudio.mappingo.server.microservice.gisprocess.sdk.tools;


import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by domea on 17-5-29.
 */
public final class ValidGeometryHelper {
    private static final long serialVersionUID = 1L;
    private static Logger logger = LoggerFactory.getLogger(ValidGeometryHelper.class);

    public static Boolean isValidAndNotEmpty(Geometry geometry) {
        return !geometry.isEmpty() && geometry.isValid();
    }

    public static Boolean isGeometryCollection(Geometry geometry) {
        return geometry.getClass().equals(GeometryCollection.class);
    }
}
