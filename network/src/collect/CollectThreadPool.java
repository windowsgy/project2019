package collect;

import utils.DateTimeUtils;
import utils.FileUtils;
import utils.Log;
import utils.collect.ssh.Ssh_Client_Get;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

class CollectThreadPool {

    /**
     * @param collectList 采集结构体
     * @param timeFormat  时间格式
     * @param threadPool  线程池大小
     * @param logFilePath 日志文件路径
     * @return boolean
     */
    static boolean run(List<Strut_Collect> collectList, String timeFormat, int threadPool, String logFilePath) {
        FileUtils fileUtils = new FileUtils();
        DateTimeUtils dtUtils = new DateTimeUtils();
        Log.info("Collect Start ");
        if (collectList.size() < 1) {
            Log.info("Collect Strut Size :" + collectList.size());
            return false;
        }
        //采集失败List
        List<Stru_CollectResult> failList = new ArrayList<>();
        Stru_CollectCount countStru = new Stru_CollectCount();
        countStru.setStartTime(dtUtils.getCurTime(timeFormat));
        countStru.setCount(collectList.size());
        int failCount = 0;
        int successfulCount = 0;

        ExecutorService pool = Executors.newFixedThreadPool(threadPool);
        // 创建多个有返回值的任务
        List<Future> list = new ArrayList<>();
        for (int i = 0; i < collectList.size(); i++) {
            Strut_Collect stru = collectList.get(i);
            stru.setTn(i);
            Callable<?> c = new Ssh_Client_Get(stru);
            Future<?> f = pool.submit(c);
            list.add(f);
        }
        // 关闭线程池
        pool.shutdown();
        for (Future<?> f : list) {
            try {
                Stru_CollectResult struCollectResult = (Stru_CollectResult) f.get();
                Log.debug(struCollectResult.toString());
                fileUtils.wrStrAddToFile(struCollectResult.toString() + "\r\n", logFilePath);
                boolean collectBoolean = struCollectResult.isCollectBoolean();
                if (collectBoolean) {
                    successfulCount = successfulCount + 1;
                } else {
                    failList.add(struCollectResult);//采集失败的加入list;
                    failCount = failCount + 1;
                }
            } catch (Exception e) {
                Log.error(e.getClass().getSimpleName() + "," + e.getMessage());
                e.printStackTrace();
            }
        }
        while (true) {
            if (pool.isTerminated()) {
                Log.debug("pool is finished ");
                break;
            }
        }
        Log.out(null);
        countStru.setEndTime(dtUtils.getCurTime(timeFormat));
        countStru.setTimeLong(dtUtils.timeDifference(countStru.getStartTime(), countStru.getEndTime()));
        countStru.setSuccessfulCount(successfulCount);
        countStru.setFailCount(failCount);
        double collectSuccess = countStru.getSuccessfulCount();
        double collectTotal = countStru.getCount();
        double collectRatio = collectSuccess / collectTotal;
        countStru.setRatio(collectRatio);
        Log.linel3();
        Log.info(countStru.toString());//打印采集结果
        //遍历打印采集失败的列表
        for (Stru_CollectResult strut : failList) {
            Log.info("Collect Failed :" + strut.getTn() + "IP :" + strut.getIpAddress() + ",CollectStatus :" + strut.isCollectBoolean() + ",CollectStep :" + strut.getStep() + ",Log :" + strut.getLog());
        }
        Log.linel3();
        //采集质量分析
        Log.info("collect finished");
        return true;
    }
}
