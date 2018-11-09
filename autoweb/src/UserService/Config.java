package UserService;


import utils.Log;

import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Config {
    public static Map<String, String> getMap(String resourceFilePath) {
        Map<String, String> map = new HashMap<>();
        Properties props = new Properties();

        try {
            props.load(new InputStreamReader(Config.class.getClassLoader().getResourceAsStream(resourceFilePath)));
        } catch (Exception e) {
            Log.error(e.getMessage());
            Log.error("Load Config Failed :" + resourceFilePath);
        }
        //   Log.info("Load ConfigFile :"+fileName);
        for (Object key : props.keySet()) {
            String keyString = String.valueOf(key);  //Key
            String valString = props.getProperty(keyString);//Value
            map.put(keyString, valString);
        }
        //  Log.info("Load File Finished :"+fileName);
        return map;
    }
}
