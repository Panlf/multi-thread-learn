package com.plf.learn.base;

public class ThreadJoin {

    public static void main(String[] args) {
         Thread thread1 = new Thread(()->{
             for(int i=0;i<5;i++){
                 System.out.println("Thead -- 1 -- " + i);
             }
         });
        thread1.start();
        Thread thread2 = new Thread(()->{
            //等待thread1执行完成
            try {
                thread1.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for(int i=0;i<5;i++){
                System.out.println("Thead -- 2 -- " + i);
            }
        });
        thread2.start();

        Thread thread3 = new Thread(()->{
            //等待thread2执行完成
            try {
                thread2.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for(int i=0;i<5;i++){
                System.out.println("Thead -- 3 -- " + i);
            }
        });
        thread3.start();

    }
}
