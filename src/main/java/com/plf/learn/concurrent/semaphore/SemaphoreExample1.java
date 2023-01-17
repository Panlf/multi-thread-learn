package com.plf.learn.concurrent.semaphore;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * @author panlf
 * @date 2022/2/12
 */
public class SemaphoreExample1 {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        // 定义5个信号量
        Semaphore semaphore = new Semaphore(5);

        for(int i=1;i<=20;i++){
            final int index = i;
            executorService.execute(()->{
                try {
                    //获取一个信号量
                    semaphore.acquire();

                    play(index);

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
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
