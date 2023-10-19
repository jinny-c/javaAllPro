package com.example.utils;

import com.example.service.bean.BallEnty;
import com.example.service.impl.RandomBallsSet;
import com.example.service.impl.RandomBallsStream;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.concurrent.*;

/**
 * @description 随机数 双色球
 * @auth chaijd
 * @date 2021/7/28
 */
@Slf4j
public class MyLotteryProcessing {
    private static final long DEFAULT_ALIVE_TIME = 0L;
    private static final int DEFAULT_CORE_POOL_SIZE = 3;
    private static final int DEFAULT_MAX_POOL_SIZE = 6;
    private static final int DEFAULT_QUEUE_SIZE = 9;

    private static final ExecutorService executor = new ThreadPoolExecutor(DEFAULT_CORE_POOL_SIZE, DEFAULT_MAX_POOL_SIZE
            , DEFAULT_ALIVE_TIME, TimeUnit.SECONDS
            , new ArrayBlockingQueue(DEFAULT_QUEUE_SIZE)
            , new ThreadFactoryBuilder().setNameFormat("async-pool-%d").build());


    public static List<String> getBallsByCondations(boolean isInList, String defType, List<Integer> red, List<Integer> blue, Map<Integer, Double> redMp, Map<Integer, Double> blueMp) {
        log.info("isInList={},defType={},red={},blue={},redMp={},blueMp={}", isInList, defType, red, blue, redMp, blueMp);
        List<String> rstList = new ArrayList<>();
        try {
            BallEnty setEnty = null;
            BallEnty streamEnty = null;
            RandomBallsSet ballsSet = new RandomBallsSet();
            RandomBallsStream ballsStream = new RandomBallsStream();
            int count = 0;
            while (true) {
                setEnty = ballsSet.getBalls(isInList, defType, red, blue, redMp, blueMp);
                streamEnty = ballsStream.getBalls(isInList, defType, red, blue, redMp, blueMp);

                if (setEnty.equals(streamEnty)) {
                    log.info("setEnty={},streamEnty={}", setEnty, streamEnty);
                    break;
                }
                count++;
                if (count % 13659200 == 0) {
                    log.info("count={}", count);
                }
            }
            rstList.add(StringUtils.join("count=", count, ",defType=", defType, ",isInList=", isInList));
            String entyStr = streamEnty.toString();
            Collections.sort(streamEnty.getRed());
            String redStr = streamEnty.getRed().toString();
            rstList.add(entyStr + redStr);
        } catch (Exception e) {
            log.info("Exception==", e);
        }
        return rstList;
    }

    public static Map<Integer, List<String>> getBallsByExecutor(int conut, boolean isInList, String defType, List<Integer> red, List<Integer> blue, Map<Integer, Double> redMp, Map<Integer, Double> blueMp) {
        List<Future<List<String>>> rest = new ArrayList<>();
        do {
            rest.add(executor.submit(() -> getBallsByCondations(isInList, defType, red, blue, redMp, blueMp)));
            conut--;
        } while (conut > 0);

        int contKey = 1;
        Map<Integer, List<String>> restMap = new HashMap<>();
        for (Future<List<String>> future : rest) {
            try {
                List<String> lt = future.get();
                restMap.put(contKey, lt);
                contKey++;
            } catch (Exception e) {
                log.info("Exception==", e);
            }
        }
        return restMap;
    }

    public static void main(String[] args) {
        getBallsByExecutor(2, true, "01", null, null, null, null);
    }
}
