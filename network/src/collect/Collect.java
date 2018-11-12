package collect;


import param.Param;
import start.ModelInterface;
import utils.Log;

import java.util.List;

public class Collect implements ModelInterface {

    public void run() {
        Log.linel1();
        if (!BuildCollectParams.run()) {
            return;
        }
        Log.linel2();
        List<Stru_Collect> listGatherStru = BuildCollectStrut.run();
        if (listGatherStru == null) {
            return;
        }
        Log.linel2();
        if (!CollectThreadPool.run(listGatherStru,Param.pathMap.get("timeFormat"),Param.threadPool,Param.currentLogFilePath)) {
            return;
        }
        Param.collectOnOff = true; //采集开关打开
        Log.linel1();
    }
}
