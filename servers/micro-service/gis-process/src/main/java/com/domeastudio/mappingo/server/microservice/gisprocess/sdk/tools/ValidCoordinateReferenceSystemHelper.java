package com.domeastudio.mappingo.server.microservice.gisprocess.sdk.tools;

import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by domea on 17-5-29.
 */
public class ValidCoordinateReferenceSystemHelper {
    private static final long serialVersionUID = 1L;
    private static Logger logger = LoggerFactory.getLogger(ValidCoordinateReferenceSystemHelper.class);

    public static Boolean isValid(CoordinateReferenceSystem crs) {
        return false;
    }
}
