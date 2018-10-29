package utils;


import java.util.*;
import java.util.regex.Pattern;

/**
 * Created by jlgaoyuan on 2018/5/5.
 * List 工具
 */
public class ListUtils {


    /**
     * 根据value 返回所有 key list
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
     * List To List Array
     *
     * @param list      List
     * @param splitChar SplitChar
     * @return List Array
     */
    public  List<String[]> list2ListArray(List<String> list, String splitChar) {
        String[] firstArray = list.get(0).split(splitChar); //首行
        int firstArraySize = firstArray.length;
        //  LogInfo.info("First Array Size:"+firstArraySize);
        List<String[]> listArray = new ArrayList<>();
        int lineCount = 0;
        for (String aList : list) {
            lineCount++;
            String[] array = aList.split(splitChar);
            if (array.length != firstArraySize) {
                LogInfo.info(lineCount + " :Line Error : " + aList);
                return null;
            }
            listArray.add(aList.split(splitChar));
        }
        return listArray;
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
                LogInfo.error(lineCount + " :Line Error , Current Line Split Lenght : " + lists);
                return null;
            }
        }
        return theList;
    }


    /**
     * @param listArray List
     * @param index     index
     * @return List
     */
    public  List<String> listArrField(List<String[]> listArray, int index) {
        List<String> list = new ArrayList<>();
        for (String[] aListArray : listArray) {
            list.add(aListArray[index]);
        }
        return list;
    }

    /**
     * @param list  List
     * @param regex regex
     * @return List
     */
    public  List<String> listFilter(List<String> list, String regex) {
        List<String> filterList = new ArrayList<>();
        for (String line : list) {
            if (Pattern.matches(regex, line)) {
                filterList.add(line);
            }
        }
        return filterList;
    }


}
