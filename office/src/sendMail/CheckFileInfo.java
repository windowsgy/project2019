package sendMail;


import base.ListUtils;
import base.Log;
import base.Regex;
import base.SetUtils;
import params.BuildParm;

import java.util.HashSet;

/**
 *
 * Created by jlgaoyuan on 2018/5/5.
 */
public class CheckFileInfo {

    /**
     * 文件信息检查
     * @return boolean
     */
    public static boolean run(){
        Log.info("Check Files Info ");
        if(BuildParm.summaryFileHead== null || BuildParm.summaryFileHead.length()<2){
            Log.info("汇总文件表头信息不完整");
            return false;
        }  else if(BuildParm.summaryList == null || BuildParm.summaryList.size()<1){
            Log.info("汇总文件信息为空");
            return false;
        }  else if (BuildParm.summaryJoinField.size()!= new HashSet<String>(BuildParm.summaryJoinField).size()){
            Log.info("汇总文件关联字段不唯一");
            return false;
        }     else if (BuildParm.summaryMailField.size()!= ListUtils.listSelect(BuildParm.summaryMailField, Regex.REGEX_EMAIL).size()){
            Log.info("E-Mail 地址格式存在错误 ");
            return false;
        } else if (BuildParm.summaryJoinField.size()!= BuildParm.summaryMailField.size()){
            Log.info("汇总文件的关联字段与邮件信息数量不一致");
            return false;
        }
        else if (BuildParm.summaryJoinField.size()!= BuildParm.SPLITFILELIST.size()){
            Log.info("汇总文件关联字段信息与拆分文件数量不一致");
            return false;
        }   else if (!(SetUtils.isSetEqual(new HashSet<>(BuildParm.summaryJoinField),new HashSet<>(BuildParm.SPLITFILELIST)))) {
            Log.info("汇总文件关联字段与拆分文件名称不一致");
            return false;
        }
        Log.info("Check Files Info  Succeed");
        return true;
    }
}
