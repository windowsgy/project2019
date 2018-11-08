package analysis;

import inOut.Input;
import param.Param;
import utils.FileUtils;
import utils.Log;

import java.util.HashSet;

public class AnalysisParam {

    public static boolean run() {
        if (Param.collectOnOff) {//如果当前参数采集成功过采集开关打开状态，提示是否加载当前采集数据
            Log.info("currentCollectFilePath :" + Param.currentCollectPath);
            String info = " :Whether to load the currentCollectPath ? ";
            Input.inputParam(info);
            if (Param.inputStatus) {//是否加载当前采集数据路径
                Param.currentFormatSourcePath = Param.currentCollectPath;
                Param.inputStatus = false;
                Log.info("currentFormatSourcePath :" + Param.currentFormatSourcePath);
                return true;
            } else {
                return setCollectPath();
            }
        } else {
            return setCollectPath();
        }
    }

    /**
     * 设置采集路径
     * @return 是否路径存在
     */
    private static boolean setCollectPath() {
        //输入当前时间格式
        Input.inputPath("currentTimeStr", Param.pathMap.get("main") + "\\", true);
        if (!Param.inputStatus) {
            return false;
        }
        Param.inputStatus = false;
        Param.currentMainPath = Param.pathMap.get("main") + "\\" + Param.currentTimeStr;
        //输入采集类型
        Input.inputParam("currentCollectType", new HashSet<>(Param.formatMap.values()));
        if (!Param.inputStatus) {
            return false;
        }
        Param.inputStatus = false;
        String basePath = Param.currentMainPath + Param.pathMap.get("collect");
        Param.currentFormatSourcePath = basePath + Param.currentCollectType;
        FileUtils fileUtils = new FileUtils();
        if (!fileUtils.isDir(Param.currentFormatSourcePath)) {
            Log.error("formatSourcePath is not exist .");
            return false;
        }
        Log.info("currentFormatSourcePath :" + Param.currentFormatSourcePath);
        return true;
    }
}
