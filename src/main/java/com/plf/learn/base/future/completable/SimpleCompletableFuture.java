package com.plf.learn.base.future.completable;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author panlf
 * @date 2021/8/17
 */
public class SimpleCompletableFuture {
    static ExecutorService executor = Executors.newFixedThreadPool(2);

    public static void main(String[] args) {

        System.out.println("我分配了任务1");
        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {

            try {
                System.out.println("开始处理任务1");
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return "处理完了任务1";

        },executor);


        System.out.println("我分配了任务2");
        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {

            try {
                System.out.println("开始处理任务2");
                Thread.sleep(7000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return "处理完了任务2";

        },executor);


        future1.thenAccept(e->{
            System.out.println("反馈1 : "+e);
        });

        future2.thenAccept(e->{
            System.out.println("反馈2 : "+e);
        });

        System.out.println("我开始等待结果");

        executor.shutdown();
    }
}
