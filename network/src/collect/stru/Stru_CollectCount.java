package collect.stru;

public class Stru_CollectCount {

    private int total;
    private int successfulCount;
    private int failCount;
    private String startTime ;
    private String endTime;
    private long timeLong;

    public void setTotal(int count) {
        this.total = count;
    }
    public void setSuccessfulCount(int successfulCount) {
        this.successfulCount = successfulCount;
    }
    public void setFailCount(int failCount) {
        this.failCount = failCount;
    }

    public String getStartTime() {
        return startTime;
    }
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
    public String getEndTime() {
        return endTime;
    }
    public void setTimeLong(long timeLong) {
        this.timeLong = timeLong;
    }

    @Override
    public String toString() {
        return "Stru_CollectCount{" +
                "total=" + total +
                ", successfulCount=" + successfulCount +
                ", failCount=" + failCount +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", timeLong=" + timeLong +
                ", ratio=" + successfulCount/total +
                '}';
    }








}
