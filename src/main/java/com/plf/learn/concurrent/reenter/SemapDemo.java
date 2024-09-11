package com.plf.learn.concurrent.reenter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * Semaphore
 * @author panlf
 * @Date 2024/9/11
 */
public class SemapDemo implements Runnable{

    final Semaphore semaphore = new Semaphore(5);

    @Override
    public void run() {

        try{
            semaphore.acquire();
            System.out.println("======");
            Thread.sleep(2000);
            System.out.println(Thread.currentThread().getId()+":done!");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            semaphore.release();
        }
    }

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(20);
        final SemapDemo semapDemo = new SemapDemo();
        for(int i=0;i<20;i++){
            executorService.submit(semapDemo);
        }
    }
}
