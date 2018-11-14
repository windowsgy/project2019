package params;



import javaUtils.DateTimeUtils;
import javaUtils.Log;

import java.util.Scanner;

/**
 *
 * Created by jlgaoyuan on 2018/5/7.
 */
public class InputParm {

    public static boolean run() {
        DateTimeUtils dtUtil = new DateTimeUtils();
        Scanner input = new Scanner(System.in);
        Log.info("InputDate............"+ InitParm.DATE_FORMAT);
        String inputDate = input.nextLine().trim();
        if (inputDate.length() != 6) {
            Log.error("Date Format Error");
            return false;
        } else if (!dtUtil.dtFormatCheak(inputDate, InitParm.DATE_FORMAT)) {
            Log.error("Date Format Error");
            return false;
        }
        BuildParm.inputDate = inputDate;
        return true;
    }
}
