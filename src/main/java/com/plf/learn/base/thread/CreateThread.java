package com.plf.learn.base.thread;


//1、继承Thread方法
class CreateExtendsThread extends Thread {
    @Override
    public void run() {
        for(int i=0;i<10;i++){
            Thread.currentThread().setName("Thread");
            System.out.println(Thread.currentThread().getName()+"--"+i);
        }
    }
}

//实现Runnable方法
class CreateImplementRunnable implements Runnable {
    @Override
    public void run() {
        for(int i=0;i<10;i++){
            Thread.currentThread().setName("Runnable");
            System.out.println(Thread.currentThread().getName()+"--"+i);
        }
    }
}

/**
 * @author panlf
 * @Date 2021/8/21
 */
public class CreateThread {
    public static void main(String[] args) {
        CreateExtendsThread thread = new CreateExtendsThread();
        CreateImplementRunnable runnable = new CreateImplementRunnable();
        new Thread(runnable).start();
        thread.start();
        for(int i=0;i<10;i++){
            System.out.println(Thread.currentThread().getName()+"--"+i);
        }
    }
}
