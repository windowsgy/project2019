package analysis.analysis;


import utils.Log;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class IpVpnLoopback implements AnalysisInterface {

    /**
     * 构建loopback地址，从ip routing table
     *
     * @return string
     */
    @Override
    public String run(List<List<String>> list) {
        StringBuilder sb = new StringBuilder();
        Set<String> set = new HashSet<>();

        for (List<String> fields : list) {
            String desNetwork = fields.get(1);
            if (desNetwork.contains("/32")) {
                String str = desNetwork.substring(0, desNetwork.indexOf("/")).trim();
                set.add(str);
            }
        }
        int i = 0;
        for (String str : set) {
            if (str.trim().endsWith(".255")) {//过滤广播地址
                continue;
            }
            i++;
            sb.append(str).append("\r\n");
        }

        Log.out("loopback count :" + i);
        return sb.toString();
    }
}
