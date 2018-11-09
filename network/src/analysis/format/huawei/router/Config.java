package analysis.format.huawei.router;

import analysis.format.FormatInterface;

import java.util.ArrayList;
import java.util.List;

public class Config  implements FormatInterface {

    @Override
    public String run(List<String> list) {
        List<String> filterList = filter(list);
        return format(filterList);
    }

    /**
     * 过滤
     */
    private List<String> filter(List<String> list) {
        List<String> filterList = new ArrayList<>();
        for (String line : list) {
            if (!line.contains("local-user")) {
                continue;
            }
            filterList.add(line);
        }
        return filterList;
    }

    private String format(List<String> list) {
        StringBuilder sb = new StringBuilder();
        for (String line : list) {
            line = line.trim();
            if (line.contains("password")) {
                sb.append("\r\n");
                sb.append(line.substring(0, line.indexOf("password")).trim()).append("|");
            }
            if (line.contains("service")) {
                sb.append(line.substring(line.lastIndexOf(" ")).trim()).append("|");
            }
            if (line.contains("level")) {
                sb.append(line.substring(line.lastIndexOf(" ")).trim());
            }
        }
        return sb.toString();
    }
}
