package com.plf.learn.threadpool;

import java.util.concurrent.*;

/**
 * @author panlf
 * @date 2023/1/23
 */
public class ExceptionThreadPool {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        test03();
    }



    public static void test01() throws ExecutionException, InterruptedException {
        //创建一个线程池
        ExecutorService executorService = Executors.newFixedThreadPool(1);

        //当线程池抛出异常后 submit无提示，其他线程继续执行
        Future<Integer> future = executorService.submit(()->{
            throw new RuntimeException("submit 我就是要抛异常");
        });

        System.out.println(future.get());

        //当线程池抛出异常后 execute抛出异常，其他线程继续执行新任务
        executorService.execute(()->{
            throw new RuntimeException("execute 我就是要抛异常");
        });

        executorService.shutdown();
    }

    public static void test02() throws InterruptedException, ExecutionException {
        //1.实现一个自己的线程池工厂
        ThreadFactory factory = (Runnable r) -> {
            //创建一个线程
            Thread t = new Thread(r);
            //给创建的线程设置UncaughtExceptionHandler对象 里面实现异常的默认逻辑
            t.setUncaughtExceptionHandler((Thread thread1, Throwable e) -> {
                System.out.println("线程工厂设置的exceptionHandler" + e.getMessage());
            });
            return t;
        };

        //2.创建一个自己定义的线程池，使用自己定义的线程工厂
        ExecutorService executorService = new ThreadPoolExecutor(
                1,
                1,
                0,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(10),
                factory);

        Future<Integer> future = executorService.submit(()->{
            throw new RuntimeException("submit 我就是要抛异常");
        });

        //System.out.println(future.get());

        /**
         * 在用submit提交的时候，runable对象被封装成了future ，future 里面的 run方法在处理异常时，
         * try-catch了所有的异常，通过setException(ex);方法设置到了变量outcome里面，
         * 可以通过future.get获取到outcome。
         *
         * 所以在submit提交的时候，里面发生了异常， 是不会有任何抛出信息的。
         * 而通过future.get()可以获取到submit抛出的异常！在submit里面，除了从返回结果里面取到异常之外, 没有其他方法。
         * 因此，在不需要返回结果的情况下，最好用execute ，这样就算没有写try-catch，疏漏了异常捕捉，也不至于丢掉异常信息。
         */

        Thread.sleep(1000);

        executorService.execute(()->{
            throw new RuntimeException("execute 我就是要抛异常");
        });
    }

    public static void test03(){
        //1.创建一个自己定义的线程池
        ExecutorService executorService = new ThreadPoolExecutor(
                2,
                3,
                0,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(10)
        ) {
            //重写afterExecute方法
            @Override
            protected void afterExecute(Runnable r, Throwable t) {
                //这个是excute提交的时候
                if (t != null) {
                    System.out.println("afterExecute里面获取到excute提交的异常信息，处理异常" + t.getMessage());
                }
                //如果r的实际类型是FutureTask 那么是submit提交的，所以可以在里面get到异常
                if (r instanceof FutureTask) {
                    try {
                        Future<?> future = (Future<?>) r;
                        //get获取异常
                        future.get();

                    } catch (Exception e) {
                        System.out.println("afterExecute里面获取到submit提交的异常信息，处理异常" + e);
                    }
                }
            }
        };
        //使用重写afterExecute这种方式，既可以处理execute抛出的异常，也可以处理submit抛出的异常
        executorService.submit(()->{
            throw new RuntimeException("submit 我就是要抛异常");
        });
    }
}
