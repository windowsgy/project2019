package sendMail;


import javaUtils.Log;
import mail.SendMailUtil;
import mail.StruMail;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * Created by jlgaoyuan on 2018/5/7.
 *
 */
public class SendMail {

    public static void run(Map<String, StruMail> map) {
        Log.info("Send Mail");
        int succeedCount = 0;
        int failCount = 0 ;
        List<String> failList = new ArrayList<>();
        for (String key : map.keySet()) {
            StruMail stru = map.get(key);
            SendMailUtil sm = new SendMailUtil();
            Log.info("From :"+stru.getFrom()+" To :"+ stru.getTo());
            // 发送邮件
            if(sm.sendMail(stru)){
                succeedCount++;
            }else{
                failList.add(key);
                failCount++;
            }
        }
        Log.linel1();
        Log.info("Send Mail Count:"+map.size());
        Log.info("Send Mail Succeed Count:"+succeedCount);
        Log.info("Send Mail Fail Count:"+failCount);
        for (String aFailList : failList) {
            Log.info(aFailList);
        }
        Log.linel1();
    }
}
