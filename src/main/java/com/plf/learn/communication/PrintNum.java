package com.plf.learn.communication;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author panlf
 * @Date 2021/8/21
 */
public class PrintNum {

    private final Lock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();
    private int num = 1;

    public void printOddNumber() {
        try {
            lock.lock();
            if (num % 2 == 0) {
                condition.await();
            }
            System.out.println(Thread.currentThread().getName() + " --- " + num);
            num++;
            condition.signal();

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
                condition.await();
            }
            System.out.println(Thread.currentThread().getName() + " --- " + num);
            num++;
            condition.signal();
        } catch (Exception e) {
            lock.unlock();
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        PrintNum printNum = new PrintNum();

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
