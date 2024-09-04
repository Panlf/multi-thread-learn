package com.plf.learn.base.thread;

/**
 * @author panlf
 * @date 2024/9/2
 */
public class ThreadInterruptTest {

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(){
            @Override
            public void run() {
                while(true){
                    if(Thread.currentThread().isInterrupted()){
                        System.out.println("Interrupted");
                        break;
                    }
                    try{
                        Thread.sleep(2000);
                    } catch (InterruptedException e){
                        System.out.println("Interrupted When Sleep");
                        //设置中断状态
                        Thread.currentThread().interrupt();
                    }
                    // 提示当前正在执行的线程让出 CPU 资源，
                    // 使当前线程从运行状态变为就绪状态，以便同优先级的其他线程有机会执行。
                    Thread.yield();
                }
            }
        };

        thread.start();
        Thread.sleep(2000);
        thread.interrupt();
    }
}
