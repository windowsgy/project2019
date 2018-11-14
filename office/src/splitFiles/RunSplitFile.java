package splitFiles;




import javaUtils.Log;
import params.InputParm;
import params.SetupParms;

import java.util.List;
import java.util.Map;


/**
 *
 * Created by jlgaoyuan on 2018/5/4.
 *
 */
public class RunSplitFile {

    /**
     *
     */
    public static void run(){
        Log.info("Start Split Files");
        Log.linel0();
        if(!InputParm.run()){
         return;
        }
        Log.linel0();
        SetupParms.run();
        Log.linel0();
        if(!CheckFiles.run()){//文件检查
            return;
        }
        Log.linel0();
        if(!Load.run()){//加载文件
            return;
        }
        Log.linel0();
        if(!CheckFileInfo.run()){//文件信息检查
            return;
        }
        Map<String,List<String>> map =  SplitInfo.run();//拆分文件结构
        Log.linel0();
        WrMap.run(map); ///拆分文件写入
        Log.linel0();
        Log.info("Split Files End");
    }
}
