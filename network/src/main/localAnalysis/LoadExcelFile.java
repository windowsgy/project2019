package localAnalysis;

import main.utils.ExcelUtils;

import java.util.List;
import java.util.Map;

public class LoadExcelFile {

    public static Map<String,List<List<String>>> run(String path){

       return ExcelUtils.xlsToString(path);
    }

}
