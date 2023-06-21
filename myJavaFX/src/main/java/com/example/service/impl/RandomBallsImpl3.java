package com.example.service.impl;

import com.example.service.RandomBallsService;
import com.example.service.bean.BallEnty;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
}
