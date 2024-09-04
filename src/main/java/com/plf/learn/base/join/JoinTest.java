package com.plf.learn.base.join;

/**
 * @author panlf
 * @date 2024/9/3
 */
public class JoinTest {

    public volatile static int i = 0;


    public static class AddThread extends Thread {
        @Override
        public void run() {
            for(i=0;i<1000000;i++) {

            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        AddThread addThread = new AddThread();
        addThread.start();
        addThread.join();
        /**
         * 无线等待，他会一直阻塞当前线程，直到目标线程执行完毕。
         */
        System.out.println(i);
    }
}
