package splitFiles;


import base.Log;
import base.SetUtils;
import params.BuildParm;

import java.util.HashSet;

/**
 * Created by jlgaoyuan on 2018/5/5.
 *
 */
public class CheckFileInfo {


    /**
     * 文件信息检查
     *
     * @return boolean
     */
    public static boolean run() {
        Log.info("Check Files Info");
        if (BuildParm.summaryFileHead == null || BuildParm.summaryFileHead.length() < 2) {
            Log.info("汇总文件表头信息不完整");
            return false;
        } else if (BuildParm.detailFileHead == null || BuildParm.detailFileHead.length() < 2) {
            Log.info("明细文件表头信息不完整");
            return false;
        } else if (BuildParm.summaryList == null || BuildParm.summaryList.size() < 1) {
            Log.info("汇总文件信息为空");
            return false;
        } else if (BuildParm.detailList == null || BuildParm.detailList.size() < 1) {
            Log.info("明细文件信息为空");
            return false;
        } else if (BuildParm.summaryJoinField.size() != new HashSet<>(BuildParm.summaryJoinField).size()) {
            Log.info("汇总文件关联字段不唯一");
            return false;
        } else if (BuildParm.summaryJoinField.size() != new HashSet<>(BuildParm.detailJoinField).size()) {
            Log.info("汇总文件与明细文件关联字段数量不一致");
            return false;
        } else if (!(SetUtils.isSetEqual(new HashSet<>(BuildParm.summaryJoinField), new HashSet<>(BuildParm.detailJoinField)))) {
            Log.info("汇总文件与明细文件关联字段不一致");
            return false;
        } else if (!(BuildParm.detailDetailField.size() != new HashSet<>(BuildParm.detailDetailField).size())) {
            Log.info("明细文件名字字段不唯一");
            return false;
        } else if (new HashSet<>(BuildParm.detailDateField).size() > 1) {
            Log.info("明细文件日期字段不唯一");
            return false;
        } else if (!(new HashSet<>(BuildParm.detailDateField).contains(BuildParm.inputDate))) {
            Log.info("明细文件日期字段与输入日期不一致");
            return false;
        } else if (BuildParm.summaryJoinField.size() != BuildParm.summaryMailField.size()) {
            Log.info("汇总文件的关联字段与邮件信息数量不一致");
            return false;
        }
        Log.info("Check Files Info  Succeed");
        return true;
    }
}
