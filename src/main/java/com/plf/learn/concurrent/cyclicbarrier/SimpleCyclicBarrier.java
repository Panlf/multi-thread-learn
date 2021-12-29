package com.plf.learn.concurrent.cyclicbarrier;

import java.util.concurrent.CyclicBarrier;

/**
 * @author panlf
 * @Date 2021/8/21
 */
public class SimpleCyclicBarrier {

    CyclicBarrier cyclicBarrier;

    public SimpleCyclicBarrier(CyclicBarrier cyclicBarrier){
        this.cyclicBarrier = cyclicBarrier;
    }

    public void write(){
        try {

            System.out.println(Thread.currentThread().getName()+"开始写入数据");
            Thread.sleep(30);
            System.out.println(Thread.currentThread().getName()+"写入数据成功");

            //所有线程完成州才会执行之后的语句
            cyclicBarrier.await();
            System.out.println(Thread.currentThread().getName()+"写入数据完毕...");

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(5);
        for(int i=0;i<5;i++){
            new Thread(()->{
                new SimpleCyclicBarrier(cyclicBarrier).write();
            }).start();
        }
    }

}
