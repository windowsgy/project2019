package call;

import java.util.concurrent.*;

/**
 * Created by jlgaoyuan on 2018/12/10.
 *
 */
public class Call {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
    //create thread pool
        ExecutorService ser = Executors.newFixedThreadPool(2);
        Race tortoise = new Race("乌龟",1000);
        Race rabbit = new Race("兔子",500);
        Future<Integer> result1 = ser.submit(tortoise);
        Future<Integer> result2 = ser.submit(rabbit);
        Thread.sleep(2000);
        tortoise.setFlag(false);
        rabbit.setFlag(false);
        int num1 = result1.get();
        int num2 = result2.get();
        System.out.println("乌龟跑了"+num1+"步");

        System.out.println("兔子跑了"+num2+"步");

        ser.shutdownNow();
    }
}

class Race implements Callable<Integer>{
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    private long time;
    private boolean flag = true;
    private int step = 0;

    public Race(){}

    public Race(String name,long time){
        super();
        this.name = name;
        this.time = time;
    }


    @Override
    public Integer call() throws Exception {
        while (flag){
            Thread.sleep(time);
            step++;

        }
        return step;
    }
}