package com.domeastudio.mappingo.server.microservice.gisprocess.sdk.tools;

import com.vividsolutions.jts.geom.*;
import com.vividsolutions.jts.triangulate.ConformingDelaunayTriangulationBuilder;
import com.vividsolutions.jts.triangulate.DelaunayTriangulationBuilder;

/**
 * Created by domea on 17-5-29.
 */
public final class DelaunayArithmeticHelper {

    public static Geometry delaunayArithmetic4Edges(Geometry geometry) {
        if (geometry.getCoordinates().length > 2) {
            DelaunayTriangulationBuilder delaunayTriangulationBuilder = new DelaunayTriangulationBuilder();
            delaunayTriangulationBuilder.setSites(geometry);
            return delaunayTriangulationBuilder.getEdges(new GeometryFactory());
        } else {
            return null;
        }
    }

    public static Geometry delaunayArithmetic4Triangles(Geometry geometry) {
        if (geometry.getCoordinates().length > 2) {
            DelaunayTriangulationBuilder delaunayTriangulationBuilder = new DelaunayTriangulationBuilder();
            delaunayTriangulationBuilder.setSites(geometry);
            return delaunayTriangulationBuilder.getTriangles(new GeometryFactory());
        } else {
            return null;
        }
    }

    public static Geometry conformingDelaunayArithmetic4Edges(Geometry geometry, Geometry constraintLines) {
        if (geometry.getCoordinates().length > 2) {
            ConformingDelaunayTriangulationBuilder conformingDelaunayTriangulationBuilder = new ConformingDelaunayTriangulationBuilder();
            conformingDelaunayTriangulationBuilder.setSites(geometry);
            conformingDelaunayTriangulationBuilder.setConstraints(constraintLines);
            return conformingDelaunayTriangulationBuilder.getEdges(new GeometryFactory());
        } else {
            return null;
        }
    }

    public static Geometry conformingDelaunayArithmetic4Triangles(Geometry geometry, Geometry constraintLines) {
        if (geometry.getCoordinates().length > 2) {
            ConformingDelaunayTriangulationBuilder conformingDelaunayTriangulationBuilder = new ConformingDelaunayTriangulationBuilder();
            conformingDelaunayTriangulationBuilder.setSites(geometry);
            conformingDelaunayTriangulationBuilder.setConstraints(constraintLines);
            return conformingDelaunayTriangulationBuilder.getTriangles(new GeometryFactory());
        } else {
            return null;
        }
    }

//    public static void main(String[] args) {
//        Coordinate[] coordinates = new Coordinate[]{
//                new Coordinate(108, 34),
//                new Coordinate(109, 45),
//                new Coordinate(107, 50),
//                new Coordinate(106, 24),
//                new Coordinate(107, 28)
//        };
//        Geometry geometry = (new GeometryFactory()).createMultiPoint(coordinates);
//        Geometry geometry1 = DelaunayArithmeticHelper.delaunayArithmetic4Triangles(geometry);
//    }
}
