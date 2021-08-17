package com.plf.learn.base.future.agg;

import java.util.concurrent.*;

/**
 * @author panlf
 * @date 2021/8/17
 */
public class AggCountFuture {
    private static TaskCountService taskCountService = new TaskCountService();


    private static ExecutorService executor =
            new ThreadPoolExecutor(8, 20,
                    30L,
                    TimeUnit.SECONDS,
                    new LinkedBlockingQueue<Runnable>(10),
                    Executors.defaultThreadFactory(),
            new ThreadPoolExecutor.CallerRunsPolicy());

    public static void main(String[] args) {
        long count =  AggCountFuture.aggCountTask();
        System.out.println(count);
    }


    public static long aggCountTask(){

        long startTime = System.currentTimeMillis();

        System.out.println("aggCountTask execute ..."+Thread.currentThread().getName());

        long taskCount1 = 0;
        long taskCount2 = 0;
        long taskCount3 = 0;
        long taskCount4 = 0;

        try{
         Future<Long> taskCountFuture1 = executor.submit(()-> taskCountService.taskCount1());
         Future<Long> taskCountFuture2 = executor.submit(()-> taskCountService.taskCount2());
         Future<Long> taskCountFuture3 = executor.submit(()-> taskCountService.taskCount3());
         Future<Long> taskCountFuture4 = executor.submit(()-> taskCountService.taskCount4());

        taskCount1 = taskCountFuture1.get();
        taskCount2 = taskCountFuture2.get();
        taskCount3 = taskCountFuture3.get();
        taskCount4 = taskCountFuture4.get();


        }catch (Exception e){
            e.printStackTrace();
        }

        System.out.println("complete task the time is " +(System.currentTimeMillis()-startTime)/1000);

        return taskCount1+taskCount2+taskCount3+taskCount4;
    }

}
