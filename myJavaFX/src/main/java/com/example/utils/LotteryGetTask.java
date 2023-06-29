package com.example.utils;

import com.example.service.RandomBallsService;
import com.example.service.bean.BallEnty;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.Callable;

/**
 * @description TODO
 * @auth chaijd
 * @date 2023/6/28
 */
@Slf4j
public class LotteryGetTask implements Callable<BallEnty> {
    private RandomBallsService ballsService;
    private Map<Integer, Double> red = null;
    private Map<Integer, Double> blue = null;

    public LotteryGetTask(RandomBallsService ballsService) {
        this.ballsService = ballsService;
    }

    public LotteryGetTask(RandomBallsService ballsService, Map<Integer, Double> red, Map<Integer, Double> blue) {
        this.ballsService = ballsService;
        this.red = red;
        this.blue = blue;
    }

    @Override
    public BallEnty call() throws Exception {
        BallEnty enty1 = null;
        BallEnty enty2 = null;
        int count = 0;
        while (true) {
            enty1 = ballsService.getBalls(red, blue);
            enty2 = ballsService.getBalls(red, blue);
            if (enty1.equals(enty2)) {
                log.info("countcount{},enty1={},enty2={}", count, enty1, enty2);
                if (!enty1.getRed().contains(enty1.getBlue())) {
                    return enty1;
                }
            }
            if (count == Integer.MAX_VALUE) {
                log.info("countcount{},enty1={},enty2={}", count, enty1, enty2);
                return enty1;
            }
            count++;
            if (count % 136592 == 0) {
                log.info("count={}", count);
            }
        }
    }
}
