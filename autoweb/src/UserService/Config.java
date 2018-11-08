package UserService;


import utils.LogInfo;

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
            LogInfo.error(e.getMessage());
            LogInfo.error("Load Config Failed :" + resourceFilePath);
        }
        //   LogInfo.info("Load ConfigFile :"+fileName);
        for (Object key : props.keySet()) {
            String keyString = String.valueOf(key);  //Key
            String valString = props.getProperty(keyString);//Value
            map.put(keyString, valString);
        }
        //  LogInfo.info("Load File Finished :"+fileName);
        return map;
    }
}
