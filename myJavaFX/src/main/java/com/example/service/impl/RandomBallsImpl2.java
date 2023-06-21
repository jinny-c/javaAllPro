package com.example.service.impl;

import com.example.service.RandomBallsService;
import com.example.service.bean.BallEnty;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

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
}
