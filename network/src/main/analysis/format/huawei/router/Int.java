package analysis.format.huawei.router;

import analysis.format.FormatInterface;

import java.util.ArrayList;
import java.util.List;

public class Int implements FormatInterface {
    @Override
    public String run(List<String> list) {
        return fields(filter(list));
    }

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
               // System.out.println(line);
                newList.add(line);
            }
        }
        return newList;
    }

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
                desc = line.substring(line.lastIndexOf(":") + 1).trim();
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

    private boolean includeInt(String line){
        return line.indexOf("DCN") == 0 || line.indexOf("Eth") == 0 || line.indexOf("NULL") == 0 || line.indexOf("LoopBack") == 0 || line.indexOf("Gigabit") == 0;
    }
    private boolean includeDesc(String line){
        return line.indexOf("Desc") == 0;
    }
    private boolean includeLinePro (String line){
        return line.indexOf(("Line protocol")) == 0;
    }
    private boolean includeMac (String line){
        return line.indexOf("IP Sending") == 0;
    }
    private boolean includeInternetAddress (String line){
        return line.indexOf("Internet Address") == 0;
    }
}

