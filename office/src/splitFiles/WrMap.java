package splitFiles;


import base.FileUtils;
import base.Log;
import params.BuildParm;

import java.util.List;
import java.util.Map;

/**
 * Created by jlgaoyuan on 2018/5/7.
 *
 */
public class WrMap {

    /**
     *
     * @param map
     */
    public static void run(Map<String, List<String>> map) {
        FileUtils fileUtils = new FileUtils();
        Log.info("create split dir");
        fileUtils.createDir(BuildParm.SPLIT_SUBPATH);
        StringBuilder sb = new StringBuilder();
        sb.append(BuildParm.detailFileHead).append("\r");
        for (String key : map.keySet()) {
            String fileName = key + ".csv";
            String filePath = BuildParm.SPLIT_SUBPATH + fileName;
            List<String> list = map.get(key);
            for (String aList : list) {
                sb.append(aList).append("\r");
            }
            fileUtils.wrStr2File(sb.toString(), filePath, BuildParm.detailFileCode);
            sb.setLength(0);
            sb.append(BuildParm.detailFileHead).append("\r");
        }
    }
}
