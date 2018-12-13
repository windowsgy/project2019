package collect.stru;

public class Stru_Collect {

    private int  tn ; //线程编号
    private String ipAddress ;//IP地址
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
    private String systemType;//linux，huawei,zte
    private String driversType;//router , switch
    private String collectType;//ssh ,telnet

    public String getSystemType() {
        return systemType;
    }
    public void setSystemType(String systemType) {
        this.systemType = systemType;
    }
    public String getDriversType() {
        return driversType;
    }
    public void setDriversType(String driversType) {
        this.driversType = driversType;
    }
    public String getCollectType() {
        return collectType;
    }
    public void setCollectType(String collectType) {
        this.collectType = collectType;
    }
    public String getWrPath() {
        return wrPath;
    }
    public void setWrPath(String wrPath) {
        this.wrPath = wrPath;
    }
    public String getCharCode() {
        return charCode;
    }
    public void setCharCode() {
        this.charCode = param.Param.charCode;
    }
    public int getTn() {
        return tn;
    }
    public void setTn(int tn) {
        this.tn = tn;
    }
    public String getIpAddress() {
        return ipAddress;
    }
    public void setIpAddress(String IpAddress) {
        this.ipAddress = IpAddress;
    }
    public String getUname() {
        return uname;
    }
    public void setUname(String uname) {
        this.uname = uname;
    }
    public String getPwd() {
        return pwd;
    }
    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
    public String getCmd() {
        return cmd;
    }
    public void setCmd(String cmd) {
        this.cmd = cmd;
    }
    public int getPort() {
        return port;
    }
    public void setPort(int port) {
        this.port = port;
    }
    public String getTimeFormat() {
        return timeFormat;
    }
    public void setTimeFormat(String timeFormat) {
        this.timeFormat = timeFormat;
    }
    public String getExitCmd() {
        return exitCmd;
    }
    public void setExitCmd(String exitCmd) {
        this.exitCmd = exitCmd;
    }
    public int getTimeOut() {
        return timeOut;
    }
    public void setTimeOut(int timeOut) {
        this.timeOut = timeOut;
    }
    public long getSleepTime() {
        return sleepTime;
    }
    public void setSleepTime(long sleepTime) {
        this.sleepTime = sleepTime;
    }
}
