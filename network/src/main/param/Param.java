package param;



import java.util.*;


public class Param {

    public static Map<String,String> modelTypeMap = new HashMap<>();//模块类型集合
    public static Map<String,Map<String,Map<String,String>>> commandMap = new HashMap<>();//命令集合
    public static Map<String,String> formatMap = new HashMap<>();//格式化集合
    public static Map<String,String> analysisMap = new HashMap<>();//分析参数
    public static Map<String,String> collectMap = new HashMap<>();//采集参数
    public static Map<String,String> pathMap = new HashMap<>();//采集参数

    public static String currentTimeStr ;//当前采集时间参数路径格式
    public static String currentMainPath;//当前时间目录
    public static String currentModelType;//当前模块类型
    public static String currentSystemType;//当前采集系统类型
    public static String currentDriversType;//当前设备类型
    public static String currentCollectType ;//当前采集类型

    public static boolean inputStatus = false;//输入状态 判断输入是否成功
    public static boolean collectOnOff ;//采集开关 未执行采集进行分析时进行判断使用,初始化当前时间参数初始化
    public static boolean currentParamOnOff = false;//初始化时使用，当前参数开关,更新当前时间信息用于目录名称生成
    public static boolean exitOnOff = false;//退出开关

    //collect
    public static boolean collectDebugOnOff = false;//debug开关
    public static boolean collectOutOnOff = false;//采集输出开关
    public static String command;
    public static String exitCmd;//采集设备退出指令
    public static int timeOut;//超时时间
    public static long sleepTime;//等待时长
    public static int port;
    public static int threadPool;//线程池大小
    public static String userName;
    public static String passWord;
    public static List<String> loginIpAddList;


    //path
    public static String loginFileName;
    public static String loginFilePath;
    public static String accountFileName;
    public static String accountFilePath;


    public static String currentCollectPath;//当前采集路径
    public static String currentFormatSourcePath;//当前格式化路径
    public static String currentIntegratePath;//当前整理路径
    public static String currentAnalysisPath;//当前分析路径
    public static String currentLogFilePath;//当前日志路径


    public static final String splitChar = "\\|";
    public static final String fileSplitChar = "_";
    public static final String charCode = "UTF-8";



    public final static String  filterStr = "---- More ----\u001B[42D                                          \u001B[42D";

}
