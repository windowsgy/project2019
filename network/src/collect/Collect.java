package collect;


import param.Param;
import start.ModelInterface;
import utils.LogInfo;

import java.util.List;

public class Collect implements ModelInterface {

    public void run() {
        LogInfo.linel1();
        if (!CollectParams.run()) {
            return;
        }
        LogInfo.linel2();
        List<Collect_Strut> listGatherStru = BuildCollectStrut.run();
        if (listGatherStru == null) {
            return;
        }
        LogInfo.linel2();
        if (!CollectThreadPool.run(listGatherStru,Param.pathMap.get("timeFormat"),Param.threadPool,Param.collectDebugOnOff,Param.currentLogFilePath)) {
            return;
        }
        Param.collectOnOff = true; //采集开关打开
        LogInfo.linel1();
    }
}
