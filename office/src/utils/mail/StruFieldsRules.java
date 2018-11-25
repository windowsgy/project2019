package utils.mail;

import java.util.Set;

/**
 * Created by jlgaoyuan on 2018/6/15.
 * 定义数据字段规则结构
 * 规则包括数据类型，数据长度，是否允许为""，是否允许为 null
 */
public class StruFieldsRules {

    private static String fieldName, fieldDataType;//定义字段名称，字段数据类型
    private static int lenght;//定义字段长度或数值范围
    private static String format;//定义时间字段格式
    private static long startLong;//起始范围
    private static long endLong;//结束范围
    private static int startInt;//起始范围
    private static int endInt;//结束范围
    private static double startDouble; //double起始范围
    private static double endDouble;//double结束范围
    private static boolean isNull;//是否允许为空
    private static Set set;//规则集合

    public void setFieldName(String value) {
        fieldName = value;
    }

    public void setFieldDataType(String value) {
        fieldDataType = value;
    }

    public void setLenght(int value) {
        lenght = value;
    }

    public void setStart(int value) {
        startInt = value;
    }

    public void setStart(long value) {
        startLong = value;
    }

    public void setStart(double value) {
        startDouble = value;
    }

    public void setEnd(int value) {
        endInt = value;
    }

    public void setEnd(long value) {
        endLong = value;
    }

    public void setEnd(double value) {
        endDouble = value;
    }

    public void setFormat(String value) {
        format = value;
    }

    public void setIsNull(boolean value) {
        isNull = value;
    }

    public void setSet( Set  value){ set = value;}

    public String getFieldName() {
        return fieldName;
    }

    public String getFieldDataType() {
        return fieldDataType;
    }

    public int getLenght() {
        return lenght;
    }

    public long getStartInt() {
        return startInt;
    }

    public long getEndInt() {
        return endInt;
    }

    public long getStartLong() {
        return startLong;
    }

    public long getEndLong() {
        return endLong;
    }

    public double getStartDouble() {
        return startDouble;
    }

    public double getEndDouble() {
        return endDouble;
    }

    public String getFormat() {
        return format;
    }

    public boolean isNull() {
        return isNull;
    }

    public Set getSet(){return set ;}

}
