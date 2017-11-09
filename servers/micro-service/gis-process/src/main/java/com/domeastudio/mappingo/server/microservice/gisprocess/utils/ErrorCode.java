package com.domeastudio.mappingo.server.microservice.gisprocess.utils;

/**
 * 1:成功
 * 2：警告
 * 3:失败
 * 4:未知
 */
public enum ErrorCode {
    dataBaseStartSuccess("1000", "数据库启动成功"),
    dataBaseStartFaile("3000", "数据库启动失败"),
    dataBaseSearchFaile("3100", "数据库查询失败"),
    dataBaseAddFaile("3200", "数据库添加失败"),
    dataBaseDeleteFaile("3300", "数据库记录删除失败"),
    dataTypeInequality("3400", "数据类型不相同"),
    conversionFaile("2000", "地理数据转换失败"),
    unknown("4000", "未知错误请查看后台堆栈错误");

    private String key;
    private String value;

    //自定义的构造函数，参数数量，名字随便自己取
    //构造器默认也只能是private, 从而保证构造函数只能在内部使用
    private ErrorCode(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    //重新toString方法，默认的toString方法返回的就是枚举变量的名字，和name()方法返回值一样
    @Override
    public String toString() {
        return this.key + ":" + this.value;

    }

}
