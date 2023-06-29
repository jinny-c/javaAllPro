package com.example.utils;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @description TODO
 * @auth chaijd
 * @date 2023/6/28
 */
public class RandomNumberGenerator {
    private static final int MAX_NUMBER = 10;
    private static final int NUM_THREADS = 4;
    //private static volatile int[] randomNumbers = new int[NUM_THREADS];
    private static volatile List<Integer> list = new CopyOnWriteArrayList<>();
    private static volatile boolean foundMatchingNumbers = false;
    // 生成随机数
    private static volatile Random random = new Random();
    private static final Object lock = new Object();  // 创建一个私有的锁对象
    private static volatile Set<Integer> set = new HashSet<>();

    public static void main(String[] args) {
        Thread[] threads = new Thread[NUM_THREADS];

        for (int i = 0; i < NUM_THREADS; i++) {
            threads[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    do {
                        int randomNumber = random.nextInt(MAX_NUMBER) + 1;

                        // 将随机数存储到数组中
                        int threadIndex = Integer.parseInt(Thread.currentThread().getName());
                        //randomNumbers[threadIndex] = randomNumber;
                        list.add(randomNumber);
                        System.out.println(threadIndex + ":" + randomNumber);
                        // 检查是否存在相同的随机数
                        checkMatchingNumbers();
                    } while (!foundMatchingNumbers);
                }

                private void checkMatchingNumbers() {
                    // 同步代码块
                    synchronized (lock) {
                        // 执行同步操作
                        if (list.size() >= NUM_THREADS) {
                            System.out.println(list);
                            set = new HashSet<>(list);
                            if (set.size() == 1) {
                                foundMatchingNumbers = true;
                                notifyAll();
                            } else {
                                list = new LinkedList<>();
                            }
                        }
                        if (foundMatchingNumbers) {
                            System.out.println(list);
                        }
                    }
                }
            }, String.valueOf(i));
            threads[i].start();
        }

        // 打印相同的随机数
        System.out.println("The matching numbers are: " + list.get(0));
    }
}
