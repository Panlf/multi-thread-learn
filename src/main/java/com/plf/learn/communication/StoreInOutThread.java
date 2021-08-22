package com.plf.learn.communication;

/**
 * @author panlf
 * @Date 2021/8/21
 */
public class StoreInOutThread {
    public static void main(String[] args) {
        StoreInOut storeInOut = new StoreInOut();
        Object lock = new Object();
        Thread producerA = new Thread(() -> {
            storeInOut.producer(lock);
        });
        Thread producerB = new Thread(() -> {
            storeInOut.producer(lock);
        });
        Thread consumerA = new Thread(() -> {
            storeInOut.consumer(lock);
        });
        Thread consumerB = new Thread(() -> {
            storeInOut.consumer(lock);
        });
        consumerA.start();
        consumerB.start();
        producerA.start();
        producerB.start();
    }
}
