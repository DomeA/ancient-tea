package com.domeastudio.mappingo.server.microservice.gisprocess.sdk.tools;

import com.vividsolutions.jts.geom.Geometry;
import org.geotools.geometry.jts.JTS;
import org.geotools.referencing.CRS;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.ReferenceIdentifier;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.opengis.referencing.operation.MathTransform;
import org.opengis.referencing.operation.TransformException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by domea on 17-5-29.
 */
public final class CoordinateTransformationHelper {
    private static final long serialVersionUID = 1L;
    private static Logger logger = LoggerFactory.getLogger(CoordinateTransformationHelper.class);

    public static Geometry transform(Geometry geometry, CoordinateReferenceSystem sourceCRS, CoordinateReferenceSystem targetCRS) throws FactoryException, TransformException {
        MathTransform mathTransform = CRS.findMathTransform(sourceCRS, targetCRS);
        Geometry targetGeometry = JTS.transform(geometry, mathTransform);
        if (ValidGeometryHelper.isValidAndNotEmpty(targetGeometry)) {
            return targetGeometry;
        } else {
            logger.error("transformed geometry is empty or invalid");
            throw new RuntimeException("transformed geometry is empty or invalid");
        }
    }

    public static Geometry transform(Geometry geometry, String sourceWKT, String targetWKT) throws FactoryException, TransformException {
        CoordinateReferenceSystem sourceCRS = CRS.parseWKT(sourceWKT);
        CoordinateReferenceSystem targetCRS = CRS.parseWKT(targetWKT);
        MathTransform mathTransform = CRS.findMathTransform(sourceCRS, targetCRS);
        Geometry targetGeometry = JTS.transform(geometry, mathTransform);
        if (ValidGeometryHelper.isValidAndNotEmpty(targetGeometry)) {
            return targetGeometry;
        } else {
            logger.error("transformed geometry is empty or invalid");
            throw new RuntimeException("transformed geometry is empty or invalid");
        }
    }

    public static Geometry transform(Geometry geometry, Integer sourceEPSG, Integer targetEPSG) throws FactoryException, TransformException {
        CoordinateReferenceSystem sourceCRS = CRS.decode("EPSG:" + sourceEPSG);
        CoordinateReferenceSystem targetCRS = CRS.decode("EPSG:" + targetEPSG);
        MathTransform mathTransform = CRS.findMathTransform(sourceCRS, targetCRS);
        Geometry targetGeometry = JTS.transform(geometry, mathTransform);
        if (ValidGeometryHelper.isValidAndNotEmpty(targetGeometry)) {
            return targetGeometry;
        } else {
            logger.error("transformed geometry is empty or invalid");
            throw new RuntimeException("transformed geometry is empty or invalid");
        }
    }

    public static String getWKT(CoordinateReferenceSystem crs) {
        return crs.toWKT();
    }

    public static String getWKT(Integer epsg) throws FactoryException {
        CoordinateReferenceSystem crs = CRS.decode("EPSG:" + epsg);
        return crs.toWKT();
    }

    public static String getName(CoordinateReferenceSystem crs) {
        return crs.getName().toString();
    }

    public static String getName(Integer epsg) throws FactoryException {
        CoordinateReferenceSystem crs = CRS.decode("EPSG:" + epsg);
        return crs.getName().toString();
    }

    public static String getEPSG(CoordinateReferenceSystem crs) {
        if (crs.getIdentifiers().size() > 0) {
            Object[] objects = crs.getIdentifiers().toArray();
            return ((ReferenceIdentifier) objects[0]).toString();
        } else {
            logger.error("this coordinate system has no epsg code.");
            throw new RuntimeException("this coordinate system has no epsg code.");
        }
    }

    public static String getEPSG(String wkt) throws FactoryException {
        CoordinateReferenceSystem crs = CRS.parseWKT(wkt);
        return getEPSG(crs);
    }

//    public static void main(String[] args) throws FactoryException, ParseException, TransformException {
//        String wkt="PROJCS[\"WGS 84 / UTM zone 50N\", \n" +
//                "GEOGCS[\"WGS 84\", DATUM[\"WGS_1984\", SPHEROID[\"WGS 84\", 6378137, 298.257223563, AUTHORITY[\"EPSG\", \"7030\"]], AUTHORITY[\"EPSG\", \"6326\"]], PRIMEM[\"Greenwich\", 0, AUTHORITY[\"EPSG\", \"8901\"]], UNIT[\"degree\", 0.0174532925199433, AUTHORITY[\"EPSG\", \"9122\"]], AUTHORITY[\"EPSG\", \"4326\"]], \n" +
//                "PROJECTION[\"Transverse_Mercator\"], \n" +
//                "PARAMETER[\"latitude_of_origin\", 0], \n" +
//                "PARAMETER[\"central_meridian\", 117], \n" +
//                "PARAMETER[\"scale_factor\", 0.9996], \n" +
//                "PARAMETER[\"false_easting\", 500000], \n" +
//                "PARAMETER[\"false_northing\", 0], \n" +
//                "UNIT[\"metre\", 1, AUTHORITY[\"EPSG\", \"9001\"]], \n" +
//                "AUTHORITY[\"EPSG\", \"32650\"]]";
//        System.out.println(CoordinateTransformationHelper.getEPSG(wkt));
//
//        String wkto="Point(118 32)";
//        Geometry g=GeometryFormateHelper.getGeometry(wkto);
//        Geometry p=CoordinateTransformationHelper.transform(g, 4326, 3826);
//        System.out.println(p.toText());
//    }
}
