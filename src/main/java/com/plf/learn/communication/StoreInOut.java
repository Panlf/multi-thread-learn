package com.plf.learn.communication;

/**
 * @author panlf
 * @Date 2021/8/21
 */
public class StoreInOut {

    private final int MAX_SIZE = 10;


    private int storeNum = 10;

    public void producer(Object lock){
        synchronized (lock){
            if(storeNum>=MAX_SIZE){
                System.out.println("仓库已经满了，不需要生产");
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            storeNum++;
            System.out.println(Thread.currentThread().getName()+"生产了一个，当前数量为【"+storeNum+"】");
            lock.notifyAll();
        }
    }


    public void consumer(Object lock){
        synchronized (lock){
            if(storeNum<=0){
                System.out.println("仓库已经没了，请不要来消费了");
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            storeNum--;
            System.out.println(Thread.currentThread().getName()+"消费了一个，当前数量为【"+storeNum+"】");
            lock.notifyAll();
        }
    }

}
