package com.plf.learn.base;

class ThreadVolatileFlag extends Thread {
    public volatile boolean flag = true;

    @Override
    public void run() {
        System.out.println("run start ... ");
        while (flag){

        }
        System.out.println("run end ... ");
    }

    public void setFlag(){
        this.flag = false;
    }
}

public class ThreadVolatile {
    public static void main(String[] args) throws InterruptedException {
        ThreadVolatileFlag threadVolatileFlag = new ThreadVolatileFlag();
        threadVolatileFlag.start();

        Thread.sleep(2000);

        System.out.println("main....");
        threadVolatileFlag.setFlag();
    }
}
