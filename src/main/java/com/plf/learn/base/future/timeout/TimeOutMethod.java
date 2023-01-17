package com.plf.learn.base.future.timeout;

import java.util.concurrent.*;

/**
 * @author panlf
 * @date 2023/1/14
 */
public class TimeOutMethod {
    final static ExecutorService executor = Executors.newSingleThreadExecutor();

    public static void main(String[] args) {
        FutureTask<String> futureTask = new FutureTask<String>(TimeOutMethod::getUserInfo);
        executor.execute(futureTask);
        try {
            String result = futureTask.get(1, TimeUnit.SECONDS);
            System.out.println(result);
        } catch (TimeoutException e) {
            System.out.println("接口访问超时");
        }catch (InterruptedException | ExecutionException e) {
            System.out.println("访问出错");
        }
        executor.shutdown();
    }

    public static String getUserInfo() throws InterruptedException {
        System.out.println("进入getUserInfo");
        Thread.sleep(3000);
        return "name is Lancer";
    }
}
