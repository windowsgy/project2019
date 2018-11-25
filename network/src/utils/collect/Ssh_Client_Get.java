package utils.collect;

import com.jcraft.jsch.JSch;

import java.util.concurrent.Callable;

import com.jcraft.jsch.ChannelShell;
import com.jcraft.jsch.Session;
import base.DateTimeUtils;
import base.FileUtils;
import base.Log;

import java.io.*;

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
    private InputStream in;
    private OutputStream out;
    private boolean ExitOnOff = true;//退出指令开关
    private FileUtils fileUtils = new FileUtils();
    private DateTimeUtils dtUtils = new DateTimeUtils();

    /**
     * 构造函数
     *
     * @param stru 采集信息结构体
     */
    Ssh_Client_Get(Stru_Collect stru) {
        //线程ID
        int tn = stru.getTn();
        this.host = stru.getIpAddress();
        this.port = stru.getPort();
        this.systemType = stru.getSystemType();
        this.driversType = stru.getDriversType();
        this.collectType = stru.getCollectType();
        this.userName = stru.getUname();
        this.passWord = stru.getPwd();
        this.cmd = stru.getCmd() + "\r\n";
        this.DATE_FORMAT = stru.getTimeFormat();
        this.exitCmd = stru.getExitCmd();
        this.timeOut = stru.getTimeOut();
        this.sleepTime = stru.getSleepTime();
        this.charCode = stru.getCharCode();
        this.wrPath = stru.getWrPath();

        reStrut = new Stru_CollectResult();//采集返回信息
        reStrut.setTn(tn);
        reStrut.setIpAddress(host);
        reStrut.setStartDateTime(dtUtils.getCurTime(DATE_FORMAT));
    }


    public Object call() {
        System.out.println(".");

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

        Log.debug(null);
        close();
        reStrut.setCollectBoolean(false);
        reStrut.setEndDateTime(dtUtils.getCurTime(DATE_FORMAT));
        reStrut.setTimeLong(dtUtils.timeDifference(reStrut.getStartDateTime(), reStrut.getEndDateTime()));
    }

    /**
     * create session
     *
     * @return boolean
     */
    private boolean createSession() {
        reStrut.setStep("Create Session");
        Log.debug(reStrut.getTn() + "," + reStrut.getIpAddress() + "," + reStrut.getStep());
        try {
            session = jsch.getSession(userName, host, port);
            session.setPassword(passWord);
            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.connect(timeOut);
        } catch (Exception e) {
            Log.error("session catch error : " + session.isConnected());
            reStrut.setLog(e.getMessage());
            Log.debug(reStrut.getTn() + "," + reStrut.getIpAddress() + "," + reStrut.getStep() + session.isConnected());
            return false;
        }

        Log.debug(reStrut.getTn() + "," + reStrut.getIpAddress() + "," + reStrut.getStep() + session.isConnected());

        return session.isConnected();
    }

    /**
     * create Channel
     *
     * @return boolean
     */
    private boolean createChannel() {
        reStrut.setStep("create Channel");
        Log.debug(reStrut.getTn() + "," + reStrut.getIpAddress() + "," + reStrut.getStep());

        try {
            channelShell = (ChannelShell) session.openChannel("shell");
            channelShell.connect(timeOut);
            in = channelShell.getInputStream();
            out = channelShell.getOutputStream();
        } catch (Exception e) {
            reStrut.setLog(e.getMessage());
            Log.debug(reStrut.getTn() + "," + reStrut.getIpAddress() + "," + reStrut.getStep() + channelShell.isConnected());
            return false;
        }
        Log.debug(channelShell.isConnected());
        return channelShell.isConnected();
    }


    /**
     * 登录后读取数据
     *
     * @return boolean
     */
    private boolean afterLoginGetData() {//登录之后获取数据
        reStrut.setStep("AfterLoginGetData");
        Log.debug(null);

        try {
            sendCmd("\r\n", true);
            int i = in.available();
            Log.debug("in available :" + i);
            if (i > 0) {
                byte[] b = new byte[i];
                int len = in.read(b);
                if (len < 0) {
                    return false;
                }
                String str = new String(b, 0, len);
                Log.out(str);
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
            Log.error("afterLogin catch error : " + e.getMessage());
            reStrut.setLog(e.getMessage());
            Log.debug(e.getMessage());
        }
        return true;
    }


    /**
     * send command
     */
    private void sendCmd(String cmd, boolean sleep) {
        Log.debug(cmd);
        try {
            out.write(cmd.getBytes());
            out.flush();
        } catch (IOException e) {
            Log.error(e.getMessage());
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
        Log.debug(null);
        try {
            BufferedReader brIn = new BufferedReader(new InputStreamReader(in));
            StringBuilder sb = new StringBuilder();
            String inData;
            while ((inData = brIn.readLine()) != null) {
                sb.append(inData).append("\r\n");
                Log.out(inData);
                if (inData.contains("More")) {//如果数据中包括
                    sendCmd(" " + "\r\n", true);
                }
                if (error(inData)) {//如果数据中包括错误
                    reStrut.setLog("command error");
                    sendCmd(exitCmd + "\r\n", false);
                    Log.debug(inData);

                    return false;
                }
                //发送退出指令 结束字符串非空、包含结束字符串、退出开关状态为 true
                if (endStr != null && inData.contains(endStr) && ExitOnOff) {
                    sendCmd(exitCmd + "\r\n", false);
                    ExitOnOff = false;//发送退出指令开关置于关闭状态，不再进行发送退出指令
                }
            }
            reStrut.setCollectInfo(sb.toString());
            reStrut.setStep("getData end");
            if (sb.length() < 1) {
                return false;
            }
            Log.debug(sb.length());

        } catch (Exception e) {
            Log.error("getData catch error : " + e.getMessage());
            reStrut.setLog(e.getMessage());
            Log.debug(e.getMessage());
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
     *
     * @return boolean
     */
    private boolean wrData() {
        boolean succeeded;
        reStrut.setStep("writeData");
        Log.debug(null);
        String collectFilesPath = wrPath + systemType + "_" + driversType + "_" + collectType + "_" + host + ".txt";
        if (!fileUtils.createFile(collectFilesPath)) {
            Log.debug(false);

            return false;
        }
        succeeded = fileUtils.wrStr2File(reStrut.getCollectInfo(), collectFilesPath, charCode);
        Log.debug(succeeded);
        return succeeded;

    }

    /**
     * 数据错误判断
     *
     * @param str 数据内容
     * @return boolean 是否包含错误字符串
     */
    private boolean error(String str) {
        return str.contains("command found at") || str.contains("未找到命令") || str.contains("does not exist");
    }

    /**
     * 等待
     */
    private void sleep() {
        try {//等待时长
            Log.debug("sleep time :" + sleepTime);
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
        if (str.contains("[Y/N]")) {
            reStrut.setLog("system password need reset");
            return false;
        }
        if (str.contains(endStrStart1) && str.contains(endStrEnd1)) {
            endStr = str.substring(str.indexOf(endStrStart1), str.indexOf(endStrEnd1) + 1);
            Log.debug(endStr);
            endBoolean = true; //结束字符串截取成功，判定为正常
        } else if (str.contains(endStrStart2) && str.contains(endStrEnd2)) {
            endStr = str.substring(str.indexOf(endStrStart2), str.indexOf(endStrEnd2) + 1);

            Log.debug(endStr);
            endBoolean = true;
        }
        return endBoolean;
    }
}
