package collect;

class Stru_CollectCount {
    int getCount() {
        return count;
    }

    void setCount(int count) {
        this.count = count;
    }

    int getSuccessfulCount() {return successfulCount;}

    void setSuccessfulCount(int successfulCount) {
        this.successfulCount = successfulCount;
    }

    int getFailCount() {return failCount; }

    void setFailCount(int failCount) {
        this.failCount = failCount;
    }

    String getStartTime() {
        return startTime;
    }

    void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    String getEndTime() {
        return endTime;
    }

    void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    Long getTimeLong() {
        return timeLong;
    }

    void setTimeLong(Long timeLong) {
        this.timeLong = timeLong;
    }

    private int count;
    private int successfulCount;
    private int failCount;
    private String startTime ;
    private String endTime;
    private Long timeLong;

}
