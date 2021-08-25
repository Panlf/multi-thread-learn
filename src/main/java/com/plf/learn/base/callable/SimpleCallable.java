package com.plf.learn.base.callable;

import java.util.concurrent.*;

/**
 * @author panlf
 * @date 2021/8/25
 */
public class SimpleCallable {


    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();

        Future<String> future = executorService.submit(() -> "Hello Callable");

        //获取结果
        String result = future.get();
        System.out.println(result);
    }
}
