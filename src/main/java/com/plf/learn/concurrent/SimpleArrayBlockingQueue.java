package com.plf.learn.concurrent;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author panlf
 * @date 2021/8/24
 */
public class SimpleArrayBlockingQueue {
    /**
     * ArrayBlockingQueue是一个有边界的阻塞队列，它的内部实现是一个数组。有边界的意思是它的容量是有限的，
     * 我们必须在其初始化的时候指定它的容量大小，容量大小一旦指定就不可改变
     * ArrayBlockingQueue是以先进先出的方式存储数据，最新插入的对象是尾部，最新移出的对象是头部。
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        ArrayBlockingQueue<String> arrayBlockingQueue = new ArrayBlockingQueue<>(3);
        arrayBlockingQueue.add("AA");
        arrayBlockingQueue.add("BB");
        arrayBlockingQueue.add("CC");
        arrayBlockingQueue.offer("AA",1, TimeUnit.SECONDS);
    }
}
