package utils;



/**
 * 日志记录类
 * Created by Telis on 17/7/12.
 */
public class Log {

    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static DateTimeUtils dtUtils = new DateTimeUtils();
    private static boolean debugOnOff = false;//debug 开关
    private static FileUtils fileUtils = new FileUtils();
    private static String logFilePath ;

    public static void  setDebug(boolean debug){
        debugOnOff = debug;
    }

    public static void debug(String x) {
        if(debugOnOff){
            String message = "\033[35;4m"+"[debug][" + dtUtils.getCurTime(DATE_FORMAT) + "]" + x + "\033[0m"+"\r\n";
            System.out.print(message);
            if(logFilePath!= null){
                fileUtils.wrStrToFile(message,logFilePath,"utf-8");
            }
        }
    }

    public static void info(String x) {
        String message = "[info][" + dtUtils.getCurTime(DATE_FORMAT) + "]" + x + "\r\n";
        System.out.print(message);
        if(logFilePath!= null){
            fileUtils.wrStrToFile(message,logFilePath,"utf-8");
        }
    }

    public static void warn(String x) {
        String message = "[warn][" + dtUtils.getCurTime(DATE_FORMAT) + "]" + x + "\r\n";
        System.out.print(message);
        if(logFilePath!= null){
            fileUtils.wrStrToFile(message,logFilePath,"utf-8");
        }
    }

    public static void error(String x) {
        String message = "[error][" + dtUtils.getCurTime(DATE_FORMAT) + "]" + x + "\r\n";
        System.err.print(message);
        if(logFilePath!= null){
            fileUtils.wrStrToFile(message,logFilePath,"utf-8");
        }
    }

    public static void out(String x){
        System.out.println(x);
        if(logFilePath!= null){
            fileUtils.wrStrToFile(x,logFilePath,"utf-8");
        }
    }
    public static void out(boolean x){
        System.out.println(x);
        if(logFilePath!= null){
            fileUtils.wrStrToFile(String.valueOf(x),logFilePath,"utf-8");
        }
    }
    public static void out(int x){
        System.out.println(x);
        if(logFilePath!= null){
            fileUtils.wrStrToFile(String.valueOf(x),logFilePath,"utf-8");
        }
    }
    public static void out(long x){
        System.out.println(x);
        if(logFilePath!= null){
            fileUtils.wrStrToFile(String.valueOf(x),logFilePath,"utf-8");
        }
    }
    public static void out(double x){
        System.out.println(x);
        if(logFilePath!= null){
            fileUtils.wrStrToFile(String.valueOf(x),logFilePath,"utf-8");
        }
    }

    public static void linel0() {
        String message = "#############################################################################################" + "\r\n";
        System.out.print(message);
        if(logFilePath!= null){
            fileUtils.wrStrToFile(message,logFilePath,"utf-8");
        }
    }

    public static void linel1() {
        String message = "=============================================================================" + "\r\n";
        System.out.print(message);
        if(logFilePath!= null){
            fileUtils.wrStrToFile(message,logFilePath,"utf-8");
        }
    }

    public static void linel2() {
        String message = "**************************************************************" + "\r\n";
        System.out.print(message);
        if(logFilePath!= null){
            fileUtils.wrStrToFile(message,logFilePath,"utf-8");
        }
    }


    public static void linel3() {
        String message = "---------------------------------------" + "\r\n";
        System.out.print(message);
        if(logFilePath!= null){
            fileUtils.wrStrToFile(message,logFilePath,"utf-8");
        }
    }

    public static void linel4() {
        String message = "............................." + "\r\n";
        System.out.print(message);
        if(logFilePath!= null){
            fileUtils.wrStrToFile(message,logFilePath,"utf-8");
        }
    }


}
