package com.example.utils;

import com.example.service.RandomBallsService;
import com.example.service.bean.BallEnty;
import com.example.service.impl.RandomBallsImpl1;
import com.example.service.impl.RandomBallsImpl3;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @description 随机数 双色球
 * @auth chaijd
 * @date 2021/7/28
 */
@Slf4j
public class LotteryProcessing {
    /**
     * 根据条件获取
     *
     * @param isIn
     * @param red
     * @param blue
     * @return
     */
    public static List<String> getBallsByCondations(Boolean isIn, List<Integer> red, List<Integer> blue) {
        log.info("isIn={},red={},blue={}", isIn, red, blue);
        List<String> rstList = new ArrayList<>();
        try {
            BallEnty enty1 = null;
            BallEnty enty2 = null;
            RandomBallsService randomBalls1 = new RandomBallsImpl1();
            RandomBallsService randomBalls2 = new RandomBallsImpl3();
            int count = 0;
            while (true) {
                enty1 = randomBalls1.getBalls(isIn, red, blue);
                enty2 = randomBalls2.getBalls(isIn, red, blue);

                if (enty1.equals(enty2)) {
                    log.info("enty1={},enty2={}", enty1, enty2);
                    if (!enty1.getRed().contains(enty1.getBlue())) {
                        break;
                    }
                }
                count++;
                if (count % 136592 == 0) {
                    log.info("count={}", count);
                }
            }
            log.info("end count={},isIn={}", count, isIn);
            rstList.add(StringUtils.join("count=", count, ",isIn=", isIn));
            if (null != enty2) {
                String entyStr = enty2.toString();
                Collections.sort(enty2.getRed());
                String redStr = enty2.getRed().toString();
                rstList.add(entyStr + redStr);
            }
        } catch (Exception e) {
            log.info("===", e);
        }
        return rstList;
    }

    public static List<String> getBallsByCondations(Boolean isIn, List<Integer> list) {
        return getBallsByCondations(isIn, list, null);
    }

    public static List<String> getBallsByCondations(Boolean isIn, Boolean only) {
        List<String> rstList = new ArrayList<>();
        try {
            BallEnty enty1 = null;
            BallEnty enty2 = null;
            RandomBallsService randomBalls1 = new RandomBallsImpl1();
            RandomBallsService randomBalls2 = new RandomBallsImpl3();
            int count = 0;
            while (true) {
                if (only) {
                    enty1 = randomBalls1.getBalls();
                    enty2 = randomBalls2.getBalls();
                } else {
                    enty1 = randomBalls1.getBalls(isIn);
                    enty2 = randomBalls2.getBalls(isIn);
                }

                if (enty1.equals(enty2)) {
                    log.info("enty1={},enty2={}", enty1, enty2);
                    if (!enty1.getRed().contains(enty1.getBlue())) {
                        break;
                    }
                }
                if (enty1.getRed().equals(enty2.getRed())) {
                    log.info("enty1={},enty2={}", enty1, enty2);
                    if (!enty2.getRed().contains(enty2.getBlue())) {
                        break;
                    }
                }
                count++;
                if (count % 136592 == 0) {
                    log.info("count={}", count);
                }
            }
            log.info("end count={},only={},isIn={}", count, only, isIn);
            rstList.add(StringUtils.join("count=", count, ",only=", only, ",isIn=", isIn));
            if (null != enty2) {
                String entyStr = enty2.toString();
                Collections.sort(enty2.getRed());
                String redStr = enty2.getRed().toString();
                rstList.add(entyStr + redStr);
            }
        } catch (Exception e) {
            log.info("===", e);
        }
        return rstList;
    }
}
