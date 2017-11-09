package com.domeastudio.mappingo.server.microservice.gisprocess.sdk.tools.core;

import com.domeastudio.mappingo.server.microservice.gisprocess.utils.ChangeStringCharset;
import com.vividsolutions.jts.geom.*;
import org.geotools.data.FeatureWriter;
import org.geotools.data.Transaction;
import org.geotools.data.shapefile.ShapefileDataStore;
import org.geotools.data.shapefile.ShapefileDataStoreFactory;
import org.geotools.feature.simple.SimpleFeatureTypeBuilder;
import org.geotools.geojson.geom.GeometryJSON;
import org.geotools.referencing.crs.DefaultGeographicCRS;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.referencing.crs.GeographicCRS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 将GeoJSON字符串流或文件转换成Shp文件，存入文件系统中
 */
public class GeoJson2Shp {
    private static Logger logger = LoggerFactory.getLogger(GeoJson2Shp.class);

    private static class SingletonHolder {
        private static final GeoJson2Shp INSTANCE = new GeoJson2Shp();
    }

    private GeoJson2Shp() {
    }



    /**
     * 获取当前实例
     *
     * @return 获取当前对象的实例，是线程安全的
     */
    public static final GeoJson2Shp getInstance() {
        return GeoJson2Shp.SingletonHolder.INSTANCE;
    }

    private void toShpFile(String geoJson, String charSet, String filePath, GeographicCRS crs){
        try{
            GeometryJSON geometryJSON=new GeometryJSON();
            JSONParser jsonParser=new JSONParser();
            Map<Integer,String> map=new HashMap<Integer, String>();
            JSONObject jsonObject = (JSONObject)jsonParser.parse(geoJson);
            JSONArray features = (JSONArray) jsonObject.get("features");
            JSONObject feature0 = (JSONObject)features.get(0);
            JSONObject properties=(JSONObject) feature0.get("properties");
            Class<?> geoType = getGeometryClass(geoJson);
            //创建shape文件对象
            File file = createFile(filePath);
            Map<String, Serializable> params = new HashMap<String, Serializable>();
            params.put(ShapefileDataStoreFactory.URLP.key, file.toURI().toURL());
            params.put(ShapefileDataStoreFactory.DBFCHARSET.key,charSet);
            ShapefileDataStore shapefileDataStore = (ShapefileDataStore) new ShapefileDataStoreFactory().createNewDataStore(params);
//            //定义图形信息和属性信息
            SimpleFeatureTypeBuilder simpleFeatureTypeBuilder = createSimpleFeatureTypeBuilder(crs, map, properties, geoType,charSet);
            shapefileDataStore.createSchema(simpleFeatureTypeBuilder.buildFeatureType());
//            //设置编码
            shapefileDataStore.setCharset(Charset.forName(charSet));
//            //设置Writer
            FeatureWriter<SimpleFeatureType, SimpleFeature> featureWriter = shapefileDataStore.getFeatureWriter(shapefileDataStore.getTypeNames()[0], Transaction.AUTO_COMMIT);
            for(int i=0,len=features.size();i<len;i++){
                String strFeature = features.get(i).toString();
                Reader reader = new StringReader(strFeature);
                SimpleFeature feature = featureWriter.next();
                feature.setAttribute("the_geom",geometryJSON.read(reader));
                feature.setAttribute("FID",i);
                for(int j=0;j<map.size();j++){
                    Object value=((JSONObject)((JSONObject)features.get(i)).get("properties")).get(map.get(j));
                    feature.setAttribute(map.get(j),value);
                }
                featureWriter.write();
            }
            featureWriter.close();
            shapefileDataStore.dispose();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    private SimpleFeatureTypeBuilder createSimpleFeatureTypeBuilder(GeographicCRS crs, Map<Integer, String> map, JSONObject properties, Class<?> geoType,String charSet) throws UnsupportedEncodingException {
        SimpleFeatureTypeBuilder simpleFeatureTypeBuilder = new SimpleFeatureTypeBuilder();
        simpleFeatureTypeBuilder.setCRS(crs);
        simpleFeatureTypeBuilder.setName("shapefile");
        simpleFeatureTypeBuilder.add("the_geom", geoType);
        simpleFeatureTypeBuilder.add("FID", Long.class);
        Iterator iterator = properties.keySet().iterator();
        int count=0;
        while (iterator.hasNext()) {
            String key = (String)iterator.next();
            key= ChangeStringCharset.changeCharset(key,charSet);
            if(key.length()>10){
                logger.error("FieldName "+key+" is longer than 10 characters,truncating to "+ key.substring(0,10));
                throw new IllegalArgumentException("FieldName "+key+" is longer than 10 characters,truncating to "+ key.substring(0,10));
            }
            map.put(count,key);
            count++;
            simpleFeatureTypeBuilder.add(key,String.class);
        }
        return simpleFeatureTypeBuilder;
    }

    private Class<?> getGeometryClass(String geoJson) throws ParseException {
        JSONParser jsonParser=new JSONParser();
        JSONObject jsonObject = (JSONObject)jsonParser.parse(geoJson);
        JSONArray features = (JSONArray) jsonObject.get("features");
        JSONObject feature0 = (JSONObject)features.get(0);
        String strType = ((JSONObject)feature0.get("geometry")).get("type").toString();
        GeometryType geometryType=GeometryType.valueOf(strType.toUpperCase());
        Class<?> geoType = null;
        switch(geometryType){
            case POINT:
                geoType = Point.class;
                break;
            case MULTIPOINT:
                geoType = MultiPoint.class;
                break;
            case LINESTRING:
                geoType = LineString.class;
                break;
            case MULTILINESTRING:
                geoType = MultiLineString.class;
                break;
            case POLYGON:
                geoType = Polygon.class;
                break;
            case MULTIPOLYGON:
                geoType = MultiPolygon.class;
                break;
        }
        return geoType;
    }

    private File createFile(String filePath) throws IOException {
        File file = new File(filePath);
        if(!file.exists()){
            file.createNewFile();
        }else{
            file.delete();
            file.createNewFile();
        }
        return file;
    }

    public void toShpFile(URI jsonPath, String charSet) throws IOException {
        File file=new File(jsonPath);
        FileInputStream fileInputStream=new FileInputStream(file);
        InputStreamReader inputStreamReader=new InputStreamReader(fileInputStream,charSet.toString());
        BufferedReader bufferedReader=new BufferedReader(inputStreamReader);
        StringBuffer stringBuffer=new StringBuffer();
        String bufferStr;
        while ((bufferStr=bufferedReader.readLine())!=null){
            stringBuffer.append(bufferStr);
        }
        bufferedReader.close();
        toShpFile(stringBuffer.toString(),charSet,createShpPath(),getGeoJSONCrs(stringBuffer.toString()));
    }

    private String createShpPath(){
        //todo:文件系统中创建shp文件
        return null;
    }

    private GeographicCRS getGeoJSONCrs(String geoJSON){
        //todo:从geoJSON中获取CRS坐标定义，若无则返回WGS84作为默认坐标系
        return DefaultGeographicCRS.WGS84;
    }

    public void toShpFile(String jsonPath,String charSet) throws IOException {
        File file=new File(jsonPath);
        toShpFile(file.toURI(),charSet);
    }

//    public static void main(String[] args) throws IOException {
//        GeoJson2Shp geoJson2Shp=GeoJson2Shp.getInstance();
//        String ss= "/home/domea/test/dd.json";
//        geoJson2Shp.toShpFile(ss,"GBK");
//    }
}
