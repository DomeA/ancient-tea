package com.domeastudio.mappingo.server.microservice.gisprocess.sdk.tools;

import com.vividsolutions.jts.geom.Point;

import java.util.List;

/**
 * Created by domea on 17-5-29.
 */
public final class VoronoiArithmeticHelper {
    private List<Point> pointList = null;

//    VoronoiDiagramBuilder voronoiDiagramBuilder = new VoronoiDiagramBuilder();
//    List<Coordinate> coords = new ArrayList<Coordinate>();
//    Envelope clipEnvelpoe = new Envelope();
//    Random random = new Random(100);
//    for(int i = 0;i<100;i++){
//        //使用过程中一把每个坐标需要配置一个属性数据，但是这个coord没法附带一个属性，
//        //否则剖分之后不知道多边形对应的点位
//        //可以使用coordinate的z值，作为一个唯一标志位
//        //剖分后的结果中，每个多边形会有一个getUserData方法，这个方法返回的对象就是
//        //对应的coordinate点位，x,y,z相同，但是地址不一样，根据z就能找到多边形对应的属性
//        //另一种方法是使用coordinate的hashCode也可以
//        Coordinate coord = new Coordinate(random.nextDouble(), random.nextDouble(), i);
//        //coord.setUserData(i);
//        coords.add(coord);
//        clipEnvelpoe.expandToInclude(coord);
//        if (i == 80) {
//            System.out.println(coord.hashCode());
//        }
//    }
//
//   /* coords.add(new Coordinate(2,0));
//    coords.add(new Coordinate(2,2));
//    coords.add(new Coordinate(0,2));
//    coords.add(new Coordinate(1,1));*/
//    voronoiDiagramBuilder.setSites(coords);
//    voronoiDiagramBuilder.setClipEnvelope(clipEnvelpoe);
//    Geometry geom = voronoiDiagramBuilder.getDiagram(JTSFactoryFinder.getGeometryFactory());
//    //System.out.println(geom.getGeometryN(0).getClass());
//    List<Geometry> geoms = new ArrayList<Geometry>();
//    for(
//    int i = 0;i<geom.getNumGeometries();i++)
//
//    {
//        //下面这个输出语句很重要，用来找到这个多边形对应的原始点位坐标
//        System.out.println(geom.getGeometryN(i).getUserData().hashCode());
//        geoms.add(geom.getGeometryN(i));
//    }
//    ShortestPath.showGeometry(geom);
}
