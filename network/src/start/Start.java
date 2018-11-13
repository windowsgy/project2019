package start;


import initParam.Init;
import param.Param;
import javaUtils.Log;

public class Start {

    public static void run(){
        Log.linel0();
        if(!Init.run()){
            return ;
        }
        do {
           ControlCenter.run();
        } while (!Param.exitOnOff);
        Log.linel0();
        Log.info("End");

    }

}
