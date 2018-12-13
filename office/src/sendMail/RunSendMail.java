package sendMail;


import base.Log;
import collect.mail.StruMail;
import params.InputParm;
import params.SetupParms;

import java.util.Map;

/**
 *
 * Created by jlgaoyuan on 2018/5/4.
 *
 */
public class RunSendMail {

    /**
     *
     */
    public static void run(){
        Log.info("Send Mail Start");
        Log.linel0();
        if(!InputParm.run()){
         return;
        }
        Log.linel0();
        SetupParms.run();
        Log.linel0();
        if(!CheckFiles.run()){//文件检查
            return;
        }
        Log.linel0();
        if(!Load.run()){//加载文件
            return;
        }
        Log.linel0();
        if(!CheckFileInfo.run()){//文件信息检查
            return;
        }
        Log.linel0();
        Map<String, StruMail> map = MailInfo.run();
        Log.linel0();
        Log.info("Mail Count:"+map.size());
        Log.linel0();
        SendMail.run(map);
        Log.info("Send Mail End");
    }
}
