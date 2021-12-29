package com.plf.learn.concurrent.deque;

import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * @author panlf
 * @date 2021/8/24
 */
public class SimpleConcurrentLinkedDeque {
    /**
     *  ConcurrentLinkedDeque ：是一个适用于高并发场景下的队列，通过无锁的方式，实现了高并发
     *  状态下的高性能，通常ConcurrentLinkedDeque性能好于BlockingQueue。它是一个基于链接节点
     *  的无界线程安全队列。该队列的元素遵循先进先出的原则。头是最先加入的，尾是最近加入的，该队列不允许null元素
     *
     *  add offer 都是加入元素的方法
     *  poll和peak都是取头元素节点，区别在于前者会删除元素，后者不会
     * @param args
     */
    public static void main(String[] args) {
        ConcurrentLinkedDeque<String> concurrentLinkedDeque = new ConcurrentLinkedDeque<>();
        concurrentLinkedDeque.add("AA");
        concurrentLinkedDeque.offer("BB");
        System.out.println(concurrentLinkedDeque.poll());
        System.out.println(concurrentLinkedDeque.peek());
    }
}
