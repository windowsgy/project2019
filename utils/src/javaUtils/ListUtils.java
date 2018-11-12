package javaUtils;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by jlgaoyuan on 2018/5/5.
 * List 工具
 */
public class ListUtils {
    /**
     * 根据map 中的value进行判断， 返回所有 key List
     *
     * @param map   map
     * @param value 查找的value
     * @return list
     */
    public  List<String> keyList(Map<String, String> map, String value) {
        List<String> list = new ArrayList<>();
        for (String key : map.keySet()) {
            if (map.get(key).equals(value)) {
                list.add(key);
            }
        }
        return list;
    }


    /**
     * List 按分隔符拆分为  行、列 字段模式
     *
     * @param list      List
     * @param splitChar 分隔符
     * @return 行、列 列表
     */
    public  List<List<String>> list2ListFields(List<String> list, String splitChar) {
        String[] firstArray = list.get(0).split(splitChar,-1); //首行
        int firstArraySize = firstArray.length;
        List<List<String>> theList = new ArrayList<>();
        int lineCount = 0;
        for (String lists : list) {
            lineCount++;
            String[] array = lists.split(splitChar,-1);
            if (array.length == firstArraySize) {// 如果此行长度等于首行字段长度
                theList.add(Arrays.asList(array));
            } else {
                Log.error(lineCount + " :Line Error , Current Line Split Lenght : " + lists);
                return null;
            }
        }
        return theList;
    }



}
