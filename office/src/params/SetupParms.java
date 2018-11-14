package params;


import javaUtils.Log;

/**
 *
 * Created by jlgaoyuan on 2018/5/7.
 *
 */
public class SetupParms {

   public static void run(){

       Log.info("Init Path Parm");

       BuildParm.inputDatePath = BuildParm.inputDate+"\\";//时间格式路径

       BuildParm.INPUT_PATH = InitParm.MAIN_PATH+ InitParm.INPUT_PATHNAME;//输入路径
       BuildParm.SPLIT_PATH = InitParm.MAIN_PATH+ InitParm.SPLIT_PATHNAME;//拆分路径
       BuildParm.SPLIT_SUBPATH =  BuildParm.SPLIT_PATH+BuildParm.inputDatePath;//拆分文件子路径
       BuildParm.LOG_PATH = InitParm.MAIN_PATH+ InitParm.LOGS_PATHNAME;//日志路径

       BuildParm.SUMMARY_FILENAME = BuildParm.inputDate+"_summary.csv"; //摘要文件名
       BuildParm.DETAIL_FILENAME = BuildParm.inputDate+"_detail.csv";  //明细文件名


       BuildParm.SUMMARY_FILEPATH = BuildParm.INPUT_PATH+ BuildParm.SUMMARY_FILENAME;//输入文件路径
       BuildParm.DETAIL_FILEPATH = BuildParm.INPUT_PATH+ BuildParm.DETAIL_FILENAME;//拆分文件路径
       BuildParm.LOGS_FILEPATH = BuildParm.LOG_PATH+ InitParm.LOGS_FILENAME;//日志文件路径

   }


}
