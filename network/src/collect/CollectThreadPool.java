package collect;

import utils.DateTimeUtils;
import utils.FileUtils;
import utils.collect.ssh.Ssh_Client_Get;
import utils.LogInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

class CollectThreadPool {

    /**
     * @param collectList 采集结构体
     * @param timeFormat 时间格式
     * @param threadPool  线程池大小
     * @param debugOnOff  debug开关
     * @param logFilePath  日志文件路径
     * @return boolean
     */
    static boolean run(List<Collect_Strut> collectList, String timeFormat, int threadPool, boolean debugOnOff, String logFilePath) {
        FileUtils fileUtils = new FileUtils();
        DateTimeUtils dtUtils = new DateTimeUtils();
        LogInfo.info("Collect Start ");
        if (collectList.size() < 1) {
            LogInfo.info("Collect Struct Size :" + collectList.size());
            return false;
        }

        //采集失败List
        List<CollectResult_Strut> failList = new ArrayList<>();


        CollectrCount_Strut countStru = new CollectrCount_Strut();
        countStru.setStartTime(dtUtils.getCurTime(timeFormat));
        countStru.setCount(collectList.size());
        int failCount = 0;
        int successedCount = 0;

        ExecutorService pool = Executors.newFixedThreadPool(threadPool);
        // 创建多个有返回值的任务
        List<Future> list = new ArrayList<>();
        for (int i = 0; i < collectList.size(); i++) {
            Collect_Strut stru = collectList.get(i);
            stru.setTn(i);
            Callable<?> c = new Ssh_Client_Get(stru);
            Future<?> f = pool.submit(c);
            list.add(f);
        }

        // 关闭线程池
        pool.shutdown();
        for (Future<?> f : list) {
            try {
                CollectResult_Strut collectResult_strut = (CollectResult_Strut) f.get();
                String logInfo = collectResult_strut.getTn() + "," + collectResult_strut.getIpAddress() + "," + collectResult_strut.isCollectBoolean() + "," + collectResult_strut.getStep() + "," + collectResult_strut.getLog() + "," + collectResult_strut.getTimeLong() + "," + collectResult_strut.getStartDateTime() + "," + collectResult_strut.getEndDateTime();
                if (debugOnOff) {
                    LogInfo.info(logInfo);
                }
                fileUtils.wrStrAddToFile(logInfo + "\r\n", logFilePath);
                boolean collectBoolean = collectResult_strut.isCollectBoolean();
                if (collectBoolean) {
                    successedCount = successedCount + 1;
                } else {
                    failList.add(collectResult_strut);//采集失败的加入list;
                    failCount = failCount + 1;
                }

            } catch (Exception e) {
                LogInfo.error(e.getClass().getSimpleName() + "," + e.getMessage());
                e.printStackTrace();
            }
        }
        while (true) {
            if (pool.isTerminated()) {
                if (debugOnOff) {
                    LogInfo.info("pool is finished ");
                }
                break;
            }
        }
        System.out.println();
        countStru.setEndTime(dtUtils.getCurTime(timeFormat));
        countStru.setTimeLong(dtUtils.timeDifference(countStru.getStartTime(), countStru.getEndTime()));
        countStru.setSuccessedCount(successedCount);
        countStru.setFailCount(failCount);
        double collectSuccess = countStru.getSuccessedCount();
        double collectTotal = countStru.getCount();
        double collectRatio = collectSuccess/collectTotal;

        LogInfo.linel3();
        LogInfo.info("StartTime :" + countStru.getStartTime());
        LogInfo.info("Collect Total :" + countStru.getCount());
        LogInfo.info("Successful Count :" + countStru.getSuccessedCount());
        LogInfo.info("Failed Count :" + countStru.getFailCount());
        LogInfo.info("EndTime :" + countStru.getEndTime());
        LogInfo.info("Collect TimeLong :" + countStru.getTimeLong() + " second");
        LogInfo.info("collectRaio :" + collectRatio);
        //遍历打印采集失败的列表
        for(CollectResult_Strut strut :failList){
            System.out.println("Collect Failed :"+strut.getTn()+"IP :"+strut.getIpAddress()+",CollectStatus :"+strut.isCollectBoolean()+",CollectStep :"+strut.getStep()+",Log :"+strut.getLog());
        }
        LogInfo.linel3();

        //采集质量分析
        LogInfo.info("collect finished");
        return true;

    }
}
