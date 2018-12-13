package collect;


import base.Log;
import collect.stru.Stru_Collect;
import param.Param;
import start.ModelInterface;


import java.util.List;

public class Collect implements ModelInterface {

    public void run() {
        Log.linel1();
        if (!BuildCollectParams.run()) {
            return;
        }
        Log.linel2();
        List<Stru_Collect> collectList = BuildCollectList.run();
        if (collectList == null || collectList.size() <1) {
            Log.error("collectStru size is null or eq zero");
            return;
        }
        Log.linel2();
        if (!CollectThreadPool.run(collectList,Param.pathMap.get("timeFormat"),Param.threadPool,Param.currentLogFilePath)) {
            return;
        }
        Param.collectOnOff = true; //采集开关打开
        Log.linel1();
    }
}
