package com.plf.learn.concurrent.locksupport;

import java.util.concurrent.locks.LockSupport;

/**
 * 线程阻塞工具类
 * @author panlf
 * @Date 2024/9/19
 */
public class LockSupportDemo {

    public static Object obj = new Object();

    static ChangeObjectThread t1 = new ChangeObjectThread("t1");

    static ChangeObjectThread t2 = new ChangeObjectThread("t2");


    public static class ChangeObjectThread extends Thread  {
        public ChangeObjectThread(String name){
            super.setName(name);
        }

        @Override
        public void run() {
            synchronized (obj){
                System.out.println("in "+getName());
                LockSupport.park();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        /*
        * 这是因为LockSupport类使用类似信号量的机制。它为每一个线程准备了一个许可，
        * 如果许可可用，那么park()方法会立即返回，并且消费这个许可（也就是将许可变为不可用），
        * 如果许可不可用，就会阻塞，而unpark()方法则使得一个许可变为可用（但是和信号量不同的是，
        * 许可不能累加，你不可能拥有超过一个许可，它永远只有一个）
        * 这个特点使得：即使unpark()方法操作发生在park()方法之前，它也可以使下一次的park()方法
        * 操作立即返回。
        * */

        t1.start();
        Thread.sleep(100);
        t2.start();
        LockSupport.unpark(t1);
        LockSupport.unpark(t2);
        t1.join();
        t2.join();
    }
}
