package com.plf.learn.concurrent.semaphore;

import java.sql.Time;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @author panlf
 * @date 2022/2/12
 */
public class SemaphoreExample2 {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        // 定义5个信号量
        Semaphore semaphore = new Semaphore(5);

        for(int i=1;i<=20;i++){
            final int index = i;
            executorService.execute(()->{
                try{
                    if(semaphore.tryAcquire(2, TimeUnit.SECONDS)) {
                        play(index);
                    }else {
                        System.out.println(Thread.currentThread().getName()+":对不起服务器已经满了，请稍后再试！");
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    semaphore.release();
                }
            });
        }
    }

    private static void play(int index) {
        try {
            System.out.println(Thread.currentThread().getName()+"--"+index+"-task running...");
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
