package utils;



/**
 * 日志记录类
 * Created by Telis on 17/7/12.
 */
public class Log {

    private final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private DateTimeUtils dtUtils = new DateTimeUtils();

    public String debug(String x) {
        String message = "[debug][" + dtUtils.getCurTime(DATE_FORMAT) + "]" + x + "\r\n";
        System.out.print(message);
        return message;
    }

    public String info(String x) {
        String message = "[info][" + dtUtils.getCurTime(DATE_FORMAT) + "]" + x + "\r\n";
        System.out.print(message);
        return message;
    }

    public String warn(String x) {
        String message = "[warn][" + dtUtils.getCurTime(DATE_FORMAT) + "]" + x + "\r\n";
        System.out.print(message);
        return message;

    }

    public  String error(String x) {
        String message = "[error][" + dtUtils.getCurTime(DATE_FORMAT) + "]" + x + "\r\n";
        System.out.print(message);
        return message;
    }

    public  String linel0() {
        String message = "#############################################################################################" + "\r\n";
        System.out.print(message);
        return message;
    }

    public  String linel1() {
        String message = "=============================================================================" + "\r\n";
        System.out.print(message);
        return message;
    }

    public  String linel2() {
        String message = "--------------------------------------------------------------" + "\r\n";
        System.out.print(message);
        return message;
    }


    public  String linel3() {
        String message = "***********************************************" + "\r\n";
        System.out.print(message);
        return message;
    }

    public  String linel4() {
        String message = "............................." + "\r\n";
        System.out.print(message);
        return message;
    }


}
