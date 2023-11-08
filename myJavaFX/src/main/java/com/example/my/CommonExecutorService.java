package com.example.my;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @description 单例线程池
 * @auth chaijd
 * @date 2023/11/7
 */
@Slf4j
public class CommonExecutorService {
    private static final long DEFAULT_ALIVE_TIME = 3L;
    private static final int DEFAULT_CORE_POOL_SIZE = 9;
    private static final int DEFAULT_MAX_POOL_SIZE = 12;
    private static final int DEFAULT_QUEUE_SIZE = 32;

    private static ExecutorService executor = null;


    private CommonExecutorService() {
    }

    public static ExecutorService getInstannce() {
        //log.info("getInstannce executor");
        if (executor == null || executor.isShutdown()) {
            syncInit();
        }
        return executor;
    }

    private static synchronized void syncInit() {
        if (executor == null || executor.isShutdown()) {
            log.info("synchronized syncInit executor");
            executor = new ThreadPoolExecutor(DEFAULT_CORE_POOL_SIZE, DEFAULT_MAX_POOL_SIZE
                    , DEFAULT_ALIVE_TIME, TimeUnit.SECONDS
                    , new ArrayBlockingQueue(DEFAULT_QUEUE_SIZE)
                    //setDaemon(true) 设置为守护线程，以免影响javaFX的退出
                    , new ThreadFactoryBuilder().setNameFormat("daemon-pool-%d").setDaemon(true).build());
        }
    }

    // 关闭线程池
    public static void shutdown() {
        log.info("shutdown");
        if (executor != null && !executor.isShutdown()) {
            log.info("shutdown executor");
            executor.shutdown();
        }
    }

}
