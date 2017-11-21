package com.domeastudio.mappingo.server.microservice.gisprocess.sdk.tools.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ucar.ma2.*;
import ucar.nc2.*;
import java.io.IOException;
import java.util.*;

import ucar.nc2.NetcdfFileWriter.Version;

public class RealTimeData2Netcdf {

    private static Logger logger = LoggerFactory.getLogger(RealTimeData2Netcdf.class);

    private static class SingletonHolder {
        private static final RealTimeData2Netcdf INSTANCE = new RealTimeData2Netcdf();
    }

    private RealTimeData2Netcdf() {
    }



    /**
     * 获取当前实例
     *
     * @return 获取当前对象的实例，是线程安全的
     */
    public static final RealTimeData2Netcdf getInstance() {
        return RealTimeData2Netcdf.SingletonHolder.INSTANCE;
    }

    public Boolean createNetcdfFile(String fileName,Group group,List<AxisObject> dimensions,DataObject dataObject) throws IOException {
        NetcdfFileWriter netcdfFileWriter=null;
        //if(!NetcdfFile.canOpen(fileName)){
        netcdfFileWriter=NetcdfFileWriter.createNew(Version.netcdf4,fileName);
        //}else{
        //logger.warn(fileName+" is already exists and will overwrite this file!");
        //netcdfFileWriter=NetcdfFileWriter.createNew(Version.netcdf4,fileName);
       // }
        List<Variable> dimVariables=new ArrayList<>();
        List<Dimension> dimensionList=new ArrayList<>();
        List<Integer> dimesRang=new ArrayList<>();
        for (AxisObject axisObject:dimensions) {
            Dimension dim=netcdfFileWriter.addDimension(group,axisObject.getDimName(),axisObject.getRange());
            Variable variable=netcdfFileWriter.addVariable(group,dim.getShortName(),axisObject.getDataType(),axisObject.getDimName());
            for (Attribute attribute:axisObject.getAttributes()) {
                netcdfFileWriter.addVariableAttribute(variable,attribute);
            }
            dimesRang.add(axisObject.getRange());
            dimVariables.add(variable);
            dimensionList.add(dim);
        }
        Variable variable = netcdfFileWriter.addVariable(group,dataObject.getShortName(), dataObject.getDataType(),dimensionList);
        for (Attribute attribute :dataObject.getAttributes()) {
            netcdfFileWriter.addVariableAttribute(variable, attribute);
        }
        try {
            netcdfFileWriter.create();
            Map<String,Array> arrayMap = createNetcdfAxis(dimensions);
            Array netcdfDataArray = createNetcdfData(dimesRang.toArray(new Integer[dimesRang.size()]),dataObject);
            for(Variable dimVariable : dimVariables){
                for(Map.Entry<String,Array> arrayEntry:arrayMap.entrySet()){
                    if(dimVariable.getShortName().equals(arrayEntry.getKey())){
                        netcdfFileWriter.write(dimVariable, arrayEntry.getValue());
                    }
                }
            }
            netcdfFileWriter.write(variable, netcdfDataArray);
            netcdfFileWriter.close();
            System.out.println("文件已存入"+fileName);
            return true;
        }catch (IOException e){
            e.printStackTrace();
            return false;
        }catch (InvalidRangeException e){
            e.printStackTrace();
            return false;
        }
    }
//
    private Map<String,Array> createNetcdfAxis(List<AxisObject> dimensions) {
        Map<String,Array> arrayList=new HashMap<>();
        for (AxisObject axisObject:dimensions) {
            Array array=Array.factory(axisObject.getDataType(),new int[]{axisObject.getRange()});
            int count=0;
            for(Object o:axisObject.getObjectDatas()){
                array.setObject(count,o);
                count++;
            }
            arrayList.put(axisObject.getDimName(),array);
        }
        return arrayList;
    }

    private Array createNetcdfData(Integer[] dimNum,DataObject dataObject){
        int[] d=new int[dimNum.length];
        for(int i=0;i<dimNum.length;i++){
            d[i]=dimNum[i].intValue();
        }
        Array array = Array.factory(dataObject.getDataType(),d);
        Index index=array.getIndex();
        for (Object o : dataObject.getObjectDatas()){
            for(int j=0;j<index.getSize();j++){
                array.setObject(index,o);
            }

        }
        return array;
    }

    public void dimReduction(){

    }

    public void readNetcdfFile(String fileName,List<String> params){
        NetcdfFile ncfile = null;
        try {
            ncfile = NetcdfFile.open(fileName);
            //read dimensions
            List<Dimension> list =  ncfile.getDimensions();
            for(Dimension d : list){
                System.out.println("name="+d.getShortName()+" length="+d.getLength());
            }
            //read variables
            List<Variable> variables = ncfile.getVariables();
            for(Variable v : variables){
                System.out.println("name="+v.getShortName()+" NameAndDimension="+v.getNameAndDimensions()+" ElementSize="+v.getElementSize());
            }

//            // 存经纬度
//            String var1 = "lon";// 此处严格区分大小写，不然找不到，不知道有什么变量的可以断点debug一下，鼠标移到上面ncfile那行看
//            String var2 = "lat";
//            Variable v1 = ncfile.findVariable(var1);
//            Variable v2 = ncfile.findVariable(var2);
//
//            float[][] lon = (float[][]) v1.read().copyToNDJavaArray();// 因为经纬度是二维的，直接
//            // copyToNDJavaArray结果就是二维的，强转一下就好，然后java的二维数组大家该咋用咋用就行了
//            float[][] lat = (float[][]) v2.read().copyToNDJavaArray();

        } catch (IOException ioe) {
        } finally {
            if (null != ncfile)
                try {
                    ncfile.close();
                } catch (IOException ioe) {
                }
        }
    }

//
//    public List<Variable> readNetcdfFile(){
//        return null;
//    }
//
//
    public static void main(String[] args) throws Exception {
        RealTimeData2Netcdf realTimeData2Netcdf=RealTimeData2Netcdf.getInstance();
        List<AxisObject> axisObjects=new ArrayList<>();
        AxisObject axisObject0=new AxisObject();
        AxisObject axisObject1=new AxisObject();
        AxisObject axisObject2=new AxisObject();
        AxisObject axisObject3=new AxisObject();
        //先经度后纬度
        axisObject1.setDimName("lon");//经度
        axisObject1.setDataType(DataType.DOUBLE);
        axisObject1.setRange(700);
        List<Attribute> attributes1=new ArrayList<>();
        Attribute attribute1_0=new Attribute("long_name","longitude");
        Attribute attribute1_1=new Attribute("units","Degrees_north");
        attributes1.add(attribute1_0);
        attributes1.add(attribute1_1);
        axisObject1.setAttributes(attributes1);
        List<Object> dimLonData=new ArrayList<>();
        for(int j=0;j<700;j++){
            Double d=123+(j*0.01);
            dimLonData.add(d);
        }
        axisObject1.setObjectDatas(dimLonData);

        axisObject0.setDimName("lat");//纬度
        axisObject0.setDataType(DataType.DOUBLE);
        axisObject0.setRange(400);
        List<Attribute> attributes0=new ArrayList<>();
        Attribute attribute0_0=new Attribute("long_name","latitude");
        Attribute attribute0_1=new Attribute("units","Degrees_east");
        attributes0.add(attribute0_0);
        attributes0.add(attribute0_1);
        axisObject0.setAttributes(attributes0);
        List<Object> dimLatData=new ArrayList<>();
        for(int i=0;i<400;i++){
            Double d=30-(i*0.01);
            dimLatData.add(d);
        }
        axisObject0.setObjectDatas(dimLatData);


        axisObject2.setDimName("alt");
        axisObject2.setDataType(DataType.DOUBLE);
        axisObject2.setRange(10);
        List<Attribute> attributes2=new ArrayList<>();
        Attribute attribute2_0=new Attribute("long_name","altitude");
        Attribute attribute2_1=new Attribute("units","meter");
        attributes2.add(attribute2_0);
        attributes2.add(attribute2_1);
        axisObject2.setAttributes(attributes2);
        List<Object> dimAltData=new ArrayList<>();
        for(int i=0;i<10;i++){
            Double d=0+(i*100.0);
            dimAltData.add(d);
        }
        axisObject2.setObjectDatas(dimAltData);

        axisObject3.setDimName("time");
        axisObject3.setDataType(DataType.LONG);
        axisObject3.setRange(2);
        List<Attribute> attributes3=new ArrayList<>();
        Attribute attribute3_0=new Attribute("long_name","time");
        Attribute attribute3_1=new Attribute("units","minute");
        attributes3.add(attribute3_0);
        attributes3.add(attribute3_1);
        axisObject3.setAttributes(attributes3);
        List<Object> dimTimeData=new ArrayList<>();
        Date date=new Date();
        for(int i=0;i<2;i++){
            Long d=date.getTime()+i;
            dimTimeData.add(d);
        }
        axisObject3.setObjectDatas(dimTimeData);

        axisObjects.add(axisObject0);
        axisObjects.add(axisObject1);
        axisObjects.add(axisObject2);
        axisObjects.add(axisObject3);

        DataObject dataObject=new DataObject();
        dataObject.setShortName("temperature");
        dataObject.setDataType(DataType.DOUBLE);
        List<Attribute> dataAttributes=new ArrayList<>();
        Attribute attribute4_0=new Attribute("long_name","temperature");
        Attribute attribute4_1=new Attribute("units","centigrade");
        dataAttributes.add(attribute4_0);
        dataAttributes.add(attribute4_1);
        dataObject.setAttributes(dataAttributes);
        List<Object> dataObjs=new ArrayList<>();
        Double[][][][] data = new Double[700][400][10][2];
        Integer max=50;
        Double min=0.0;
        Random random = new Random();
        for(int k=0;k<700;k++){
            for(int z=0;z<400;z++){
                for(int v=0;v<10;v++){
                    for(int x=0;x<2;x++){
                        data[k][z][v][x]=random.nextInt(max)%(max-min+1) + min;
                    }
                }
            }
        }
        dataObjs.add(data);
        dataObject.setObjectDatas(dataObjs);
        realTimeData2Netcdf.createNetcdfFile("/home/domea/test.nc",null,axisObjects,dataObject);
    }
}
