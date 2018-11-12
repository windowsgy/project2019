import start.Start;
import utils.Log;


public class NetworkMain {
    public static void main(String[] args) {
        Log.info("run");
        Log.debug("den");
        Log.info("a");

        if(args.length==1){
            if("debug".equals(args[0]) || "DEBUG".equals(args[0])){
                Log.setDebug(true);
            }
        }else if(args.length == 2){
            if("debug".equals(args[0]) || "DEBUG".equals(args[0])){
                Log.setDebug(true);
            }
            if("out".equals(args[1]) || "OUT".equals(args[1])){
                Log.setOut(true);
            }


        }
        Start.run();
    }
}
