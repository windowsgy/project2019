package initParam;


import param.Param;
import utils.Log;


public class Init {

    /**
     * 构造参数
     *
     * @return boolean
     */
    public static  boolean run() {
        Log.info("Init Params Maps ");

        if(!Config.mapL1("path.properties",Param.pathMap)){
            return false;
        }
        if(!Config.mapL1("collect.properties",Param.collectMap)){
            return false;
        }
        if(!Config.mapL3("command.properties",Param.commandMap)){
            return false;
        }
        if(!Config.mapL1("format.properties",Param.formatMap)){
            return false;
        }
        if(!Config.mapL1("analysis.properties",Param.analysisMap)){
            return false;
        }
        if(!Config.mapL1("model.properties",Param.modelTypeMap)){
            return false;
        }
        if(!Config.mapL1("localAnalysis.properties",Param.localAnalysisMap)){
            return false;
        }
        if(!Config.mapL1("fieldsIndex.properties",Param.fieldsIndexMap)){
            return false;
        }


        Log.info("Init Params Finished");
        return true;
    }
}
