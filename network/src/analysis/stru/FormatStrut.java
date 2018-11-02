package analysis.stru;

public class FormatStrut {

    private String fileName;
    private String systemType;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

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

    public String getSouPath() {
        return souPath;
    }

    public void setSouPath(String souPath) {
        this.souPath = souPath;
    }

    public String getDesPath() {
        return desPath;
    }

    public void setDesPath(String desPath) {
        this.desPath = desPath;
    }

    public boolean isFormated() {
        return formated;
    }

    public void setFormated(boolean formated) {
        this.formated = formated;
    }

    public String getFormatedData() {
        return formatedData;
    }

    public void setFormatedData(String formatData) {
        this.formatedData = formatData;
    }

    private String driversType;
    private String souPath;
    private String desPath;
    private boolean formated;
    private String formatedData;

    public String getCollectType() {
        return collectType;
    }

    public void setCollectType(String collectType) {
        this.collectType = collectType;
    }

    public String getFormatType() {
        return formatType;
    }

    public void setFormatType(String formatType) {
        this.formatType = formatType;
    }

    private String collectType;
    private String formatType;

    public String getIpAddress() {
        return IpAddress;
    }

    public void setIpAddress(String IpAddress) {
        this.IpAddress = IpAddress;
    }

    private String IpAddress;

}
