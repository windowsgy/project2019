package initParam;


import utils.LogInfo;

import java.io.InputStreamReader;
import java.util.*;

public class Config {

    /**
     * @param fileName 配置文件名
     * @param map      参数map
     * @return boolean
     */

    static boolean mapL1(String fileName, Map<String, String> map) {
        //   LogInfo.info("Load ConfigFile :"+fileName);
        Properties props = new Properties();
        try {
            props.load(new InputStreamReader(Config.class.getClassLoader().getResourceAsStream(fileName)));
        } catch (Exception e) {
            LogInfo.error(e.getMessage());
            LogInfo.error("Load Config Failed :" + fileName);
            return false;
        }
        for (Object key : props.keySet()) {
            String keyString = String.valueOf(key);  //Key
            String valString = props.getProperty(keyString);//Value
            map.put(keyString, valString);
        }
        //  LogInfo.info("Load File Finished :"+fileName);
        return true;
    }


    /**
     * @param fileName 配置文件名
     * @param map      参数map
     * @return boolean
     */

    public static boolean mapL2(String fileName, Map<String, Map<String, String>> map) {
        //    LogInfo.info("Load ConfigFile :"+fileName);
        Properties props = new Properties();
        try {
            props.load(new InputStreamReader(Config.class.getClassLoader().getResourceAsStream(fileName)));
        } catch (Exception e) {
            LogInfo.error(e.getMessage());
            LogInfo.error("Load Config Failed :" + fileName);
            return false;
        }
        for (Object key : props.keySet()) {
            String keyString = String.valueOf(key);  //Key
            String valString = props.getProperty(keyString);//Value

            if (keyString.contains(".")) { //判断参数Key包含 . ，用于截取typeKey,subKey
                String keys[] = keyString.split("\\.");
                if (map.containsKey(keys[0])) {//如果包含一级key
                    map.get(keys[0]).put(keys[1], valString);//添加二级key
                } else { //不包含一级key
                    map.put(keys[0], new HashMap<>());//创建一级key
                    map.get(keys[0]).put(keys[1], valString);//添加三级key
                }
            }
        }

        //LogInfo.info("Load File Finished :"+fileName);
        return true;
    }

    /**
     * @param fileName 配置文件名
     * @param map      参数map
     * @return boolean
     */

    static boolean mapL3(String fileName, Map<String, Map<String, Map<String, String>>> map) {
        // LogInfo.info("Load ConfigFile :"+fileName);
        Properties props = new Properties();
        try {
            props.load(new InputStreamReader(Config.class.getClassLoader().getResourceAsStream(fileName)));
        } catch (Exception e) {
            LogInfo.error(e.getMessage());
            LogInfo.error("Load Config Failed :" + fileName);
            return false;
        }
        for (Object key : props.keySet()) {
            String keyString = String.valueOf(key);  //Key
            String valString = props.getProperty(keyString);//Value
            if (keyString.contains(".")) { //判断参数Key包含 . ，用于截取typeKey,subKey
                String keys[] = keyString.split("\\.");
                if (map.containsKey(keys[0])) {//如果包含一级key
                    if (map.get(keys[0]).containsKey(keys[1])) {//包含二级key
                        map.get(keys[0]).get(keys[1]).put(keys[2], valString);//添加三级key
                    } else {
                        map.get(keys[0]).put(keys[1], new HashMap<>());
                        map.get(keys[0]).get(keys[1]).put(keys[2], valString);//添加三级key
                    }
                } else { //不包含一级key
                    map.put(keys[0], new HashMap<>());//创建一级key
                    if (map.get(keys[0]).containsKey(keys[1])) {//包含二级key
                        map.get(keys[0]).get(keys[1]).put(keys[2], valString);//添加三级key
                    } else {//不包含二级key
                        map.get(keys[0]).put(keys[1], new HashMap<>());
                        map.get(keys[0]).get(keys[1]).put(keys[2], valString);//添加三级key
                    }
                }
            }
        }
        //LogInfo.info("Load File Finished :"+fileName);
        return true;
    }

}
