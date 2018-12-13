package collect;

import java.util.ArrayList;
import java.util.List;
import base.DateTimeUtils;
import base.FileUtils;
import collect.stru.Stru_Collect;
import collect.stru.Stru_CollectResult;
import org.apache.commons.net.telnet.TelnetClient;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;
import java.util.concurrent.Callable;


public class Telnet_Client_Get implements Callable<Stru_CollectResult> {

    private int tn;
    private TelnetClient telnet = new TelnetClient();// 构造telnet对象
    private InputStream in; // 输入流 连接方法赋值
    private PrintStream out; // 输出流 连接方法赋值
    private String IpAddress; // IP地址 构造方法初始化
    private String username; // 用户名 构造方法初始化
    private String password; // 密码 构造方法初始化
    private String command; // 指令
    private Stru_CollectResult reStrut;// gather return status
    private String DATE_FORMAT;
    private FileUtils fileUtils = new FileUtils();
    private DateTimeUtils dtUtils = new DateTimeUtils();


    Telnet_Client_Get(Stru_Collect map) {

        this.IpAddress = map.getIpAddress(); // ip地址加载
        this.username = map.getUname(); // 用户名加载
        this.password = map.getPwd(); // 密码加载
        this.command = map.getCmd();
        reStrut = new Stru_CollectResult();//采集返回信息
        reStrut.setTn(tn);
        reStrut.setIpAddress(map.getIpAddress());
        reStrut.setStartDateTime(dtUtils.getCurTime(DATE_FORMAT));
    }

    public Stru_CollectResult call() {
        reStrut.setStep("connectToServer");
        if (!connectToServer()) {//如果连接失败返回
            return reStrut;
        }
        reStrut.setStep("beforeLogin");
        if (!read(beforeLoginList())) {//登陆前判断
            return reStrut;
        }
        if (command.contains("version")) {//如果是查看设备基本信息，构造 version 指令
            this.command = buildVersionCmd("xxxxxxxxxxxxxxxxxx");
        }
        reStrut.setStep("Send Username");
        if (sendToServer(this.username)) { //发送用户名
            return reStrut;
        }
        if (!read(LogingList())) {//判断
            return reStrut;
        }
        reStrut.setStep("Send Password");
        if (sendToServer(this.password)) {//发送密码
            return reStrut;
        }
        if (!read(LogingList())) {//登陆判断
            return reStrut;
        }
        reStrut.setStep("Send Command");
        if (!sendCmd()) {//发送指令
            return reStrut;
        }
        reStrut.setStep("Read Command");
      /*  if (!read(commandEndList())) {//登陆判断
            return reStrut;
        }*/
        connectClose();
        return reStrut;
    }

    /**
     * 循环发送指令
     *
     * @return boolean
     */
    private boolean sendCmd() {

        return sendToServer(command);
    }


    /**
     * 连接方法
     *
     * @return boolean
     */
    private boolean connectToServer() { //连接到服务器
        try {
            this.telnet.setDefaultTimeout(60000);// 60秒
            this.telnet.setConnectTimeout(60000);// 60秒
            this.telnet.setDefaultPort(23);//端口
            this.telnet.connect(this.IpAddress); //IP地址
            this.in = telnet.getInputStream();//输入流
            this.out = new PrintStream(telnet.getOutputStream());//输入流
            return true;
        } catch (Exception e) {
            reStrut.setEndDateTime(dtUtils.getCurTime(DATE_FORMAT));//设置结束时间
            reStrut.setLog(e.getMessage());//设置错误消息
            reStrut.setCollectBoolean(false);//设置失败
            return false;
        }
    }

    /**
     * 关闭连接方法
     */
    private boolean connectClose() {
        try {
            this.telnet.disconnect();
            return true;
        } catch (Exception e) {
            reStrut.setEndDateTime(dtUtils.getCurTime(DATE_FORMAT));//设置结束时间
            reStrut.setLog(e.getMessage());//设置错误消息
            reStrut.setCollectBoolean(false);//设置失败
            return false;
        }
    }

    /**
     * 发送数据到服务端方法
     *
     * @param cmdStr 指令
     * @return String
     */
    private boolean sendToServer(String cmdStr) {
        try {
            this.out.println(cmdStr); // 发送指令
            this.out.flush(); // 刷新缓冲
        } catch (Exception e) {
            reStrut.setEndDateTime(dtUtils.getCurTime(DATE_FORMAT));//设置结束时间
            reStrut.setLog(e.getMessage());//设置错误消息
            reStrut.setCollectBoolean(false);//设置失败
            return false;
        }
        return true;
    }

    /**
     * 命令读取方法每字符读取
     *
     * @return boolean
     */
    private boolean read(List<String> endList) { // 读取发送版本命令
        StringBuffer sb = new StringBuffer();
        try {
            char ch = (char) this.in.read();
            sb.append(ch);
            System.out.print(ch);
            while (true) {
                sb.append(ch);
                if (sb.toString().endsWith("More") || sb.toString().endsWith("Press any key to continue")) {//继续判断
                    sendToServer(" ");
                } else if (error(sb.toString())) {//错误判断
                    return false;
                } else if (end(endList, sb.toString())) {//结束判断
                    return true;
                }
                ch = (char) in.read();
            }
        } catch (Exception e) {
            reStrut.setEndDateTime(dtUtils.getCurTime(DATE_FORMAT));//设置结束时间
            reStrut.setLog(e.getMessage());//设置错误消息
            reStrut.setCollectBoolean(false);//设置失败
            return false;
        }
    }


    /**
     * 结束判断
     *
     * @param endList 结束list
     * @param str     采集数据
     * @return 如果采集数据中包括结束list 中的内容，则判断为结束
     */
    private boolean end(List<String> endList, String str) {
        for (String anEndList : endList) {
            if (str.endsWith(anEndList)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 构造指令行
     *
     * @param str 指令集
     * @return 每行指令
     */
    private List<String> buildCmdList(String str) {
        List<String> cmdList = new ArrayList<>();
        Scanner scanStr = new Scanner(str);
        while (scanStr.hasNextLine()) {
            cmdList.add(scanStr.nextLine().trim());// 读取每行命令
        }
        return cmdList;
    }

    /**
     * 构建version 命令 ，根据登录前或登录后提示的信息构造version指令
     *
     * @param str 登录前后的数据
     * @return version命令
     */
    private String buildVersionCmd(String str) {
        if (str.contains("PON")) {
            return "show version-running";
        } else if (str.contains("ZTE")) {
            return "show version";
        } else if (str.contains("Raisecom")) {
            return "show version";
        } else {
            return "display version";
        }
    }

    /**
     * 采集数据错误判断,根据判断结果确认程序退出
     *
     * @param str 采集的数据
     * @return boolean
     */
    private boolean error(String str) {
        String str1 = "closed";
        String str2 = "Read timed out";
        String str3 = "reset";
        String str4 = "Login password has not been set";
        String str5 = "Error";
        return !(str.equals(str1) || str.equals(str2) || str.equals(str3)
                || str.equals(str4) || str.equals(str5));
    }

    /**
     * 判断登录前字符集
     *
     * @return List<String>
     */
    private List<String> beforeLoginList() {
        List<String> list = new ArrayList<>();
        list.add("Login:");
        list.add("ame:");
        return list;
    }

    /**
     * 判断登录过程中字符集
     *
     * @return List<String>
     */
    private List<String> LogingList() {
        List<String> list = new ArrayList<>();
        list.add("ord:");
        return list;
    }

}
