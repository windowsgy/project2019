package start;


import initParam.Init;
import param.Param;
import utils.LogInfo;

public class Start {

    public static void run(){
        LogInfo.linel0();
        if(!Init.run()){
            return ;
        }
        do {
           ControlCenter.run();

        } while (!Param.exitOnOff);
        LogInfo.linel0();
        LogInfo.info("End");

    }

}
