package com.domeastudio.mappingo.server.microservice.gisprocess.sdk.tools.core;

import com.domeastudio.mappingo.server.microservice.gisprocess.sdk.tools.ValidGeometryHelper;
import com.vividsolutions.jts.geom.*;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.List;

/**
 * Created by domea on 17-5-29.
 */
public class WKT2Geometry {
    private static Logger logger = LoggerFactory.getLogger(WKT2Geometry.class);
    private WKTReader wktReader = new WKTReader();
    private GeometryFactory geometryFactory = new GeometryFactory();

    private static class SingletonHolder {
        private static final WKT2Geometry INSTANCE = new WKT2Geometry();
    }

    private WKT2Geometry() {
    }

    /**
     * 获取当前实例
     *
     * @return 获取当前对象的实例，是线程安全的
     */
    public static final WKT2Geometry getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public Geometry getGeometry(String wkt) throws ParseException {
        Geometry geometry = wktReader.read(wkt);
        if (ValidGeometryHelper.isValidAndNotEmpty(geometry)) {
            return geometry;
        } else {
            logger.error("wkt string is empty or invalid");
            throw new RuntimeException("wkt string is empty or invalid");
        }
    }

    public Geometry getGeometry(String wkt, int epsg) throws ParseException {
        Geometry geometry = wktReader.read(wkt);
        geometry.setSRID(epsg);
        if (ValidGeometryHelper.isValidAndNotEmpty(geometry)) {
            return geometry;
        } else {
            logger.error("wkt string is empty or invalid");
            throw new RuntimeException("wkt string is empty or invalid");
        }
    }

    public Geometry getGeometryExt(String ewkt) throws ParseException {
        Geometry geometry = getGeometry(ewkt.split(";")[1], Integer.parseInt(ewkt.split(";")[0].split("=")[1]));
        if (ValidGeometryHelper.isValidAndNotEmpty(geometry)) {
            return geometry;
        } else {
            logger.error("wkt string is empty or invalid");
            throw new RuntimeException("wkt string is empty or invalid");
        }
    }

    public LinearRing getLinearRing(Coordinate[] coordinates) {
        LinearRing linearRing = geometryFactory.createLinearRing(coordinates);
        if (ValidGeometryHelper.isValidAndNotEmpty(linearRing)) {
            return linearRing;
        } else {
            logger.error("linearRing object is empty or invalid");
            throw new RuntimeException("linearRing object is empty or invalid");
        }
    }

    public Point getPoint(Coordinate coordinate) {
        Point point = geometryFactory.createPoint(coordinate);
        if (ValidGeometryHelper.isValidAndNotEmpty(point)) {
            return point;
        } else {
            logger.error("point object is empty or invalid");
            throw new RuntimeException("point object is empty or invalid");
        }
    }

    public MultiPoint getMultiPoint(List<Point> pointList) {
        MultiPoint multiPoint = geometryFactory.createMultiPoint((Point[]) pointList.toArray());
        if (ValidGeometryHelper.isValidAndNotEmpty(multiPoint)) {
            return multiPoint;
        } else {
            logger.error("Multi point object is empty or invalid");
            throw new RuntimeException("Multi point object is empty or invalid");
        }
    }

    public MultiPoint getMultiPoint(Coordinate[] coordinates) {
        MultiPoint multiPoint = geometryFactory.createMultiPoint(coordinates);
        if (ValidGeometryHelper.isValidAndNotEmpty(multiPoint)) {
            return multiPoint;
        } else {
            logger.error("Multi point object is empty or invalid");
            throw new RuntimeException("Multi point object is empty or invalid");
        }
    }

    public LineString getLineString(Coordinate[] coordinates) {
        LineString lineString = geometryFactory.createLineString(coordinates);
        if (ValidGeometryHelper.isValidAndNotEmpty(lineString)) {
            return lineString;
        } else {
            logger.error("LineString object is empty or invalid");
            throw new RuntimeException("LineString object is empty or invalid");
        }
    }

    public MultiLineString getMultiLineString(List<LineString> lineStringList) {
        MultiLineString multiLineString = geometryFactory.createMultiLineString((LineString[]) lineStringList.toArray());
        if (ValidGeometryHelper.isValidAndNotEmpty(multiLineString)) {
            return multiLineString;
        } else {
            logger.error("Multi LineString object is empty or invalid");
            throw new RuntimeException("Multi LineString object is empty or invalid");
        }
    }

    public Polygon getPolygon(Coordinate[] coordinates) {
        Polygon polygon = geometryFactory.createPolygon(coordinates);
        if (ValidGeometryHelper.isValidAndNotEmpty(polygon)) {
            return polygon;
        } else {
            logger.error("polygon object is empty or invalid");
            throw new RuntimeException("polygon object is empty or invalid");
        }
    }

    public Polygon getPolygon(LinearRing shell) {
        Polygon polygon = geometryFactory.createPolygon(shell);
        if (ValidGeometryHelper.isValidAndNotEmpty(polygon)) {
            return polygon;
        } else {
            logger.error("polygon object is empty or invalid");
            throw new RuntimeException("polygon object is empty or invalid");
        }
    }

    public Polygon getPolygon(LinearRing shell, LinearRing[] holes) {
        Polygon polygon = geometryFactory.createPolygon(shell, holes);
        if (ValidGeometryHelper.isValidAndNotEmpty(polygon)) {
            return polygon;
        } else {
            logger.error("polygon object is empty or invalid");
            throw new RuntimeException("polygon object is empty or invalid");
        }
    }

    public MultiPolygon getMultiPolygon(Polygon[] polygons) {
        MultiPolygon multiPolygon = geometryFactory.createMultiPolygon(polygons);
        if (ValidGeometryHelper.isValidAndNotEmpty(multiPolygon)) {
            return multiPolygon;
        } else {
            logger.error("multi Polygon object is empty or invalid");
            throw new RuntimeException("multi Polygon object is empty or invalid");
        }
    }

    public Geometry getGeometry(Collection geoList) {
        Geometry geometry = geometryFactory.buildGeometry(geoList);
        if (ValidGeometryHelper.isValidAndNotEmpty(geometry)) {
            return geometry;
        } else {
            logger.error("geometry object is empty or invalid");
            throw new RuntimeException("geometry object is empty or invalid");
        }
    }
}
