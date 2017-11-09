package com.domeastudio.mappingo.server.microservice.gisprocess.sdk.tools.core;

import com.vividsolutions.jts.geom.Geometry;
import org.geotools.data.shapefile.ShapefileDataStore;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.geojson.feature.FeatureJSON;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.opengis.feature.simple.SimpleFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * 将shp文件转换成GeoJSON字符串流
 */
public class Shp2GeoJson {

    private static Logger logger = LoggerFactory.getLogger(Shp2GeoJson.class);

    private static class SingletonHolder {
        private static final Shp2GeoJson INSTANCE = new Shp2GeoJson();
    }

    private Shp2GeoJson() {
    }

    /**
     * 获取当前实例
     *
     * @return 获取当前对象的实例，是线程安全的
     */
    public static final Shp2GeoJson getInstance() {
        return Shp2GeoJson.SingletonHolder.INSTANCE;
    }

    public String toGeoJson(String shpPath,String charSet) throws IOException, ParseException {
        File file=new File(shpPath);
        return toGeoJson(file.toURI().toURL(),charSet);
    }

    public String toGeoJson(URL shpUrl,String charSet) throws IOException, ParseException {
        FeatureJSON featureJSON=new FeatureJSON();
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("{\"type\": \"FeatureCollection\",\"features\": ");

        ShapefileDataStore shapefileDataStore=new ShapefileDataStore(shpUrl);
        shapefileDataStore.setCharset(Charset.forName(charSet));
        String typeName=shapefileDataStore.getTypeNames()[0];
        SimpleFeatureSource simpleFeatureSource=shapefileDataStore.getFeatureSource(typeName);
        SimpleFeatureCollection simpleFeatureCollection=simpleFeatureSource.getFeatures();
        SimpleFeatureIterator simpleFeatureIterator=simpleFeatureCollection.features();
        JSONArray jsonArray=new JSONArray();
        JSONParser jsonParser=new JSONParser();
        while (simpleFeatureIterator.hasNext()){
            SimpleFeature simpleFeature=simpleFeatureIterator.next();
            StringWriter stringWriter=new StringWriter();
            featureJSON.writeFeature(simpleFeature,stringWriter);
            JSONObject jsonObject=(JSONObject)jsonParser.parse(stringWriter.toString());
            jsonArray.add(jsonObject);
        }
        simpleFeatureIterator.close();
        stringBuffer.append(jsonArray.toJSONString());
        stringBuffer.append("}");
        return stringBuffer.toString();
    }

    public String toGeoJson(Geometry geometry){
        return null;
    }

//    public static void main(String[] args) throws IOException, ParseException {
//        Shp2GeoJson shp2GeoJson=Shp2GeoJson.getInstance();
//        String ss=shp2GeoJson.toGeoJson("/home/domea/myData/1г║400═ЄSHP/╩╫╢╝║═╩б╗с_point.shp","GBK");
//        System.out.println(ss);
//    }
}
