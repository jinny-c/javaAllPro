package com.example.service.impl;

import com.example.service.AbstractRandomBalls;
import com.example.service.bean.BallEnty;
import org.apache.commons.collections4.MapUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @description TODO
 * @auth chaijd
 * @date 2023/10/18
 */
public class RandomBallsStream extends AbstractRandomBalls {
    @Override
    public BallEnty getBalls(List<Integer> redLt, List<Integer> blueLt, Map<Integer, Double> redMp, Map<Integer, Double> blueMp) {
        Integer myBlue;
        List<Integer> myRed;
        if (MapUtils.isEmpty(redMp)) {
            List<Integer> givenList = new ArrayList<>(redLt);
            Collections.shuffle(givenList, new Random());
            myRed = givenList.stream().limit(6).collect(Collectors.toList());
        } else {
            myRed = getRedsByMp(redLt, redMp);
        }

        if (MapUtils.isEmpty(blueMp)) {
            int idx = new Random().nextInt(blueLt.size());
            myBlue = blueLt.get(idx);
        } else {
            myBlue = getBlueByMp(blueLt, blueMp);
        }

        BallEnty enty = new BallEnty();
        enty.setBlue(myBlue);
        enty.setRed(myRed);
        return enty;
    }
}
