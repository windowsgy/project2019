package localAnalysis;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class ExtractFields {

    /**
     * 抽取字段
     * @param map excel文件中的子表
     * @param subTableName 子表名
     * @param fieldsIndex 字段索引
     * @return 抽取后的数据
     */
    public static List<List<String>> run(Map<String,List<List<String>>> map ,String subTableName,List<Integer> fieldsIndex){
        List<List<String>> list =  map.get(subTableName);
        List<List<String>> newList = new ArrayList<>();
        for (List<String> fieldsList : list) {
            List<String> newFieldsList = new ArrayList<>();
            for (int j = 0; j < fieldsIndex.size(); j++) {
                newFieldsList.add(fieldsList.get(j));
            }
            newList.add(newFieldsList);
        }
        return newList;
    }
}
