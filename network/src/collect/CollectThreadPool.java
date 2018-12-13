package collect;

import base.DateTimeUtils;
import base.FileUtils;
import base.Log;
import collect.stru.Stru_Collect;
import collect.stru.Stru_CollectCount;
import collect.stru.Stru_CollectResult;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;


class CollectThreadPool {

    /**
     * @param collectList 采集结构体
     * @param timeFormat  时间格式
     * @param poolSize  线程池大小
     * @param logFilePath 日志文件路径
     * @return boolean
     */
    public static boolean run(List<Stru_Collect> collectList, String timeFormat, int poolSize, String logFilePath) {
        if (collectList == null || collectList.size() <1) {
            Log.error("collectStru size is null or eq zero");
            return false;
        }
        Log.info("Collect Start ");
        FileUtils fileUtils = new FileUtils();
        DateTimeUtils dtUtils = new DateTimeUtils();
        //采集摘要结构体
        Stru_CollectCount countStrut = new Stru_CollectCount();
        countStrut.setStartTime(dtUtils.getCurTime(timeFormat));
        countStrut.setTotal(collectList.size());
        ExecutorService pool = Executors.newFixedThreadPool(poolSize);
        // 创建多个有返回值的任务
        List<Future<Stru_CollectResult>> list = new ArrayList<>();
        for (int i = 0; i < collectList.size(); i++) {
            Stru_Collect stru = collectList.get(i);
            stru.setTn(i);
            Callable<Stru_CollectResult> c = new Ssh_Client_Get(stru);
            Future<Stru_CollectResult> f = pool.submit(c);
            list.add(f);
        }
        //循环判断线程全部结束
        while (true) {
            if (pool.isTerminated()) {
                Log.debug("pool is finished ");
                break;
            }
        }
        // 关闭线程池
        pool.shutdown();
        //循环读取结果
        int failCount = 0;
        int successfulCount = 0;
        List<Stru_CollectResult> failList = new ArrayList<>(); //采集失败List
        for (Future<Stru_CollectResult> f : list) {
            try {
                Stru_CollectResult struCollectResult = f.get();
                Log.debug(struCollectResult.toString());
                fileUtils.wrStrAdd2File(struCollectResult.toString() + "\r\n", logFilePath);
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
        Log.out(null);

        countStrut.setEndTime(dtUtils.getCurTime(timeFormat));
        countStrut.setTimeLong(dtUtils.timeDifference(countStrut.getStartTime(), countStrut.getEndTime()));
        countStrut.setSuccessfulCount(successfulCount);
        countStrut.setFailCount(failCount);
        Log.linel3();
        Log.info(countStrut.toString());//打印采集结果摘要

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
