

import javaUtils.Log;
import sendMail.RunSendMail;
import splitFiles.RunSplitFile;

import java.util.Scanner;

/**
 *
 * Created by jlgaoyuan on 2018/5/7.
 *
 */
public class OfficeMain {

    public static void main( String [] args){
        String runParm = args[0];
        //   String runParm = "split";
        switch (runParm) {
            case "split":
                RunSplitFile.run();
                break;
            case "mail":
                RunSendMail.run();
                break;
            default:
                Log.info("runParm Error");
                break;
        }
        Scanner input = new Scanner(System.in);
        Log.info("Press Any Key Exit");
        String end = input.nextLine().trim();
        if(end.length()>0){
            System.exit(0);
        }
    }

}
