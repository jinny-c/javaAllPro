package com.example.service.impl;

import com.example.service.RandomBallsService;
import com.example.service.bean.BallEnty;

import java.util.*;

/**
 * @description TODO
 * @auth chaijd
 * @date 2023/6/21
 */
@Deprecated
public class RandomBallsImpl2 implements RandomBallsService {
    @Override
    public BallEnty getBalls() {
        Set<Integer> myRed = new HashSet<>();
        while (true) {
            int i = (int) (Math.random() * 33 + 1);
            myRed.add(i);
            if (myRed.size() >= 6) {
                break;
            }
        }
        int myBlue = (int) (Math.random() * 16 + 1);
        //System.out.println("myRed=" + myRed + "myBlue=" + myBlue);

        BallEnty enty = new BallEnty();
        enty.setBlue(myBlue);
        enty.setRed(new ArrayList<>(myRed));
        return enty;
    }

    @Override
    public BallEnty getBalls(Map<Integer, Double> red, Map<Integer, Double> blue) {
        return getBalls();
    }

    @Override
    public BallEnty getBalls(List<Integer> red, List<Integer> blue, Map<Integer, Double> redMap, Map<Integer, Double> blueMap) {
        return getBalls();
    }

}
