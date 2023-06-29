package com.example.service.impl;

import com.example.service.RandomBallsService;
import com.example.service.bean.BallEnty;
import com.example.utils.LotteryProcessing;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * @description TODO
 * @auth chaijd
 * @date 2023/6/21
 */
public class RandomBallsImpl1 implements RandomBallsService {
    @Override
    public BallEnty getBalls() {
        List<Integer> reds = new ArrayList<>(REDLIST);
        Random random = new Random();
        List<Integer> my = new ArrayList<>();
        while (true) {
            int len = reds.size();
            int l = random.nextInt(len);
            my.add(reds.get(l));
            reds.remove(l);
            if (my.size() > 5) {
                break;
            }
        }
        BallEnty enty = new BallEnty();
        enty.setBlue(BLUELIST.get(random.nextInt(15) + 1));
        enty.setRed(my);
        return enty;
    }

    @Override
    public BallEnty getBalls(Map<Integer, Double> red, Map<Integer, Double> blue) {
        Map<Integer, Double> redMap = REDLIST.stream().collect(Collectors.toMap(key -> key, key -> RED_PROBABILITIES));
        Map<Integer, Double> blueMap = BLUELIST.stream().collect(Collectors.toMap(key -> key, key -> BLUE_PROBABILITIES));
        if (null != red) {
            redMap.putAll(red);
        }
        if (null != blue) {
            blueMap.putAll(blue);
        }
        //概率
        List<Double> blueProbabilities = new ArrayList<>(blueMap.values());
        List<Double> redProbabilities = new ArrayList<>(redMap.values());

        List<Integer> reds = new ArrayList<>(REDLIST);
        Random random = new Random();
        List<Integer> my = new ArrayList<>();
        while (true) {
            int l = LotteryProcessing.getRandomIndex(redProbabilities, random);
            my.add(reds.get(l));
            reds.remove(l);
            redProbabilities.remove(l);
            if (my.size() > 5) {
                break;
            }
        }
        BallEnty enty = new BallEnty();
        enty.setBlue(BLUELIST.get(LotteryProcessing.getRandomIndex(blueProbabilities, random)));
        enty.setRed(my);
        return enty;
    }

    @Override
    public BallEnty getBalls(List<Integer> red, List<Integer> blue, Map<Integer, Double> redMap, Map<Integer, Double> blueMap) {
        return getBalls();
    }
}
