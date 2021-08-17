package com.plf.learn.base.future.agg;

/**
 * @author panlf
 * @date 2021/8/17
 */
public class TaskCountService {

    public long taskCount1(){
        System.out.println("taskCount1 execute ..."+Thread.currentThread().getName());

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return 40;
    }

    public long taskCount2(){
        System.out.println("taskCount2 execute ..."+Thread.currentThread().getName());
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return 10;
    }


    public long taskCount3(){
        System.out.println("taskCount3 execute ..."+Thread.currentThread().getName());
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return 20;
    }


    public long taskCount4(){
        System.out.println("taskCount4 execute ..."+Thread.currentThread().getName());
        try {
            Thread.sleep(8000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return 30;
    }
}
