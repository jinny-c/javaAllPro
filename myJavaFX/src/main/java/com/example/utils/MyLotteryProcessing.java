package com.example.utils;

import com.example.my.CommonExecutorService;
import com.example.service.AbstractRandomBalls;
import com.example.service.bean.BallEnty;
import com.example.service.impl.RandomBallsSet;
import com.example.service.impl.RandomBallsStream;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.concurrent.Future;

/**
 * @description 随机数 双色球
 * @auth chaijd
 * @date 2021/7/28
 */
@Slf4j
public class MyLotteryProcessing {

    public static List<String> getBallsByCondations(boolean isInList, String defType, List<Integer> red, List<Integer> blue, Map<Integer, Double> redMp, Map<Integer, Double> blueMp) {
        log.info("isInList={},defType={},red={},blue={},redMp={},blueMp={}", isInList, defType, red, blue, redMp, blueMp);
        List<String> rstList = new ArrayList<>();
        try {
            RandomBallsSet ballsSet = new RandomBallsSet();
            RandomBallsStream ballsStream = new RandomBallsStream();
            BallEnty setEnty = null;
            BallEnty streamEnty = null;
            //RandomBallsSet ballsSet = new RandomBallsSet();
            //RandomBallsStream ballsStream = new RandomBallsStream();
            int count = 0;
            while (true) {
                setEnty = ballsSet.getBalls(isInList, defType, red, blue, redMp, blueMp);
                streamEnty = ballsStream.getBalls(isInList, defType, red, blue, redMp, blueMp);

                if (setEnty.equals(streamEnty)) {
                    log.info("count={},setEnty={},streamEnty={}", count, setEnty, streamEnty);
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

    public static Map<Integer, List<String>> getBallsByExecutor(int conut, boolean isInList, String defType,
                                                                List<Integer> red, List<Integer> blue,
                                                                Map<Integer, Double> redMp, Map<Integer, Double> blueMp, int sameCount) {
        return getBallsByExecutor("3", conut, isInList, defType, red, blue, redMp, blueMp, sameCount);
    }

    public static Map<Integer, List<String>> getBallsByExecutor(String type, int conut, boolean isInList, String defType,
                                                                List<Integer> red, List<Integer> blue,
                                                                Map<Integer, Double> redMp, Map<Integer, Double> blueMp, int sameCount) {
        log.info("type={},sameCount={}", type, sameCount);
        List<Future<List<String>>> rest = new ArrayList<>();
        boolean compareOne = StringUtils.equalsAny(type, "0", "1", "3");
        boolean compareAll = StringUtils.equalsAny(type, "0", "2", "3");
        do {
            conut--;

            //if (sameCount <= 0 || sameCount >= 6) {
            if (compareAll) {
                rest.add(CommonExecutorService.getInstannce().submit(() -> getBallsByCondations(isInList, defType, red, blue, redMp, blueMp)));
            }
            //if (sameCount <= 6) {
            if (compareOne) {
                rest.add(CommonExecutorService.getInstannce().submit(() -> getBallsByCondations(isInList, defType, red, blue, redMp, blueMp, sameCount, new RandomBallsSet())));
                rest.add(CommonExecutorService.getInstannce().submit(() -> getBallsByCondations(isInList, defType, red, blue, redMp, blueMp, sameCount, new RandomBallsStream())));
            }

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

    public static Map<String, List<String>> getBallsByExecutor(String type, int conut, boolean isInList, boolean canRepeat, String defType,
                                                               List<Integer> red, List<Integer> blue,
                                                               Map<Integer, Double> redMp, Map<Integer, Double> blueMp, int sameCount) {
        log.info("type={},sameCount={}", type, sameCount);
        List<Future<List<String>>> rest = new ArrayList<>();
        List<Future<Map<String, List<String>>>> rest2 = new ArrayList<>();

        boolean compareOne = StringUtils.equalsAny(type, "0", "1", "3");
        boolean compareAll = StringUtils.equalsAny(type, "0", "2", "3");
        do {
            conut--;
            if (compareAll) {
                rest.add(CommonExecutorService.getInstannce().submit(() -> getBallsByCondations(isInList, defType, red, blue, redMp, blueMp)));
            }
            if (compareOne) {
                if (canRepeat) {
                    rest.add(CommonExecutorService.getInstannce().submit(() -> getBallsByCondations(isInList, defType, red, blue, redMp, blueMp, sameCount, new RandomBallsSet())));
                    rest.add(CommonExecutorService.getInstannce().submit(() -> getBallsByCondations(isInList, defType, red, blue, redMp, blueMp, sameCount, new RandomBallsStream())));
                } else {
                    //rest2.add(CommonExecutorService.getInstannce().submit(() -> getMyBallsByCondations(isInList, true, defType, red, blue, redMp, blueMp, sameCount)));
                    rest2.add(CommonExecutorService.getInstannce().submit(() -> getMyBallsByCondations(isInList, false, defType, red, blue, redMp, blueMp, sameCount)));
                }
            }
        } while (conut > 0);

        int contKey = 1;
        Map<String, List<String>> restMap = new HashMap<>();

        for (Future<List<String>> future : rest) {
            try {
                List<String> lt = future.get();
                restMap.put(contKey + "", lt);
                contKey++;
            } catch (Exception e) {
                log.info("lt Exception==", e);
            }
        }
        for (Future<Map<String, List<String>>> future2 : rest2) {
            try {
                Map<String, List<String>> mp = future2.get();
                restMap.putAll(mp);
            } catch (Exception e) {
                log.info("mp Exception==", e);
            }
        }

        return restMap;
    }

    public static <T extends AbstractRandomBalls> List<String> getBallsByCondations(boolean isInList, String defType, List<Integer> red, List<Integer> blue,
                                                                                    Map<Integer, Double> redMp, Map<Integer, Double> blueMp, int inCount, T balls) {
        log.info("isInList={},defType={},red={},blue={},redMp={},blueMp={},inCount={}", isInList, defType, red, blue, redMp, blueMp, inCount);
        List<String> rstList = new ArrayList<>();
        try {
            BallEnty enty = balls.getBallsIn(isInList, defType, red, blue, redMp, blueMp, inCount);
            rstList.add(StringUtils.join("balls=", balls.getClass(), ",defType=", defType, ",isInList=", isInList));
            String entyStr = enty.toString();
            Collections.sort(enty.getRed());
            String redStr = enty.getRed().toString();
            rstList.add(entyStr + redStr);
        } catch (Exception e) {
            log.info("Exception==", e);
        }
        return rstList;
    }

    private static Map<String, List<String>> getMyBallsByCondations(boolean isInList, boolean isOrNotAllSame, String defType, List<Integer> red, List<Integer> blue, Map<Integer, Double> redMp, Map<Integer, Double> blueMp, int sameCount) {
        log.info("isInList={},isOrNotAllSame={},defType={},red={},blue={},redMp={},blueMp={},sameCount={}", isInList, isOrNotAllSame, defType, red, blue, redMp, blueMp, sameCount);
        BallEnty entySet, entyStream;
        RandomBallsSet ballsSet = new RandomBallsSet();
        RandomBallsStream ballsStream = new RandomBallsStream();
        int count = 0;
        do {
            count++;
            //entySet = getEntyByCondations(isInList, defType, red, blue, redMp, blueMp, sameCount, new RandomBallsSet());
            entySet = getEntyByCondations(isInList, defType, red, blue, redMp, blueMp, sameCount, ballsSet);
            //entyStream = getEntyByCondations(isInList, defType, red, blue, redMp, blueMp, sameCount, new RandomBallsStream());
            entyStream = getEntyByCondations(isInList, defType, red, blue, redMp, blueMp, sameCount, ballsStream);
            if (isOrNotAllSame) {
                if (!entySet.equals(entyStream)) {
                    continue;
                }
            } else {
                if (entySet.equalsAny(entyStream)) {
                    continue;
                }
            }
            break;
        } while (true);

        Map<String, List<String>> retMap = new HashMap<>();
        retMap.put("entyStream", getMyBallsByCondations(isInList, defType, entyStream, count));
        if (!isOrNotAllSame) {
            retMap.put("entySet", getMyBallsByCondations(isInList, defType, entySet, count));
        }
        return retMap;
    }

    private static List<String> getMyBallsByCondations(boolean isInList, String defType, BallEnty enty, int count) {
        List<String> rstList = new ArrayList<>();
        try {
            rstList.add(StringUtils.join("count=", count, ",defType=", defType, ",isInList=", isInList));
            String entyStr = enty.toString();
            Collections.sort(enty.getRed());
            String redStr = enty.getRed().toString();
            rstList.add(entyStr + redStr);
        } catch (Exception e) {
            log.info("Exception==", e);
        }
        return rstList;
    }

    private static <T extends AbstractRandomBalls> BallEnty getEntyByCondations(boolean isInList, String defType, List<Integer> red, List<Integer> blue,
                                                                                Map<Integer, Double> redMp, Map<Integer, Double> blueMp, int sameCount, T balls) {
        //log.info("isInList={},defType={},red={},blue={},redMp={},blueMp={},sameCount={}", isInList, defType, red, blue, redMp, blueMp, sameCount);
        return balls.getBallsIn(isInList, defType, red, blue, redMp, blueMp, sameCount);
    }

    public static void main(String[] args) {
        //getBallsByExecutor(2, true, "01", null, null, null, null);
//        RandomBallsStream ballsStream = new RandomBallsStream();
//        BallEnty enty1 = ballsStream.getBallsIn(true, "01", null, null, null, null, 6);
//        System.out.println(enty1);
//
//        RandomBallsSet ballsSet = new RandomBallsSet();
//        BallEnty enty2 = ballsSet.getBallsIn(true, "01", null, null, null, null, 6);
//        System.out.println(enty2);

//        System.out.println(getBallsByCondations(true, "01", null, null, null, null, 6, new RandomBallsSet()));
//        System.out.println(getBallsByCondations(true, "01", null, null, null, null, 6, new RandomBallsStream()));
    }

}
