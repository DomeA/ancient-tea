package com.domeastudio.mappingo.server.microservice.gisprocess.sdk.tools;

import com.vividsolutions.jts.geom.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by domea on 17-5-29.
 */
public final class SpatialOperationHelper {
    private static final long serialVersionUID = 1L;
    private static Logger logger = LoggerFactory.getLogger(SpatialOperationHelper.class);

    public static Boolean touches(Geometry activeGeometry, Geometry passiveGeometry) {
        if (ValidGeometryHelper.isValidAndNotEmpty(activeGeometry) && ValidGeometryHelper.isValidAndNotEmpty(passiveGeometry)) {
            return activeGeometry.touches(passiveGeometry);
        } else {
            logger.error("geometry is empty or invalid");
            throw new RuntimeException("geometry is empty or invalid");
        }
    }

    public static Geometry buffer(Geometry activeGeometry, Double distance) {
        if (ValidGeometryHelper.isValidAndNotEmpty(activeGeometry)) {
            return activeGeometry.buffer(distance);
        } else {
            logger.error("geometry is empty or invalid");
            throw new RuntimeException("geometry is empty or invalid");
        }
    }

    public static Boolean contains(Geometry activeGeometry, Geometry passiveGeometry) {
        if (ValidGeometryHelper.isValidAndNotEmpty(activeGeometry) && ValidGeometryHelper.isValidAndNotEmpty(passiveGeometry)) {
            return activeGeometry.contains(passiveGeometry);
        } else {
            logger.error("geometry is empty or invalid");
            throw new RuntimeException("geometry is empty or invalid");
        }
    }

    public static Boolean crosses(Geometry activeGeometry, Geometry passiveGeometry) {
        if (ValidGeometryHelper.isValidAndNotEmpty(activeGeometry) && ValidGeometryHelper.isValidAndNotEmpty(passiveGeometry)) {
            return activeGeometry.crosses(passiveGeometry);
        } else {
            logger.error("geometry is empty or invalid");
            throw new RuntimeException("geometry is empty or invalid");
        }
    }

    public static Geometry difference(Geometry activeGeometry, Geometry passiveGeometry) {
        if (ValidGeometryHelper.isValidAndNotEmpty(activeGeometry)) {
            return activeGeometry.difference(passiveGeometry);
        } else {
            logger.error("geometry is empty or invalid");
            throw new RuntimeException("geometry is empty or invalid");
        }
    }

    public static Boolean equals(Geometry activeGeometry, Geometry passiveGeometry) {
        if (ValidGeometryHelper.isValidAndNotEmpty(activeGeometry) && ValidGeometryHelper.isValidAndNotEmpty(passiveGeometry)) {
            return activeGeometry.equals(passiveGeometry);
        } else {
            logger.error("geometry is empty or invalid");
            throw new RuntimeException("geometry is empty or invalid");
        }
    }

    public static Geometry intersection(Geometry activeGeometry, Geometry passiveGeometry) {
        if (ValidGeometryHelper.isValidAndNotEmpty(activeGeometry) && ValidGeometryHelper.isValidAndNotEmpty(passiveGeometry)) {
            return activeGeometry.intersection(passiveGeometry);
        } else {
            logger.error("geometry is empty or invalid");
            throw new RuntimeException("geometry is empty or invalid");
        }
    }

    public static Boolean disjoint(Geometry activeGeometry, Geometry passiveGeometry) {
        if (ValidGeometryHelper.isValidAndNotEmpty(activeGeometry) && ValidGeometryHelper.isValidAndNotEmpty(passiveGeometry)) {
            return activeGeometry.disjoint(passiveGeometry);
        } else {
            logger.error("geometry is empty or invalid");
            throw new RuntimeException("geometry is empty or invalid");
        }
    }

    public static Double distance(Geometry activeGeometry, Geometry passiveGeometry) {
        if (ValidGeometryHelper.isValidAndNotEmpty(activeGeometry) && ValidGeometryHelper.isValidAndNotEmpty(passiveGeometry)) {
            return activeGeometry.distance(passiveGeometry);
        } else {
            logger.error("geometry is empty or invalid");
            throw new RuntimeException("geometry is empty or invalid");
        }
    }

    public static Double length(Geometry activeGeometry) {
        if (ValidGeometryHelper.isValidAndNotEmpty(activeGeometry)) {
            return activeGeometry.getLength();
        } else {
            logger.error("geometry is empty or invalid");
            throw new RuntimeException("geometry is empty or invalid");
        }
    }

    public static Double area(Geometry activeGeometry) {
        if (ValidGeometryHelper.isValidAndNotEmpty(activeGeometry)) {
            return activeGeometry.getArea();
        } else {
            logger.error("geometry is empty or invalid");
            throw new RuntimeException("geometry is empty or invalid");
        }
    }

    public static Boolean intersects(Geometry activeGeometry, Geometry passiveGeometry) {
        if (ValidGeometryHelper.isValidAndNotEmpty(activeGeometry) && ValidGeometryHelper.isValidAndNotEmpty(passiveGeometry)) {
            return activeGeometry.intersects(passiveGeometry);
        } else {
            logger.error("geometry is empty or invalid");
            throw new RuntimeException("geometry is empty or invalid");
        }
    }

    public static Geometry union(Geometry activeGeometry, Geometry passiveGeometry) {
        if (ValidGeometryHelper.isValidAndNotEmpty(activeGeometry) && ValidGeometryHelper.isValidAndNotEmpty(passiveGeometry)) {
            return activeGeometry.union(passiveGeometry);
        } else {
            logger.error("geometry is empty or invalid");
            throw new RuntimeException("geometry is empty or invalid");
        }
    }

    public static Boolean within(Geometry activeGeometry, Geometry passiveGeometry) {
        if (ValidGeometryHelper.isValidAndNotEmpty(activeGeometry) && ValidGeometryHelper.isValidAndNotEmpty(passiveGeometry)) {
            return activeGeometry.within(passiveGeometry);
        } else {
            logger.error("geometry is empty or invalid");
            throw new RuntimeException("geometry is empty or invalid");
        }
    }

    public static Boolean overlaps(Geometry activeGeometry, Geometry passiveGeometry) {
        if (ValidGeometryHelper.isValidAndNotEmpty(activeGeometry) && ValidGeometryHelper.isValidAndNotEmpty(passiveGeometry)) {
            return activeGeometry.overlaps(passiveGeometry);
        } else {
            logger.error("geometry is empty or invalid");
            throw new RuntimeException("geometry is empty or invalid");
        }
    }

//    public static void main(String[] args) {
//        GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory(null);
//        Coordinate coord = new Coordinate(109.013388, 32.715519);
//        Point point = geometryFactory.createPoint(coord);
//
//        Coordinate coord1=new Coordinate(109.015388, 32.915519);
//        LineString lineString=geometryFactory.createLineString(new Coordinate[]{coord,coord1});
//        System.out.print(SpatialOperationHelper.buffer(point,1000000.0));
//    }
}
