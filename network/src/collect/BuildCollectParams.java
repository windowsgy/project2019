package collect;


import inOut.Input;
import param.Param;
import utils.FileUtils;
import utils.Log;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BuildCollectParams {

    public static boolean run() {
        Log.info("Init Collect Param");
        try {

            FileUtils fileUtils = new FileUtils();
            Param.port = Integer.parseInt(Param.collectMap.get("port"));
            Param.timeOut = Integer.parseInt(Param.collectMap.get("timeOut"));
            Param.sleepTime = Long.parseLong(Param.collectMap.get("sleepTime"));
            Param.threadPool = Integer.parseInt(Param.collectMap.get("threadPool"));


            //系统类型##############################################################
            Input.inputParam("currentSystemType",Param.commandMap.keySet());
            if(!Param.inputStatus){
                return false;
            }
            Param.inputStatus = false;//重置输入状态

            //输入设备类型
            Input.inputParam("currentDriversType",Param.commandMap.get(Param.currentSystemType).keySet());
            if(!Param.inputStatus){
                return false;
            }
            Param.inputStatus = false;//重置输入状态

            //设置采集类型
            Input.inputParam("currentCollectType",Param.commandMap.get(Param.currentSystemType).get(Param.currentDriversType).keySet());
            if(!Param.inputStatus){
                return false;
            }
            Param.inputStatus = false;//重置输入状态

            //创建当前采集目录

            String collectBasePath = Param.currentMainPath+Param.pathMap.get("collect");
            fileUtils.createDir(collectBasePath);
            Param.currentCollectPath =  collectBasePath+ Param.currentCollectType+"\\";
            fileUtils.createDir(Param.currentCollectPath);
            System.out.println("currentCollectPath :"+Param.currentCollectPath);

            //设定指令

            Param.command = Param.commandMap.get(Param.currentSystemType).get(Param.currentDriversType).get(Param.currentCollectType);
            System.out.println("command :" + Param.command);
            Param.exitCmd = Param.commandMap.get(Param.currentSystemType).get(Param.currentDriversType).get("ExitCommand");


            //login 相关参数设置####################################################
            String loginFilesPath = Param.pathMap.get("main")+Param.pathMap.get("login");
            System.out.println("loginFilesPath :"+loginFilesPath);

            List<String> loginFileNameList = fileUtils.getFileNameToList(loginFilesPath);
            if(loginFileNameList == null){
                Log.error("login file is null");
                return false;
            }
            Set<String> loginFilesSet = new HashSet<>(loginFileNameList);
            Input.inputParam("loginFileName",loginFilesSet);
            if(!Param.inputStatus ){
                Log.error("login file not setup :");
                return false;
            }
            Param.inputStatus = false;//重置输入状态

            Param.loginFilePath = loginFilesPath+Param.loginFileName;
            System.out.println("loginFilePath :"+Param.loginFilePath);

            //account参数设置 ####################################################
            String accountFilesPath = Param.pathMap.get("main")+Param.pathMap.get("account");
            List<String> accountFileNameList = fileUtils.getFileNameToList(accountFilesPath);
            if(accountFileNameList == null){
                Log.error("account file is null");
                return false;
            }
            Set<String> accountFilesSet = new HashSet<>(accountFileNameList);
            Input.inputParam("accountFileName",accountFilesSet);
            if(!Param.inputStatus ){
                Log.error("account file not setup :");
                return false;
            }
            Param.inputStatus = false;//重置输入状态
            Param.accountFilePath = accountFilesPath+Param.accountFileName;
            System.out.println("accountFilePath :"+Param.accountFilePath);

            //logfile ####################################################
            String currentLogBasePath = Param.currentMainPath+"\\log\\";
            fileUtils.createDir(currentLogBasePath);
            Param.currentLogFilePath = currentLogBasePath+Param.currentCollectType+".txt";
            System.out.println("currentLogFilePath :"+Param.currentLogFilePath);
            fileUtils.createFile(Param.currentLogFilePath);



        } catch (Exception e) {
            Log.error(e.getClass() + "," + e.getMessage());
            return false;
        }
        Log.info("Init Collect Param Finished");
        return true;
    }

}
