package analysis.format.huawei.router;

import analysis.format.FormatInterface;

import java.util.ArrayList;
import java.util.List;

public class Int implements FormatInterface {
    @Override
    public String run(List<String> list) {
        return fields(filter(list));//过滤后再选择字段返回字符串;
    }

    /**
     * 根据字段特征进行选择，只选择满足条件的字段加入list 中
     * @param list list
     * @return list
     */
    private List<String> filter(List<String> list) {
        List<String> newList = new ArrayList<>();
        boolean filter = false;
        for (String line : list) {
            if (line.contains("Current system time")) {
                filter = true;
                continue;
            }
            if (line.length() == 0) {
                filter = false;
            }
            if (!filter) {
                newList.add(line);
            }
        }
        return newList;
    }

    /**
     * 选择字段
     * @param list list
     * @return String
     */
    private String fields(List<String> list) {
        StringBuilder sb = new StringBuilder();
        String intStr = "";
        String phStatus = "";
        String desc = "";
        String proStatus = "";
        String mac = "";
        String IpAddress = "";
        for (String line : list) {
            if (includeInt(line)) {
                intStr = line.substring(0, line.indexOf("current")).trim();
                phStatus = line.substring(line.lastIndexOf(":") + 1).trim();
            }
            if (includeLinePro(line)) {
                proStatus = line.substring(line.lastIndexOf(":") + 1).trim();
            }
            if (includeDesc(line)) {
                desc = line.substring(line.indexOf(":") + 1).trim();
            }
            if (includeInternetAddress(line)) {
                IpAddress = line.substring(line.lastIndexOf("is") + 2).trim();
            }
            if (includeMac(line)) {
                mac = line.substring(line.lastIndexOf("is") + 2).trim();
            }
            if ("".equals(line)) {
                String fields = intStr + "|" + phStatus + "|" + proStatus + "|" + desc + "|" + mac + "|" + IpAddress + "\r\n";
                desc = "";
                proStatus = "";
                mac = "";
                IpAddress = "";
                intStr = "";
                phStatus ="";
         //       System.out.print(fields);
                sb.append(fields);
            }
        }
        return sb.toString();
    }

    /**
     * 判断此行是否包含 interface
     * @param line 每行信息
     * @return boolean
     */
    private boolean includeInt(String line){
        return line.indexOf("DCN") == 0 || line.indexOf("Eth") == 0 || line.indexOf("NULL") == 0 || line.indexOf("LoopBack") == 0 || line.indexOf("Gigabit")  == 0 ||line.indexOf("Aux") ==0 ||line.indexOf("Virtual") ==0;
    }

    /**
     * 判断此行是否包含 描述
     * @param line 每行
     * @return boolean
     */
    private boolean includeDesc(String line){
        return line.indexOf("Desc") == 0;
    }

    /**
     * 判断此行是否包含 协议状态信息
     * @param line 每行
     * @return boolean
     */
    private boolean includeLinePro (String line){
        return line.indexOf(("Line protocol")) == 0;
    }

    /**
     * 判断每行是否包含MAC地址
     * @param line 每行
     * @return boolean
     */
    private boolean includeMac (String line){
        return line.indexOf("IP Sending") == 0;
    }

    /**
     * 判断每行是否包含ip地址
     * @param line 每行
     * @return boolean
     */
    private boolean includeInternetAddress (String line){
        return line.indexOf("Internet Address") == 0;
    }
}

