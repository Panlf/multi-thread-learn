package com.plf.learn.concurrent.semaphore;

import cn.hutool.core.thread.ThreadUtil;
import com.plf.learn.common.NamedThreadFactory;


import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

/**
 * @author panlf
 * @date 2021/12/29
 */
public class SemaphoreRateLimiter {

    private static final Semaphore semaphore = new Semaphore(3,true);

    private static final ExecutorService  taskPool =
            Executors.newFixedThreadPool(10,new NamedThreadFactory("task"));

    private static final ScheduledExecutorService scheduledPool =
            Executors.newSingleThreadScheduledExecutor(new NamedThreadFactory("engine"));

    public static LinkedBlockingQueue<Task> taskLinkedBlockingQueue;

    public static class Task {
        private String taskName;
        private Runnable work;

        public String getTaskName() {
            return taskName;
        }

        public void setTaskName(String taskName) {
            this.taskName = taskName;
        }

        public Runnable getWork() {
            return work;
        }

        public void setWork(Runnable work) {
            this.work = work;
        }
    }



    public static void init(){
        taskLinkedBlockingQueue = new LinkedBlockingQueue<>();
        engineOn();
    }

    private static void engineOn(){
        scheduledPool.scheduleAtFixedRate(()->{
            if(taskLinkedBlockingQueue.isEmpty()){
                System.out.println("队列为空，无任务需要执行");
            }else{
                if(semaphore.tryAcquire()){
                    try{
                        Task task = taskLinkedBlockingQueue.poll();
                        assert task != null;
                        System.out.println(Thread.currentThread().getName()+"===> 任务名称为【"+task.getTaskName()+"】获得执行许可");
                        taskPool.submit(task.getWork());
                        System.out.println(Thread.currentThread().getName()+"===> 任务名称为【"+task.getTaskName()+"】提交了执行");
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }else {
                    System.out.println("执行任务数量已经达到限制，无法执行任务");
                }
            }
        },0,1,TimeUnit.SECONDS);
    }

    public static void addTask(String taskName,Runnable runnable){

        Task task = new Task();
        task.setTaskName(taskName);
        task.setWork(()->{
            try{
                runnable.run();
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                semaphore.release();
            }
        });
        taskLinkedBlockingQueue.add(task);
    }

    public static void main(String[] args) {
        SemaphoreRateLimiter.init();
        Random random = new Random();
        for(int i=0;i<10;i++){
            String taskName = "task"+i;
            SemaphoreRateLimiter.addTask(taskName,()->{
                try {
                    System.out.println(Thread.currentThread().getName()+"===>"+taskName+"咔擦咔擦工作中!@#$%...");
                    Thread.sleep(5000);
                    System.out.println(Thread.currentThread().getName()+"===>"+taskName+"咔擦咔擦工作结束");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
