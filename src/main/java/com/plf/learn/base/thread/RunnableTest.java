package com.plf.learn.base.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author panlf
 * @date 2021/12/29
 */
public class RunnableTest {
    public static void main(String[] args) {

        ExecutorService executor = Executors.newFixedThreadPool(1);
        TaskRun run = new TaskRun();
       // run.run();

       // executor.submit(run);

        executor.submit(run);
    }


    public static class TaskRun implements Runnable {

        @Override
        public void run() {
            System.out.println("========");
        }
    }


}
