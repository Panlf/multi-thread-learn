package com.plf.learn.concurrent.reenter;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 公平锁
 * @author panlf
 * @Date 2024/9/10
 */
public class FariLock implements Runnable {

    public static ReentrantLock fairLock = new ReentrantLock(true);

    @Override
    public void run() {
        while (true){
            try {
                fairLock.lock();
                System.out.println(Thread.currentThread().getName()+" 获得锁");
            }finally {
                fairLock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        FariLock fariLock = new FariLock();
        Thread t1 = new Thread(fariLock,"Thread-t1");
        Thread t2 = new Thread(fariLock,"Thread-t2");
        t1.start();
        t2.start();
    }
}
