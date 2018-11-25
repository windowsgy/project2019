package utils.collect;

public class Stru_Collect {

    private int  tn ; //线程编号
    private String IpAddress ;//IP地址
    private String uname;//用户名
    private String pwd;//密码
    private String cmd  ; //指令类型
    private String exitCmd;//退出命令
    private int port ;
    private String timeFormat;//时间格式
    private int timeOut;//超时时间
    private long sleepTime ;//等待时长
    private String charCode;//字符集
    private String wrPath;//写入路径

    private String systemType;

    public String getSystemType() {
        return systemType;
    }

    void setSystemType(String systemType) {
        this.systemType = systemType;
    }

    public String getDriversType() {
        return driversType;
    }

    void setDriversType(String driversType) {
        this.driversType = driversType;
    }

    public String getCollectType() {
        return collectType;
    }

    void setCollectType(String collectType) {
        this.collectType = collectType;
    }

    private String driversType;
    private String collectType;

    public String getWrPath() {
        return wrPath;
    }

    void setWrPath(String wrPath) {
        this.wrPath = wrPath;
    }


    public String getCharCode() {
        return charCode;
    }

    void setCharCode() {
        this.charCode = param.Param.charCode;
    }

    public int getTn() {
        return tn;
    }

    void setTn(int tn) {
        this.tn = tn;
    }

    public String getIpAddress() {
        return IpAddress;
    }

    void setIpAddress(String IpAddress) {
        this.IpAddress = IpAddress;
    }

    public String getUname() {
        return uname;
    }

    void setUname(String uname) {
        this.uname = uname;
    }

    public String getPwd() {
        return pwd;
    }

    void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getCmd() {
        return cmd;
    }

    void setCmd(String cmd) {
        this.cmd = cmd;
    }
    public int getPort() {
        return port;
    }

    void setPort(int port) {
        this.port = port;
    }



    public String getTimeFormat() {
        return timeFormat;
    }

    void setTimeFormat(String timeFormat) {
        this.timeFormat = timeFormat;
    }


    public String getExitCmd() {
        return exitCmd;
    }

    void setExitCmd(String exitCmd) {
        this.exitCmd = exitCmd;
    }

    public int getTimeOut() {
        return timeOut;
    }

    void setTimeOut(int timeOut) {
        this.timeOut = timeOut;
    }

    public long getSleepTime() {
        return sleepTime;
    }

    void setSleepTime(long sleepTime) {
        this.sleepTime = sleepTime;
    }

}
