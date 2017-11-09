package com.domeastudio.mappingo.server.microservice.gisprocess.sdk.tools;

import com.domeastudio.mappingo.server.microservice.gisprocess.sdk.tools.core.Geometry2WKT;
import com.domeastudio.mappingo.server.microservice.gisprocess.sdk.tools.core.GeometryType;
import com.domeastudio.mappingo.server.microservice.gisprocess.sdk.tools.core.WKT2Geometry;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.LinearRing;
import com.vividsolutions.jts.io.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by domea on 17-5-29.
 */
public final class GeometryFormateHelper {
    private static final long serialVersionUID = 1L;
    private static Logger logger = LoggerFactory.getLogger(GeometryFormateHelper.class);

    private static Geometry2WKT geometry2WKT = Geometry2WKT.getInstance();
    private static WKT2Geometry wkt2Geometry = WKT2Geometry.getInstance();

    /**
     * 获取几何对象的WKT字符串
     *
     * @param geometry
     * @return 返回WKT字符串
     */
    public static String getWKT(Geometry geometry) {
        return geometry2WKT.getWKT(geometry);
    }

    /**
     * 获取几何对象的EWKT字符串
     *
     * @param geometry
     * @return 返回WKT字符串
     */
    public static String getEWKT(Geometry geometry) {
        return geometry2WKT.getEWKT(geometry);
    }

    /**
     * 获取几何对象的EWKT字符串
     *
     * @param geometry
     * @return 返回WKT字符串
     */
    public static String getEWKT(Geometry geometry, Integer epsg) {
        return geometry2WKT.getEWKT(geometry, epsg);
    }

    /**
     * 获取几何对象的类型
     *
     * @param geometry
     * @return
     */
    public static GeometryType getGeometryType(Geometry geometry) {
        return geometry2WKT.getGeometryType(geometry);
    }

    /**
     * 拆分复杂几何对象成简单几何对象
     *
     * @param geometry
     * @return 返回WKT字符串列表
     */
    public static List<String> geometrySplit(Geometry geometry) {
        return geometry2WKT.geometrySplit(geometry);
    }

    /**
     * 拆分复杂几何对象成简单几何对象
     *
     * @param geometry
     * @return 返回WKT字符串列表
     */
    public List<String> geometrySplit4WKT(Geometry geometry) {
        return geometry2WKT.geometrySplit4WKT(geometry);
    }

    /**
     * 拆分复杂几何对象成简单几何对象
     *
     * @param geometry
     * @return 返回WKT字符串列表
     */
    public static List<String> geometrySplit(Geometry geometry, Integer epsg) {
        return geometry2WKT.geometrySplit(geometry, epsg);
    }

    public static Geometry getGeometry(String wkt) throws ParseException {
        return wkt2Geometry.getGeometry(wkt);
    }

    public static Geometry getGeometry(String wkt, Integer epsg) throws ParseException {
        return wkt2Geometry.getGeometry(wkt, epsg);
    }

    public static Geometry getGeometryExt(String ewkt) throws ParseException {
        return wkt2Geometry.getGeometryExt(ewkt);
    }

    public static LinearRing getLinearRing(Coordinate[] coordinates) {
        return wkt2Geometry.getLinearRing(coordinates);
    }

    /**
     * 合并多个几何对象成一个几何集合
     *
     * @return 返回WKT字符串
     */
    public static String geometryMerge(List<Geometry> geometryList) {
        return geometry2WKT.geometryMerge(geometryList);
    }


//    public static void main(String[] args) {
//        GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory(null);
//        Coordinate coord = new Coordinate(109.013388, 32.715519);
//        Point point = geometryFactory.createPoint(coord);
//        Point point1 = geometryFactory.createPoint(new Coordinate(100, 33));
//        MultiPoint multiPoint = geometryFactory.createMultiPoint(new Point[]{point, point1});
//        multiPoint.setSRID(4326);
//        GeometryCollection geometryCollection=geometryFactory.createGeometryCollection(new Geometry[]{point,point1,multiPoint});
//        //geometryCollection.setSRID(4326);
//        System.out.print(GeometryFormateHelper.geometrySplit(geometryCollection));
//    }
}
