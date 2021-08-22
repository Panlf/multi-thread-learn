package com.plf.learn.communication;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author panlf
 * @Date 2021/8/21
 */
public class PrintNumTwoCondition {

    private final Lock lock = new ReentrantLock();
    private final Condition conditionA = lock.newCondition();
    private final Condition conditionB = lock.newCondition();
    private int num = 1;

    public void printOddNumber() {
        try {
            lock.lock();
            if (num % 2 == 0) {
                conditionA.await();
            }
            System.out.println(Thread.currentThread().getName() + " --- " + num);
            num++;
            conditionB.signal();
        } catch (Exception e) {
            lock.unlock();
        } finally {
            lock.unlock();
        }
    }

    public void printEvenNumber() {
        try {
            lock.lock();
            if (num % 2 != 0) {
                conditionB.await();

            }
            System.out.println(Thread.currentThread().getName() + " --- " + num);
            num++;
            conditionA.signal();
        } catch (Exception e) {
            lock.unlock();
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        PrintNumTwoCondition printNum = new PrintNumTwoCondition();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                printNum.printOddNumber();
            }
        }).start();
        new Thread(() -> {
            for (int i = 0; i <= 10; i++) {
                printNum.printEvenNumber();
            }
        }).start();
    }

}
