package utils;

import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by jlgaoyuan on 2018/11/8.
 *
 */
public class Config {

    public static Map<String, String> map = new HashMap<>();

    /**
     * 加载配置文件到map     *
     * @param fileName 配置文件名
     * @return map
     */
    public static boolean build(String fileName) {
        Properties props = new Properties();
        try {
            props.load(new InputStreamReader(Config.class.getClassLoader().getResourceAsStream(fileName)));
            for (Object key : props.keySet()) {
                String keyString = String.valueOf(key);  //Key
                String valString = props.getProperty(keyString);//Value
                map.put(keyString, valString);
            }
        } catch (Exception e) {
            Log.error(e.getMessage());
            Log.error("Load Config Failed :" + fileName);
            return false;
        }

        return true;
    }
}
