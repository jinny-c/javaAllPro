package com.example.service.impl;

import com.example.service.RandomBallsService;
import com.example.service.bean.BallEnty;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
}
