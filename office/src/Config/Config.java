package Config;

import utils.Log;

import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by jlgaoyuan on 2018/11/8.
 *
 */
public class Config {

     private static Log logger = new Log();
      /**
     * 加载配置文件到map
     * @param fileName 配置文件名
     * @return map
     */
    public static Map<String, String> build(String fileName) {
        Map<String, String> map = new HashMap<>();
        Properties props = new Properties();
        try {
            props.load(new InputStreamReader(Config.class.getClassLoader().getResourceAsStream(fileName)));
        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.error("Load Config Failed :" + fileName);
        }
        //   LogInfo.info("Load ConfigFile :"+fileName);
        for (Object key : props.keySet()) {
            String keyString = String.valueOf(key);  //Key
            String valString = props.getProperty(keyString);//Value
            map.put(keyString, valString);
        }
        return map;

    }
}
