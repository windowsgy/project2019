package localAnalysis;

import localAnalysis.localAnalysis.LocalAnalysisInterface;
import param.Param;
import start.ModelInterface;
import javaUtils.FileUtils;
import javaUtils.ListUtils;
import javaUtils.Log;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LocalAnalysis implements ModelInterface {

    @Override
    public  void run() {
        FileUtils fileUtils = new FileUtils();
        ListUtils listUtils = new ListUtils();
        //输入参数
        if (!LocalAnalysisParam.run()) {
            return;
        }
        //创建分析路径
        String analysisPath = Param.currentMainPath + Param.pathMap.get("analysis");
        fileUtils.createDir(analysisPath);

        Map<String, List<List<String>>> map = LoadExcelFile.run(Param.localFilePath);//加载文件
        //分析类型list
        List<String> analysisTypeList = listUtils.keyList(Param.localAnalysisMap, Param.localFileType);
        //循环所有分析类型
        for (String localAnalysisType : analysisTypeList) {
            //创建分析文件
            String analysisFilePath = analysisPath + localAnalysisType + ".txt";
            fileUtils.createFile(analysisFilePath);
            //截取数据
            String[] indexList = Param.fieldsIndexMap.get(localAnalysisType).split(",");
            List<Integer> intList = new ArrayList<>();
            for (String x : indexList) {
                Integer z = Integer.parseInt(x);
                intList.add(z);
            }
            List<List<String>> list = ExtractFields.run(map, Param.localAnalysisSubTableName,intList);
            //分析数据
            String result;
            String classPath = "localAnalysis.localAnalysis." + localAnalysisType;
            try {
                LocalAnalysisInterface analysis = (LocalAnalysisInterface) Class.forName(classPath).newInstance();
                Method n = analysis.getClass().getDeclaredMethod("run", List.class);
                n.setAccessible(true);
                result = (String) n.invoke(analysis, list);//result
                if (result == null) {
                    Log.error("Analysis error :" + localAnalysisType);
                } else {
                    fileUtils.wrStr2File(result, analysisFilePath, Param.charCode);
                    //  Log.info("Analysis Finished :"+analysisType);
                }

            } catch (Exception e) {
                Log.error(e.getMessage());
                Log.error("Analysis error :" + localAnalysisType);
            }
        }
    }
}
