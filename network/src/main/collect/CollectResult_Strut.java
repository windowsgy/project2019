package collect;

public class CollectResult_Strut {

    private boolean gatBoolean = false; //采集结果是否成功
    private String ipadd;
    private String step;//登陆步骤
    private String log;//采集日志；
    private String gatInfo; //采集信息
    private String startDateTime;//采集开始时间
    private String endDateTime;//采集结束时间
    private long timeLong;

    long getTimeLong() {
        return timeLong;
    }

    public void setTimeLong(long timeLong) {
        this.timeLong = timeLong;
    }

    public String getcollectFilesPath() {
        return collectFilesPath;
    }

    public void setcollectFilesPath(String collectFilesPath) {
        this.collectFilesPath = collectFilesPath;
    }

    private String collectFilesPath;

    public int getTn() {
        return tn;
    }

    public void setTn(int tn) {
        this.tn = tn;
    }

    private int tn;

    boolean isGatBoolean() {
        return gatBoolean;
    }

    public void setGatBoolean(boolean gatBoolean) {
        this.gatBoolean = gatBoolean;
    }

    public String getIpadd() {
        return ipadd;
    }

    public void setIpadd(String ipadd) {
        this.ipadd = ipadd;
    }

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }

    String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public String getGatInfo() {
        return gatInfo;
    }

    public void setGatInfo(String gatInfo) {
        this.gatInfo = gatInfo;
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
