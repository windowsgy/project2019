package currentParam;

import param.Param;

import start.ModelInterface;
import javaUtils.DateTimeUtils;
import javaUtils.FileUtils;
import javaUtils.Log;


public class Build_CurrentParam implements ModelInterface {

    public void run(){
        Log.info("Init Current Param");
        DateTimeUtils dtUtils = new DateTimeUtils();
        String timeStr = dtUtils.getCurTime(Param.pathMap.get("timeFormat"));
        Param.currentTimeStr = dtUtils.timeConvTimestamp(timeStr);
        //System.out.println("currentTime :"+Param.currentTimeStr);
        Param.currentMainPath = Param.pathMap.get("main")+"\\"+Param.currentTimeStr;
        FileUtils fileUtils = new FileUtils();//创建当前时间目录
        fileUtils.createDir(Param.currentMainPath);
        Log.info("currentMainPath :"+Param.currentMainPath);
        Param.currentParamOnOff = true;
        //采集开关设置关闭，未采集状态
        Param.collectOnOff = false;

    }
}
