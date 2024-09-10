package com.plf.learn.concurrent.reenter;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 重入锁
 * @author panlf
 * @Date 2024/9/5
 */
public class ReenterLock implements Runnable {

    public static ReentrantLock lock = new ReentrantLock();
    public static int i = 0;

    @Override
    public void run() {
        for(int j = 0;j< 1000000;j++){
            //一个线程连续两次获得同一把锁是允许的。
            lock.lock();
            lock.lock();
            try{
                i++;
            } finally {
                lock.unlock();
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ReenterLock reenterLock = new ReenterLock();
        Thread t1 = new Thread(reenterLock);
        Thread t2 = new Thread(reenterLock);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(i);
    }
}
