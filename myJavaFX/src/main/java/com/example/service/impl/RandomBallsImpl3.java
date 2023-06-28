package com.example.service.impl;

import com.example.service.RandomBallsService;
import com.example.service.bean.BallEnty;
import com.example.utils.LotteryProcessing;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @description TODO
 * @auth chaijd
 * @date 2023/6/21
 */
public class RandomBallsImpl3 implements RandomBallsService {
    @Override
    public BallEnty getBalls() {
        List<Integer> reds = new ArrayList<>(REDLIST);

        Set<Integer> myRed = new HashSet<>();
        while (true) {
            //获取随机数
            int i = (int) (Math.random() * reds.size() + 1);
            int idx = i - 1;
            myRed.add(reds.get(idx));
            if (myRed.size() >= 6) {
                break;
            }
            reds.remove(idx);
        }
        int myBlue = (int) (Math.random() * 16 + 1);

        BallEnty enty = new BallEnty();
        enty.setBlue(myBlue);
        enty.setRed(new ArrayList<>(myRed));
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
        List<Integer> my = new ArrayList<>();
        while (true) {
            int l = LotteryProcessing.getRandomIndex(redProbabilities);
            my.add(reds.get(l));
            reds.remove(l);
            redProbabilities.remove(l);
            if (my.size() > 5) {
                break;
            }
        }
        BallEnty enty = new BallEnty();
        enty.setBlue(BLUELIST.get(LotteryProcessing.getRandomIndex(blueProbabilities)));
        enty.setRed(my);
        return enty;
    }
}
