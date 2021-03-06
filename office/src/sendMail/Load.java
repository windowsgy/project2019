package sendMail;




import base.FileUtils;
import base.ListUtils;
import base.Log;
import params.BuildParm;
import params.InitParm;

import java.util.List;


/**
 *
 * Created by jlgaoyuan on 2018/5/5.
 *
 */
public class Load {

    public static boolean  run(){
        Log.info("Loading Files");
        FileUtils fileUtils = new FileUtils();
        ListUtils listUtils = new ListUtils();
        BuildParm.summaryFileCode = fileUtils.codeString(BuildParm.SUMMARY_FILEPATH);
        Log.info("Summary File Code :"+BuildParm.summaryFileCode);
        BuildParm.detailFileCode = fileUtils.codeString(BuildParm.DETAIL_FILEPATH);
        Log.info("Detail File Code :"+BuildParm.detailFileCode);
        Log.info("Load Summary File Head");
        BuildParm.summaryFileHead = fileUtils.readFirstLine(BuildParm.SUMMARY_FILEPATH,BuildParm.summaryFileCode);
        Log.info("Load Summary File To List");
        BuildParm.summaryList = fileUtils.read2List(BuildParm.SUMMARY_FILEPATH,2,BuildParm.detailFileCode);//第二行开始读
        Log.info("Load Summary File To ListArray");
        BuildParm.summaryListArr = listUtils.list2ListArray(BuildParm.summaryList, InitParm.SplitStr);
        if(BuildParm.summaryListArr == null){
            Log.info("Summary Field Inconformity");//每行字段数量不一致
            return false;
        }
        Log.info("Load Summary Join Field ");
        BuildParm.summaryJoinField = listUtils.listArrField(BuildParm.summaryListArr, InitParm.summaryJoinIndex);
        Log.info("Load Summary Mail Field");
        BuildParm.summaryMailField = listUtils.listArrField(BuildParm.summaryListArr, InitParm.detailMailIndex);
        Log.info("Load SplitFiles Name");
        List<String> splitFileList = fileUtils.getFilesName(BuildParm.SPLIT_SUBPATH);
        List<String[]> splitFileListArray = listUtils.list2ListArray(splitFileList,InitParm.FileNameSplitStr);
        BuildParm.SPLITFILELIST = listUtils.listArrField(splitFileListArray,0);
        Log.info("Loading Files Succeed");
        return true;
    }
}
