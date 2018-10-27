package analysis;

import param.Param;

import java.util.ArrayList;
import java.util.List;

public class FilterList {
    public static List<String> run(List<String> list){
        List<String> newList = new ArrayList<>();
        for(String line:list){
           newList.add(line.replace(Param.filterStr,"").trim());
        }
        return newList;
    }
}
