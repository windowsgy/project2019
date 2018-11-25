package splitFiles;

import base.FileUtils;
import base.Log;
import params.BuildParm;

import java.util.Scanner;


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
        //检查拆分文件是否存在
        Log.info("File Check");
        FileUtils fileUtils = new FileUtils();
        if(fileUtils.isDir(BuildParm.SPLIT_SUBPATH)){//检查拆分文件
            Log.warn("Split Files Is Exist "+BuildParm.SPLIT_SUBPATH);
            Scanner input = new Scanner(System.in);
            Log.info("delete Split Files(Y/N)");
            String deleteFilesOnOff = input.nextLine().trim();
            if(deleteFilesOnOff.equals("Y")||deleteFilesOnOff.equals("y")){
               fileUtils.deleteFiles(BuildParm.SPLIT_SUBPATH);
               fileUtils.delDir(BuildParm.SPLIT_SUBPATH);
            }else{
               return false;
            }
        }
        if(!fileUtils.isFile(BuildParm.SUMMARY_FILEPATH)){//检查摘要文件
            Log.error("summary File Not Exist "+BuildParm.SUMMARY_FILEPATH);
            return false;
        }else if(!fileUtils.isFile(BuildParm.DETAIL_FILEPATH)){//检查明细文件
            Log.error("detail File Not Exist "+BuildParm.DETAIL_FILEPATH);
            return false;
        }
        Log.info("File Check Succeed");
            return true;
    }
}
