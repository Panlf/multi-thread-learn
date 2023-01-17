package com.plf.learn.base.safe;

/**
 * @author panlf
 * @date 2023/1/17
 */
public class VolatileExample extends Thread {

    public volatile boolean[] flag = {true};

    @Override
    public void run() {
        System.out.println("进入方法");
        while (true){
            if(!flag[0]){
                break;
            }
        }
        System.out.println("结束方法");
    }

    public void setFlag(){
        this.flag[0] = false;
    }

    public static void main(String[] args) throws InterruptedException {
        VolatileExample volatileExample = new VolatileExample();
        volatileExample.start();

        Thread.sleep(3000);
        volatileExample.setFlag();
    }
}
