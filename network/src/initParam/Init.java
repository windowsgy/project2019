package initParam;


import param.Param;
import utils.Config;
import utils.Log;


public class Init {

    /**
     * 构造参数
     *
     * @return boolean
     */
    public static  boolean run() {
        Log.info("Init Params Maps ");
        if(!Config.paramMap("path.properties",Param.pathMap)){
            return false;
        }
        if(!Config.paramMap("collect.properties",Param.collectMap)){
            return false;
        }
        if(!Config.paramMapL3("command.properties",Param.commandMap)){
            return false;
        }
        if(!Config.paramMap("format.properties",Param.formatMap)){
            return false;
        }
        if(!Config.paramMap("analysis.properties",Param.analysisMap)){
            return false;
        }
        if(!Config.paramMap("model.properties",Param.modelTypeMap)){
            return false;
        }
        if(!Config.paramMap("localAnalysis.properties",Param.localAnalysisMap)){
            return false;
        }
        if(!Config.paramMap("fieldsIndex.properties",Param.fieldsIndexMap)){
            return false;
        }
        Log.info("Init Params Finished");
        return true;
    }
}
