package collect;

import param.Param;
import utils.Chk4Str;
import utils.FileUtils;
import utils.Log;
import utils.Regex;

import java.util.*;


class BuildCollectStrut {

    /**
     * 构造采集信息结构
     * @return 采集信息结构列表
     */
    static List<Strut_Collect> run() {
        Log.info("build collect struct start");
        List<Strut_Collect> listStru = new ArrayList<>();
        if (!loadCollectIpList() || !loadAccount()) {
            return null;
        }
        List<String> list = Param.loginIpAddressList;

        for (int i = 0; i < list.size(); i++) {
            Strut_Collect stru = new Strut_Collect();
            stru.setTn(i);
            stru.setSystemType(Param.currentSystemType);
            stru.setDriversType(Param.currentDriversType);
            stru.setCollectType(Param.currentCollectType);
            stru.setIpAddress(list.get(i).trim());
            stru.setUname(Param.userName);
            stru.setPwd(Param.passWord);
            stru.setCmd(Param.command);
            stru.setPort(Param.port);
            stru.setTimeFormat(Param.pathMap.get("timeFormat"));
            stru.setCollectOut(Param.collectOutOnOff);
            stru.setDebugOnOff(Param.collectDebugOnOff);
            stru.setTimeOut(Param.timeOut);
            stru.setSleepTime(Param.sleepTime);
            stru.setWrPath(Param.currentCollectPath);
            stru.setCharCode();
            stru.setExitCmd(Param.exitCmd);
            listStru.add(stru);
        }
        Log.info("Collect Struct Size :" + listStru.size());
        if (list.size() != listStru.size()) {
            Log.error("LoginFile IpAddressress format error exists");
            return null;
        }
        Log.info("Build Collect Struct Finish");
        return listStru;
    }


    /**
     * loadCollectIpList
     *
     * @return boolean
     */
    private static boolean loadCollectIpList() {
        FileUtils fileUtils = new FileUtils();
        List<String> list = fileUtils.read2List(Param.loginFilePath, 0, Param.charCode);
        if (null == list) {
            return false;
        }
        Log.info("loginFile Row Count :" + list.size());
        Set<String> IpAddressSet = new HashSet<>(list);
        if (IpAddressSet.size() != list.size()) {//重复地址判断
            Log.error("loginFile IpAddressress duplication exists");
            return false;
        }
        for (String aList : list) {
            if (!Chk4Str.isFormat(aList, Regex.REGEX_IPV4)) {
                Log.error("IpAddressress format error :" + aList);
                return false;
            }
        }
        Param.loginIpAddressList = list;
        return true;
    }

    private static boolean loadAccount() {
        FileUtils fileUtils = new FileUtils();
        List<String> list = fileUtils.read2List(Param.accountFilePath, 0, Param.charCode);
        if (null == list) {
            Log.error("load account Failed");
            return false;
        }
        Param.userName = list.get(0).trim();
        Param.passWord = list.get(1).trim();
        return !"".equals(Param.userName) && !"".equals(Param.passWord);
    }
}
