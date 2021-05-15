package com.plf.learn.base.safe;

class SaleTicketThread implements Runnable {
    private int trainCount = 10;

    @Override
    public void run() {
        while(trainCount>0){
            sale();
        }
    }

    private void sale() {
        //同步代码块，包裹需要线程安全的问题
        synchronized (SaleTicketThread.class){
            System.out.println(Thread.currentThread().getName()+",出售第"+(10-trainCount+1)+"票");
            trainCount--;
        }
    }


}

public class SaleTicket {
    public static void main(String[] args) {
        SaleTicketThread thread1 = new SaleTicketThread();
        Thread t1 = new Thread(thread1,"窗口1");
        Thread t2 = new Thread(thread1,"窗口2");

        t1.start();
        t2.start();
    }

}
