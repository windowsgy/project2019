package analysis;

import param.Param;

import java.util.ArrayList;
import java.util.List;

public class FilterList {

    /**
     *
     * 过滤方法，根据规则规律掉数据，只返回符合规则的数据
     * @param list list
     * @return list
     */
    public static List<String> run(List<String> list){
        List<String> newList = new ArrayList<>();
        for(String line:list){
           newList.add(line.replace(Param.filterStr,"").trim());
        }
        return newList;
    }
}
