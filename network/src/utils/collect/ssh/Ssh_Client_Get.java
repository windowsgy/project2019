package utils.collect.ssh;

import com.jcraft.jsch.*;
import collect.Stru_CollectResult;
import collect.Strut_Collect;
import utils.DateTimeUtils;
import utils.FileUtils;
import utils.LogInfo;


import java.io.*;
import java.util.concurrent.Callable;


public class Ssh_Client_Get implements Callable<Object> {

    private JSch jsch = new JSch();
    private Session session;
    private ChannelShell channelShell;
    private Stru_CollectResult reStrut;// gather return status
    private String host;
    private int port;
    private int timeOut;
    private long sleepTime;//休息时间
    private String systemType;
    private String driversType;
    private String collectType;
    private String userName;
    private String passWord;
    private String cmd;
    private String exitCmd;//结束命令
    private String endStr;//结束字符串
    private String wrPath;//写入路径
    private String charCode;//字符集
    private String DATE_FORMAT;
    private boolean debugOnOff;
    private boolean collectOut;
    private InputStream in;
    private OutputStream out;
    private boolean ExitOnOff = true;//退出指令开关
    private FileUtils fileUtils = new FileUtils();
    private DateTimeUtils dtUtils = new DateTimeUtils();

    /**
     * 构造函数
     * @param collectStru 采集信息结构体
     */
    public Ssh_Client_Get(Strut_Collect collectStru) {
        //线程ID
        int tn = collectStru.getTn();
        this.host = collectStru.getIpAddress();
        this.port = collectStru.getPort();
        this.systemType = collectStru.getSystemType();
        this.driversType = collectStru.getDriversType();
        this.collectType = collectStru.getCollectType();
        this.userName = collectStru.getUname();
        this.passWord = collectStru.getPwd();
        this.cmd = collectStru.getCmd() + "\r\n";
        this.DATE_FORMAT = collectStru.getTimeFormat();
        this.debugOnOff = collectStru.isDebugOnOff();
        this.collectOut = collectStru.isCollectOut();
        this.exitCmd = collectStru.getExitCmd();
        this.timeOut = collectStru.getTimeOut();
        this.sleepTime = collectStru.getSleepTime();
        this.charCode = collectStru.getCharCode();
        this.wrPath = collectStru.getWrPath();

        reStrut = new Stru_CollectResult();//采集返回信息
        reStrut.setTn(tn);
        reStrut.setIpAddress(host);
        reStrut.setStartDateTime(dtUtils.getCurTime(DATE_FORMAT));
    }

    public Object call() {
        System.out.print(".");

        if (!createSession()) {
            theEnd();
            return reStrut;
        }
        if (!createChannel()) {
            theEnd();
            return reStrut;
        }
        sleep();
        if (!afterLoginGetData()) {
            theEnd();
            return reStrut;
        }
        sendCmd(cmd, true);//发送指令
        if (!getData()) {
            theEnd();
            return reStrut;
        }
        if (!wrData()) {
            theEnd();
            return reStrut;
        }
        close();
        reStrut.setCollectBoolean(true);
        reStrut.setLog("succeed");
        reStrut.setEndDateTime(dtUtils.getCurTime(DATE_FORMAT));
        reStrut.setTimeLong(dtUtils.timeDifference(reStrut.getStartDateTime(), reStrut.getEndDateTime()));
        return reStrut;
    }

    /**
     * 结束时间、时长计算
     */

    private void theEnd() {
        if (debugOnOff) {
            debug(null);
        }
        close();
        reStrut.setCollectBoolean(false);
        reStrut.setEndDateTime(dtUtils.getCurTime(DATE_FORMAT));
        reStrut.setTimeLong(dtUtils.timeDifference(reStrut.getStartDateTime(), reStrut.getEndDateTime()));
    }

    /**
     * debug
     */
    private void debug() {
        String debug = reStrut.getTn() + "," + reStrut.getIpAddress() + "," + reStrut.getStep();
        LogInfo.debug(debug);
    }

    private void debug(String info) {
        String debug = reStrut.getTn() + "," + reStrut.getIpAddress() + "," + reStrut.getStep() + "," + info;
        LogInfo.debug(debug);
    }

    private void debug(boolean info) {
        String debug = reStrut.getTn() + "," + reStrut.getIpAddress() + "," + reStrut.getStep() + "," + info;
        LogInfo.debug(debug);
    }

    private void debug(long info) {
        String debug = reStrut.getTn() + "," + reStrut.getIpAddress() + "," + reStrut.getStep() + "," + info;
        LogInfo.debug(debug);
    }


    /**
     * create session
     *
     * @return boolean
     */
    private boolean createSession() {
        reStrut.setStep("Create Session");
        if (debugOnOff) {
            debug();
        }
        try {
            session = jsch.getSession(userName, host, port);
            session.setPassword(passWord);
            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.connect(timeOut);
        } catch (Exception e) {
            LogInfo.error("session catch error : " + session.isConnected());
            reStrut.setLog(e.getMessage());
            if (debugOnOff) {
                debug(session.isConnected());
            }
            return false;
        }
        if (debugOnOff) {
            debug(session.isConnected());
        }
        return session.isConnected();
    }

    /**
     * create Channel
     *
     * @return boolean
     */
    private boolean createChannel() {
        reStrut.setStep("create Channel");
        if (debugOnOff) {
            debug();
        }
        try {
            channelShell = (ChannelShell) session.openChannel("shell");
            channelShell.connect(timeOut);
            in = channelShell.getInputStream();
            out = channelShell.getOutputStream();
        } catch (Exception e) {
            reStrut.setLog(e.getMessage());
            if (debugOnOff) {
                debug(channelShell.isConnected());
            }
            return false;
        }
        if (debugOnOff) {
            debug(channelShell.isConnected());
        }
        return channelShell.isConnected();
    }


    /**
     * 登录后读取数据
     *
     * @return boolean
     */
    private boolean afterLoginGetData() {//登录之后获取数据
        reStrut.setStep("AfterLoginGetData");
        if (debugOnOff) {
            debug();
        }
        try {
            sendCmd("\r\n", true);
            int i = in.available();
            if (debugOnOff) {
                debug("in available :" + i);
            }
            if (i > 0) {
                byte[] b = new byte[i];
                int len = in.read(b);
                if (len < 0) {
                    return false;
                }
                String str = new String(b, 0, len);
                if (collectOut) {//如果输出开关打开则输出
                    System.out.println(str);
                }
                if (endStr == null) {
                    if (!findEndStr(str)) {
                        return false;
                    }
                }

            } else {
                return false;
            }
            reStrut.setStep("AfterLoginGetData End");
        } catch (Exception e) {
            LogInfo.error("afterLogin catch error : " + e.getMessage());
            reStrut.setLog(e.getMessage());
            if (debugOnOff) {
                debug(e.getMessage());
            }
        }
        return true;
    }


    /**
     * send command
     */
    private void sendCmd(String cmd, boolean sleep) {
        if (debugOnOff) {
            debug(cmd);
        }
        try {
            out.write(cmd.getBytes());
            out.flush();
        } catch (IOException e) {
            LogInfo.error(e.getMessage());
        }
        if (sleep) {
            sleep();
        }
    }

    /**
     * get info
     *
     * @return String
     */
    private boolean getData() {
        reStrut.setStep("getData");
        if (debugOnOff) {
            debug();
        }
        try {
            BufferedReader brIn = new BufferedReader(new InputStreamReader(in));
            StringBuilder sb = new StringBuilder();
            String inData;
            while ((inData = brIn.readLine()) != null) {
                sb.append(inData).append("\r\n");
                if (collectOut) {//如果输出开关打开则输出
                    System.out.println(inData);
                }
                if (inData.contains("More")) {//如果数据中包括
                    sendCmd(" " + "\r\n", true);
                }
                if (error(inData)) {//如果数据中包括错误
                    reStrut.setLog("command error");
                    sendCmd(exitCmd + "\r\n", false);
                    if (debugOnOff) {
                        debug(inData);
                    }
                    return false;
                }
                //发送退出指令 结束字符串非空、包含结束字符串、退出开关状态为 true
                if (endStr!= null && inData.contains(endStr) && ExitOnOff) {
                    sendCmd(exitCmd + "\r\n", false);
                    ExitOnOff = false;//发送退出指令开关置于关闭状态，不再进行发送退出指令
                }
            }
            reStrut.setCollectInfo(sb.toString());
            reStrut.setStep("getData end");
            if (sb.length() < 1) {
                return false;
            }
            if (debugOnOff) {
                debug(sb.length());
            }
        } catch (Exception e) {
            LogInfo.error("getData catch error : " + e.getMessage());
            reStrut.setLog(e.getMessage());
            if (debugOnOff) {
                debug(e.getMessage());
            }
            return false;
        }
        return true;
    }

    /**
     * close
     */
    private void close() {
        try {
            if (channelShell.isConnected()) {
                channelShell.disconnect();
            }
            if (session.isConnected()) {
                session.disconnect();
            }

        } catch (Exception e) {
            reStrut.setLog(e.getMessage());
        }

    }

    /**
     * wrData
     * @return boolean
     */
    private boolean wrData() {
        boolean succeeded;
        reStrut.setStep("writeData");
        if (debugOnOff) {
            debug();
        }
        String collectFilesPath = wrPath +systemType+"_"+driversType+"_"+collectType+"_"+host + ".txt";
        reStrut.setCollectFilePath(collectFilesPath);
        if (!fileUtils.createFile(collectFilesPath)) {
            if (debugOnOff) {
                debug(false);
            }
            return false;
        }
        succeeded = fileUtils.wrStrToFile(reStrut.getCollectInfo(), collectFilesPath, charCode);
        if (debugOnOff) {
            debug(succeeded);
        }
        return succeeded;

    }

    /**
     * 数据错误判断
     *
     * @param str 数据内容
     * @return boolean 是否包含错误字符串
     */
    private boolean error(String str) {
        return str.contains("command found at")||str.contains("未找到命令")||str.contains("does not exist");
    }

    /**
     * 等待
     */
    private void sleep() {
        try {//等待时长
            if (debugOnOff) {
                debug("sleep time :" + sleepTime);
            }
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 结束字符串截取,登录后字符串截取
     *
     * @param str 登录前获取的字符串
     * @return boolean
     */
    private boolean findEndStr(String str) {
        String endStrStart1 = "[";
        String endStrEnd1 = "]";
        String endStrStart2 = "<";
        String endStrEnd2 = ">";
        boolean endBoolean = false;
        reStrut.setStep("find endStr");
        if(str.contains("[Y/N]")) {
            reStrut.setLog("system password need reset");
            return false;
        }
        if (str.contains(endStrStart1) && str.contains(endStrEnd1)) {
            endStr = str.substring(str.indexOf(endStrStart1), str.indexOf(endStrEnd1) + 1);
            if (debugOnOff) {
                debug(endStr);
            }
            endBoolean = true; //结束字符串截取成功，判定为正常
        } else if (str.contains(endStrStart2) && str.contains(endStrEnd2)) {
            endStr = str.substring(str.indexOf(endStrStart2), str.indexOf(endStrEnd2) + 1);
            if (debugOnOff) {
                debug(endStr);
            }
            endBoolean = true;
        }
        return endBoolean;
    }
}
