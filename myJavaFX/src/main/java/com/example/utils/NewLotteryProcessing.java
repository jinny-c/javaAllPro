package com.example.utils;

import com.example.my.CommonExecutorService;
import com.example.service.AbstractRandomBalls;
import com.example.service.bean.BallEnty;
import com.example.service.bean.LotteryResultDto;
import com.example.service.impl.RandomBallsSet;
import com.example.service.impl.RandomBallsStream;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

/**
 * @description 随机数 双色球
 * @auth chaijd
 * @date 2021/7/28
 */
@Slf4j
public class NewLotteryProcessing {

    public static LotteryResultDto getBallsByCondations(boolean isInList, String defType, List<Integer> red, List<Integer> blue, Map<Integer, Double> redMp, Map<Integer, Double> blueMp) {
        log.info("isInList={},defType={},red={},blue={},redMp={},blueMp={}", isInList, defType, red, blue, redMp, blueMp);
        LotteryResultDto resultDto = new LotteryResultDto();
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
                if (count % 1365920 == 0) {
                    log.info("count={}", count);
                }
            }

            resultDto.setBallEnty(streamEnty);
            resultDto.setCount(count);
            resultDto.setDefType(defType);
            resultDto.setIsInList(isInList);
        } catch (Exception e) {
            log.info("Exception==", e);
        }
        return resultDto;
    }

    public static Map<String, LotteryResultDto> getBallsByExecutor(String type, boolean isInList, boolean canRepeat, String defType,
                                                                   List<Integer> red, List<Integer> blue,
                                                                   Map<Integer, Double> redMp, Map<Integer, Double> blueMp, int sameCount) {
        log.info("type={},sameCount={}", type, sameCount);
        List<Future<LotteryResultDto>> rest = new ArrayList<>();
        List<Future<Map<String, LotteryResultDto>>> rest2 = new ArrayList<>();

        boolean compareOne = StringUtils.equalsAny(type, "0", "1", "3");
        boolean compareAll = StringUtils.equalsAny(type, "0", "2", "3");
        boolean isOrNotAllSame = false;
        if (sameCount < 0 || sameCount > 6) {
            isOrNotAllSame = true;
        }

        if (compareAll) {
            rest.add(CommonExecutorService.getInstannce().submit(() -> getBallsByCondations(isInList, defType, red, blue, redMp, blueMp)));
        }

        if (compareOne) {
            if (canRepeat) {
                rest.add(CommonExecutorService.getInstannce().submit(() -> getBallsByCondations(isInList, defType, red, blue, redMp, blueMp, sameCount, new RandomBallsSet())));
                rest.add(CommonExecutorService.getInstannce().submit(() -> getBallsByCondations(isInList, defType, red, blue, redMp, blueMp, sameCount, new RandomBallsStream())));
            } else {
                boolean finalIsOrNotAllSame = isOrNotAllSame;
                rest2.add(CommonExecutorService.getInstannce().submit(() -> getMyBallsByCondations(isInList, finalIsOrNotAllSame, defType, red, blue, redMp, blueMp, sameCount)));
            }
        }

        int contKey = 1;
        Map<String, LotteryResultDto> restMap = new HashMap<>();

        for (Future<LotteryResultDto> future : rest) {
            try {
                LotteryResultDto dto = future.get();
                restMap.put(contKey + "", dto);
                contKey++;
            } catch (Exception e) {
                log.info("lt Exception==", e);
            }
        }
        for (Future<Map<String, LotteryResultDto>> future2 : rest2) {
            try {
                Map<String, LotteryResultDto> mp = future2.get();
                restMap.putAll(mp);
            } catch (Exception e) {
                log.info("mp Exception==", e);
            }
        }

        return restMap;
    }

    public static <T extends AbstractRandomBalls> LotteryResultDto getBallsByCondations(boolean isInList, String defType, List<Integer> red, List<Integer> blue,
                                                                                        Map<Integer, Double> redMp, Map<Integer, Double> blueMp, int inCount, T balls) {
        log.info("isInList={},defType={},red={},blue={},redMp={},blueMp={},inCount={}", isInList, defType, red, blue, redMp, blueMp, inCount);
        LotteryResultDto resultDto = new LotteryResultDto();
        try {
            BallEnty enty = balls.getBallsIn(isInList, defType, red, blue, redMp, blueMp, inCount);

            resultDto.setBallEnty(enty);
            resultDto.setCount(0);
            resultDto.setDefType(defType);
            resultDto.setIsInList(isInList);
            resultDto.setIsOrNotAllSame(false);
        } catch (Exception e) {
            log.info("Exception==", e);
        }
        return resultDto;
    }

    private static Map<String, LotteryResultDto> getMyBallsByCondations(boolean isInList, boolean isOrNotAllSame, String defType, List<Integer> red, List<Integer> blue, Map<Integer, Double> redMp, Map<Integer, Double> blueMp, int sameCount) {
        log.info("isInList={},isOrNotAllSame={},defType={},red={},blue={},redMp={},blueMp={},sameCount={}", isInList, isOrNotAllSame, defType, red, blue, redMp, blueMp, sameCount);
        BallEnty entySet, entyStream;
        RandomBallsSet ballsSet = new RandomBallsSet();
        RandomBallsStream ballsStream = new RandomBallsStream();
        int count = 0;
        do {
            count++;
            if (count % 1365920 == 0) {
                log.info("count={}", count);
            }
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

        Map<String, LotteryResultDto> retMap = new HashMap<>();
        LotteryResultDto resultDto1 = new LotteryResultDto();
        resultDto1.setBallEnty(entyStream);
        resultDto1.setCount(count);
        resultDto1.setDefType(defType);
        resultDto1.setIsInList(isInList);
        resultDto1.setIsOrNotAllSame(isOrNotAllSame);

        retMap.put("entyStream", resultDto1);
        if (!isOrNotAllSame) {
            LotteryResultDto resultDto2 = new LotteryResultDto();
            resultDto2.setBallEnty(entySet);
            resultDto2.setCount(count);
            resultDto2.setDefType(defType);
            resultDto2.setIsInList(isInList);
            resultDto2.setIsOrNotAllSame(isOrNotAllSame);
            retMap.put("entySet", resultDto2);
        }
        return retMap;
    }

    private static <T extends AbstractRandomBalls> BallEnty getEntyByCondations(boolean isInList, String defType, List<Integer> red, List<Integer> blue,
                                                                                Map<Integer, Double> redMp, Map<Integer, Double> blueMp, int sameCount, T balls) {
        //log.info("isInList={},defType={},red={},blue={},redMp={},blueMp={},sameCount={}", isInList, defType, red, blue, redMp, blueMp, sameCount);
        return balls.getBallsIn(isInList, defType, red, blue, redMp, blueMp, sameCount);
    }

}
