package com.plf.learn.concurrent.countdownlatch;

import java.util.concurrent.CountDownLatch;

/**
 * @author panlf
 * @Date 2021/8/21
 */
public class SimpleCountDownLatch {


    public static void main(String[] args) {
        CountDownLatch countDownLatch = new CountDownLatch(2);

        new Thread(()->{
            System.out.println("我是子线程1开始执行任务");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            countDownLatch.countDown();
            System.out.println("我是子线程1完成任务");
        }).start();


        new Thread(()->{
            System.out.println("我是子线程2开始执行任务");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            countDownLatch.countDown();
            System.out.println("我是子线程2完成任务");
        }).start();

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("main 执行 ");
    }
}
