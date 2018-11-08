import start.Start;
import utils.Log;


public class NetworkMain {
    public static void main(String[] args) {

        if(args.length==1){
            if("debug".equals(args[0]) || "DEBUG".equals(args[1])){
                Log.setDebug(true);
            }
        }
        Start.run();
    }
}
