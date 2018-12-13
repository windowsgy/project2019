package collect;


import base.Chk4Str;
import base.FileUtils;
import base.Log;
import base.Regex;
import collect.stru.Stru_Collect;
import param.Param;

import java.util.*;


class BuildCollectList {

    /**
     * 构造采集信息结构
     * @return 采集信息结构列表
     */
    static List<Stru_Collect> run() {
        Log.info("build collect strut start");
        List<Stru_Collect> listStru = new ArrayList<>();
        if (!loadCollectIpList() || !loadAccount()) {
            return null;
        }
        List<String> list = Param.loginIpAddressList;

        for (int i = 0; i < list.size(); i++) {
            Stru_Collect stru = new Stru_Collect();
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
            stru.setTimeOut(Param.timeOut);
            stru.setSleepTime(Param.sleepTime);
            stru.setWrPath(Param.currentCollectPath);
            stru.setCharCode();
            stru.setExitCmd(Param.exitCmd);
            listStru.add(stru);
        }
        Log.info("Collect Strut Size :" + listStru.size());
        if (list.size() != listStru.size()) {
            Log.error("LoginFile IpAddress format error exists");
            return null;
        }
        Log.info("Build Collect Strut Finish");
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
            Log.error("loginFile IpAddress duplication exists");
            return false;
        }
        for (String aList : list) {
            if (!Chk4Str.isFormat(aList, Regex.REGEX_IPV4)) {
                Log.error("IpAddress format error :" + aList);
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
