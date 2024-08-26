package com.plf.learn.base.future.completable;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * @author panlf
 * @date 2021/8/18
 */
public class CompletableFutureTest {


    public static void main(String[] args) {
        CompletableFutureTest test = new CompletableFutureTest();
        test.test12();
    }

    //创建一个完成的CompletableFuture
    public void test01()  {
        CompletableFuture completableFuture = CompletableFuture.completedFuture("Message");
        //getNow如果结果已经计算完则返回结果或者抛出异常，否则返回给定的valueIfAbsent值。
        //如果任务完成则获得返回值，如果调用时未完成则返回设置的默认值
        System.out.println(completableFuture.getNow("Hello"));
    }


    //异步执行任务
    public void test02()  {
        CompletableFuture completableFuture = CompletableFuture.runAsync(()->{
            System.out.println("我开始处理了");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println("是否完成了===>"+completableFuture.isDone());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("是否完成了===>"+completableFuture.isDone());
    }

    //应用函数
    public void test03()  {
        CompletableFuture completableFuture = CompletableFuture
                .completedFuture("message")
                //thenApplyAsync 异步执行
                .thenApply(x->{
                    return x.toUpperCase();
                });
        System.out.println(completableFuture.getNow(null));

    }

    public void test04()  {
        CompletableFuture completableFuture = CompletableFuture
                .completedFuture("message")
                .thenApplyAsync(x->{
                    return x.toUpperCase();
                });
        completableFuture.join();
        System.out.println(completableFuture.getNow(null));

    }

    //消费前一阶段的结果
    public void test05()  {
        StringBuilder result = new StringBuilder();
        CompletableFuture.completedFuture("thenAccept message")
                .thenAccept(s -> result.append(s));
        System.out.println(result.toString());

    }

    //异步消费
    public void test06()  {
        StringBuilder result = new StringBuilder();
        CompletableFuture completableFuture = CompletableFuture.completedFuture("thenAccept message")
                .thenAcceptAsync(s -> result.append(s));
        completableFuture.join();
        System.out.println(result.toString());
    }


    //取消计算
    public void test07(){
        CompletableFuture cf = CompletableFuture
                .completedFuture("message")
                .thenApplyAsync(String::toUpperCase);
        CompletableFuture cf2 = cf.exceptionally(throwable -> "canceled message");
        cf.completeExceptionally(new RuntimeException("completed exceptionally"));
        System.out.println(cf.cancel(true));
        System.out.println(cf.isCompletedExceptionally());
        //System.out.println(cf.join());
        System.out.println(cf2.join());
    }

    public void test08(){
        String original = "Message";
        CompletableFuture cf1 = CompletableFuture.completedFuture(original)
                .thenApplyAsync(s -> s.toUpperCase());
        CompletableFuture cf2 = cf1.applyToEither(
                CompletableFuture.completedFuture(original).thenApplyAsync(s -> s.toLowerCase()),
                s -> s + " from applyToEither");
        System.out.println(cf2.join());
    }


    //组合
    public void test09(){
        String original = "Message";
        CompletableFuture<String> cf = CompletableFuture.completedFuture(original).thenApply(s -> s.toUpperCase())
                .thenCompose(upper -> CompletableFuture.completedFuture(original).thenApply(s -> s.toLowerCase())
                        .thenApply(s -> upper + s));
        System.out.println(cf.join());
    }

    public void test10(){
        StringBuilder result = new StringBuilder();
        List messages = Arrays.asList("a", "b", "c");
        List<CompletableFuture> futures = (List<CompletableFuture>) messages.stream()
                .map(msg -> CompletableFuture.completedFuture(msg).thenApply(s -> s.toString().toUpperCase()))
                .collect(Collectors.toList());
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()])).whenComplete((v, th) -> {
            futures.forEach(cf -> System.out.println(cf.getNow(null)));
            result.append("done");
        });
        System.out.println(result.toString());
    }

    public void test11(){
        StringBuilder result = new StringBuilder();
        List messages = Arrays.asList("a", "b", "c");
        List<CompletableFuture> futures = (List<CompletableFuture>) messages.stream()
                .map(msg -> CompletableFuture.completedFuture(msg).thenApplyAsync(s -> s.toString().toUpperCase()))
                .collect(Collectors.toList());
        CompletableFuture allOf = CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()]))
                .whenComplete((v, th) -> {
                    futures.forEach(cf -> System.out.println(cf.getNow(null)));
                    result.append("done");
                });
        allOf.join();
        System.out.println(result.toString());
    }

    /**
     * 串行方法
     */
    public void test12(){
        System.out.println("主线程start...");
        CompletableFuture.runAsync(()->{
            System.out.println("任务1 --- "+Thread.currentThread().getName());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).thenRunAsync(()->{
            System.out.println("任务2 --- "+Thread.currentThread().getName());
        });
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("主线程end...");
    }
}
