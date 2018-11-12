package localAnalysis;

import inOut.Input;
import param.Param;
import utils.FileUtils;
import utils.Log;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LocalAnalysisParam {

    public static boolean run() {
        FileUtils fileUtils = new FileUtils();

        //local path 相关参数设置####################################################
        String localFilesPath = Param.pathMap.get("main")+Param.pathMap.get("local");
        System.out.println("localFilesPath :"+localFilesPath);

        List<String> localFileNameList = fileUtils.getFileNameToList(localFilesPath);
        if(localFileNameList == null){
            Log.error("local file is null");
            return false;
        }
        Set<String> localFilesSet = new HashSet<>(localFileNameList);
        Input.inputParam("localFileName",localFilesSet);
        if(!Input.inputStatus ){
            Log.error("local file not setup :");
            return false;
        }

        Param.localFilePath = localFilesPath+Param.localFileName;
        System.out.println("localFilePath :"+Param.localFilePath);

        //本地分析类型
        Input.inputParam("localFileType",new HashSet<>(Param.localAnalysisMap.values()));
        if(!Input.inputStatus ){
            Log.error("local file type not setup :");
            return false;
        }
        return true;

    }
}
