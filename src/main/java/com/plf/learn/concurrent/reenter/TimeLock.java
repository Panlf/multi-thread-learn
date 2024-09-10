package com.plf.learn.concurrent.reenter;


import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 中断响应
 * @author panlf
 * @Date 2024/9/10
 */
public class TimeLock implements Runnable {

    public static ReentrantLock lock = new ReentrantLock();

    @Override
    public void run() {
        try {
            if(lock.tryLock(5, TimeUnit.SECONDS)){
                Thread.sleep(60000);
            } else {
                System.out.println("get lock failed");
            }
        } catch (Exception e){}
        finally {
            if(lock.isHeldByCurrentThread()){
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        TimeLock timeLock = new TimeLock();
        Thread t1 = new Thread(timeLock);
        Thread t2 = new Thread(timeLock);
        t1.start();
        t2.start();
    }
}
