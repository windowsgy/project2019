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
        Config build = new Config();
        Log.info("Init Params Maps ");
        if(!build.paramMap("path.properties",Param.pathMap)){
            return false;
        }
        if(!build.paramMap("collect.properties",Param.collectMap)){
            return false;
        }
        if(!build.paramMapL3("command.properties",Param.commandMap)){
            return false;
        }
        if(!build.paramMap("format.properties",Param.formatMap)){
            return false;
        }
        if(!build.paramMap("analysis.properties",Param.analysisMap)){
            return false;
        }
        if(!build.paramMap("model.properties",Param.modelTypeMap)){
            return false;
        }
        if(!build.paramMap("localAnalysis.properties",Param.localAnalysisMap)){
            return false;
        }
        if(!build.paramMap("fieldsIndex.properties",Param.fieldsIndexMap)){
            return false;
        }

        Log.info("Init Params Finished");
        return true;
    }
}
