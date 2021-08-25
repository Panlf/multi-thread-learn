package com.plf.learn.threadpool;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author panlf
 * @date 2021/8/24
 */
public class SimpleThreadPool {
    /**
     * 如果当前线程池中的线程数目小于corePoolSize，则每来一个任务，就会创建一个线程去执行这个任务;
     * 如果当前线程池中的线程数目>=corePoolSize，则每来一个任务，会尝试将其添加到任务缓存队列当中，
     * 若添加成功，则该任务会等待空闲线程将其取出去执行;若添加失败（一般来说是任务缓存队列已满）,则会尝试创建新的线程去执行这个任务;
     * 如果队列已经满了，则在总线程数不大于maximumPoolSize的前提下，
     * 则创建新的线程如果当前线程池中的线程数目达到maximumPoolSize，则会采取任务拒绝策略进行处理;
     * 如果线程池中的线程数量大于corePoolSize时，如果某线程空闲时间超过keepAliveTime，
     * 线程将被终止，直至线程池中的线程数目不大于corePoolSize;如果允许为核心池中的线程设置存活时间，那么核心池中的线程空闲时间超过keepAliveTime，线程也会被终止。
     * @param args
     */
    public static void main(String[] args) {
        /**
         * IO密集 该任务需要大量的IO阻塞，如果是在单线程中执行堵塞，解决阻塞情况下，可以使用多线程技术
         *  CPU运算能力，不会浪费等待资源。多配置线程数=2*CPU核数
         *
         * CPU密集    该任务的时候，不会产生大量IO阻塞，CPU运行的时候速度特别快，配置线程数=CPU核数
         *
         */
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1,
                2,
                0l,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(3));
        for(int i=0;i<5;i++){
            int finalI = i;
            threadPoolExecutor.execute(()->{
                System.out.println(Thread.currentThread().getName()+"任务"+ finalI);
            });
        }

        threadPoolExecutor.shutdown();
    }
}
