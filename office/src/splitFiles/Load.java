package splitFiles;


import base.FileUtils;

import base.ListUtils;
import base.Log;
import params.BuildParm;
import params.InitParm;

/**
 * Created by jlgaoyuan on 2018/5/5.
 *
 */
public class Load {

    /**
     * @return
     */
    public static boolean run() {
        Log.info("Loading Files");
        FileUtils fileUtils = new FileUtils();
        BuildParm.summaryFileCode = fileUtils.codeString(BuildParm.SUMMARY_FILEPATH);
        Log.info("Summary File Code :" + BuildParm.summaryFileCode);
        BuildParm.detailFileCode = fileUtils.codeString(BuildParm.DETAIL_FILEPATH);
        Log.info("Detail File Code :" + BuildParm.detailFileCode);
        Log.info("Load Summary File Head");
        BuildParm.summaryFileHead = fileUtils.readFirstLine(BuildParm.SUMMARY_FILEPATH, BuildParm.summaryFileCode);
        Log.info("Load Detail File Head");
        BuildParm.detailFileHead = fileUtils.readFirstLine(BuildParm.DETAIL_FILEPATH, BuildParm.detailFileCode);
        Log.info("Load Summary File To List");
        BuildParm.summaryList = fileUtils.read2List(BuildParm.SUMMARY_FILEPATH, 2, BuildParm.summaryFileCode);//第二行开始读
        Log.info("Load Detail File To List");
        BuildParm.detailList = fileUtils.read2List(BuildParm.DETAIL_FILEPATH, 2, BuildParm.detailFileCode);//第二行开始读
        Log.info("Load Summary File ListArray");
        BuildParm.summaryListArr = ListUtils.list2ListArray(BuildParm.summaryList, InitParm.SplitStr);
        if (BuildParm.summaryListArr == null) {
            Log.info("Summary Field Inconformity");//每行字段数量不一致
            return false;
        }
        Log.info("Load Summary File Join Field");
        BuildParm.summaryJoinField = ListUtils.listArrField(BuildParm.summaryListArr, InitParm.summaryJoinIndex);
        Log.info("Load Mail Field");
        BuildParm.summaryMailField = ListUtils.listArrField(BuildParm.summaryListArr, InitParm.detailMailIndex);
        Log.info("Load Detail File ListArray");
        BuildParm.detailListArr = ListUtils.list2ListArray(BuildParm.detailList, InitParm.SplitStr);
        if (BuildParm.detailListArr == null) {
            Log.info("Detail Field Inconformity");//每行字段数量不一致
            return false;
        }
        Log.info("Load Detail File Join Field");
        BuildParm.detailJoinField = ListUtils.listArrField(BuildParm.detailListArr, InitParm.detailJoinIndex);
        Log.info("Load Detail File Id Field");
        BuildParm.detailDetailField = ListUtils.listArrField(BuildParm.detailListArr, InitParm.detailIdIndex);
        Log.info("Load Detail Date Field");
        BuildParm.detailDateField = ListUtils.listArrField(BuildParm.detailListArr, InitParm.detailDateIndex);
        Log.info("Loading Files Succeed");
        return true;
    }
}
