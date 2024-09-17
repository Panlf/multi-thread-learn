package com.plf.learn.concurrent.countdownlatch;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 倒计数器
 * @author panlf
 * @Date 2024/9/17
 */
public class CountDownLatchDemo implements Runnable {

    static final CountDownLatch end = new CountDownLatch(10);

    static final CountDownLatchDemo demo = new CountDownLatchDemo();

    @Override
    public void run() {
        try {
            Thread.sleep(new Random().nextInt(10)*1000);
            System.out.println("check complete");
            end.countDown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for(int i=0;i<10;i++){
            executorService.submit(demo);
        }
        end.await();
        // 主线程在CountDownLatch上等待，当所有检查任务全部完成后，主线程方能继续执行
        System.out.println("Fire!");
        executorService.shutdown();
    }
}
