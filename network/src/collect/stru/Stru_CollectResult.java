package collect.stru;

public class Stru_CollectResult {

    private boolean collectBoolean = false; //采集结果是否成功
    private String ipAddress;
    private String step;//登陆步骤
    private String log;//采集日志；
    private String collectInfo; //采集信息
    private String startDateTime;//采集开始时间
    private String endDateTime;//采集结束时间
    private long timeLong;
    private int tn;

    public long getTimeLong() {
        return timeLong;
    }
    public void setTimeLong(long timeLong) {
        this.timeLong = timeLong;
    }
    public int getTn() {
        return tn;
    }
    public void setTn(int tn) {
        this.tn = tn;
    }
    public boolean isCollectBoolean() {
        return collectBoolean;
    }
    public void setCollectBoolean(boolean collectBoolean) {
        this.collectBoolean = collectBoolean;
    }
    public String getIpAddress() {
        return ipAddress;
    }
    public void setIpAddress(String IpAddress) {
        this.ipAddress = IpAddress;
    }
    public String getStep() {
        return step;
    }
    public void setStep(String step) {
        this.step = step;
    }
    public String getLog() {
        return log;
    }
    public void setLog(String log) {
        this.log = log;
    }
    public String getCollectInfo() {
        return collectInfo;
    }
    public void setCollectInfo(String collectInfo) {
        this.collectInfo = collectInfo;
    }
    public String getStartDateTime() {
        return startDateTime;
    }
    public void setStartDateTime(String startDateTime) {
        this.startDateTime = startDateTime;
    }
    public String getEndDateTime() {
        return endDateTime;
    }
    public void setEndDateTime(String endDateTime) {
        this.endDateTime = endDateTime;
    }
}
