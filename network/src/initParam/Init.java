package initParam;

import param.Param;
import javaUtils.LoadProperties;
import javaUtils.Log;

/**
 * 初始化方法
 */
public class Init {

    /**
     * 构造参数
     * @return boolean
     */
    public static  boolean run() {
        Log.info("Init Params Maps ");
        if(!LoadProperties.paramMap("path.properties",Param.pathMap)){
            return false;
        }
        if(!LoadProperties.paramMap("collect.properties",Param.collectMap)){
            return false;
        }
        if(!LoadProperties.paramMapL3("command.properties",Param.commandMap)){
            return false;
        }
        if(!LoadProperties.paramMap("format.properties",Param.formatMap)){
            return false;
        }
        if(!LoadProperties.paramMap("analysis.properties",Param.analysisMap)){
            return false;
        }
        if(!LoadProperties.paramMap("model.properties",Param.modelTypeMap)){
            return false;
        }
        if(!LoadProperties.paramMap("localAnalysis.properties",Param.localAnalysisMap)){
            return false;
        }
        if(!LoadProperties.paramMap("fieldsIndex.properties",Param.fieldsIndexMap)){
            return false;
        }
        Log.info("Init Params Finished");
        return true;
    }
}
