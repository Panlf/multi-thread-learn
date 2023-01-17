package com.plf.learn.concurrent.countdownlatch;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * CountDownLatch计算分数
 * @author panlf
 * @date 2021/8/29
 */
public class ScoreCountDownLatch implements Runnable{
    //创建初始化3个线程的线程池
    private final ExecutorService threadPool = Executors.newFixedThreadPool(3);
    //保存每个学生的平均成绩
    private final ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
    private final CountDownLatch countDownLatch = new CountDownLatch(3);

    private void count() {
        for (int i = 0; i < 3; i++) {
            threadPool.execute(() -> {
                //计算每个学生的平均成绩,代码略()假设为60~100的随机数
                int score = (int) (Math.random() * 40 + 60);
                try {
                    Thread.sleep(Math.round(Math.random() * 1000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                map.put(Thread.currentThread().getName(), score);
                System.out.println(Thread.currentThread().getName() + "同学的平均成绩为" + score);
                countDownLatch.countDown();
            });
        }
        this.run();
        threadPool.shutdown();
    }

    @Override
    public void run() {
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int result = 0;
        Set<String> set = map.keySet();
        for (String s : set) {
            result += map.get(s);
        }
        System.out.println("三人平均成绩为:" + (result / 3) + "分");
    }

    //阻塞主线程
    public static void main(String[] args) {
        long now = System.currentTimeMillis();
        ScoreCountDownLatch scoreCountDownLatch= new ScoreCountDownLatch();
        scoreCountDownLatch.count();
        long end = System.currentTimeMillis();
        System.out.println(end - now);
    }
}
