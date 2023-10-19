package com.example.service.impl;

import com.example.service.AbstractRandomBalls;
import com.example.service.bean.BallEnty;
import org.apache.commons.collections4.MapUtils;

import java.util.*;

/**
 * @description TODO
 * @auth chaijd
 * @date 2023/10/18
 */
public class RandomBallsSet extends AbstractRandomBalls {
    @Override
    public BallEnty getBalls(List<Integer> redLt, List<Integer> blueLt, Map<Integer, Double> redMp, Map<Integer, Double> blueMp) {
        Integer myBlue;
        List<Integer> myRed;
        if (MapUtils.isEmpty(redMp)) {
            List<Integer> reds = new ArrayList<>(redLt);
            Set<Integer> setRed = new HashSet<>();
            while (true) {
                //获取随机数
                int i = (int) (Math.random() * reds.size() + 1);
                int idx = i - 1;
                setRed.add(reds.get(idx));
                if (setRed.size() >= 6) {
                    break;
                }
                reds.remove(idx);
            }
            myRed = new ArrayList<>(setRed);
        } else {
            myRed = getRedsByMp(redLt, redMp);
        }

        if (MapUtils.isEmpty(blueMp)) {
            int idx = (int) (Math.random() * blueLt.size());
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
