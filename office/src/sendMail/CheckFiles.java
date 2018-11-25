package sendMail;


import base.FileUtils;
import base.Log;
import params.BuildParm;

/**
 *
 * Created by jlgaoyuan on 2018/5/4.
 */
public class CheckFiles {

    /**
     * 文件检查
     * @return 检查是否通过
     */
    public static boolean run(){
        FileUtils fileUtils = new FileUtils();
        //检查拆分文件是否存在
        Log.info("File Check");
        if(!fileUtils.isDir(BuildParm.SPLIT_SUBPATH)){//检查拆分文件
            Log.error("Split Dir Not Exist"+BuildParm.SPLIT_SUBPATH);
            return false;
        }
        if(!fileUtils.isFile(BuildParm.SUMMARY_FILEPATH)){//检查摘要文件
            Log.error("Summary Files Not Exist"+BuildParm.SUMMARY_FILEPATH);
            return false;
        }
        Log.info("File Check Succeed");
        return true;
    }
}
