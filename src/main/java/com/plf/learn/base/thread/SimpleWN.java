package com.plf.learn.base.thread;

/**
 * @author panlf
 * @date 2024/9/2
 */
public class SimpleWN {
    final static Object object = new Object();

    public static class T1 extends Thread {
        @Override
        public void run() {
            synchronized (object){
                System.out.println(System.currentTimeMillis()+":T1 start!");
                try {
                    System.out.println(System.currentTimeMillis()+":T1 wait for object!");
                    object.wait();
                } catch (Exception e){
                    e.printStackTrace();
                }
                System.out.println(System.currentTimeMillis()+":T1 end!");
            }
        }
    }

    public static class T2 extends Thread {
        @Override
        public void run() {
            synchronized (object){
                System.out.println(System.currentTimeMillis()+":T2 start!notify one thread");
                object.notify();
                System.out.println(System.currentTimeMillis()+":T2 end!");
                try {
                    Thread.sleep(2000);
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        Thread t1 = new T1();
        Thread t2 = new T2();
        t1.start();
        t2.start();
        /*

            1725290578900:T1 start!
            1725290578900:T1 wait for object!
            1725290578900:T2 start!notify one thread
            1725290578900:T2 end!
            1725290580916:T1 end!

            在T2通知T1继续执行后，T1并不能立即继续执行，而是要等待T2释放object的锁，并重新成功获得锁后，才能继续执行。

            Object.wait()方法和Thread.sleep()方法都可以让线程等待若干时间。
            除wait()方法可以被唤醒后，另外一个主要区别就是wait()方法会释放目标对象的锁，而Thread.sleep()方法不会释放任何资源。
         */
    }
}
