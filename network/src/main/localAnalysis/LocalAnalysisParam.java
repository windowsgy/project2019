package localAnalysis;

import inOut.Input;
import param.Param;
import utils.FileUtils;
import utils.LogInfo;

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
            LogInfo.error("local file is null");
            return false;
        }
        Set<String> localFilesSet = new HashSet<>(localFileNameList);
        Input.inputParam("localFileName",localFilesSet);
        if(!Param.inputStatus ){
            LogInfo.error("local file not setup :");
            return false;
        }
        Param.inputStatus = false;//重置输入状态

        Param.localFilePath = localFilesPath+Param.localFileName;
        System.out.println("localFilePath :"+Param.localFilePath);

        //本地分析类型
        Input.inputParam("localFileType",new HashSet<>(Param.localAnalysisMap.values()));
        if(!Param.inputStatus ){
            LogInfo.error("local file type not setup :");
            return false;
        }
        Param.inputStatus = false;//重置输入状态
        return true;

    }
}
