package com.example.service.bean;

import com.example.service.RandomBallsService;

import java.util.List;

/**
 * @description TODO
 * @auth chaijd
 * @date 2023/6/21
 */
public class BeanConvert {
    public static boolean isAllInMy(List<Integer> my, BallEnty enty) {
        if (blues(my) && reds(my)) {
            return true;
        }
        // blue && red
        if (my.contains(enty.getBlue())) {
            if (my.containsAll(enty.getRed())) {
                return true;
            }
        }
        return false;
    }

    public static boolean isAllNotInMy(List<Integer> my, BallEnty enty) {
        if (blues(my) || reds(my)) {
            return true;
        }
        //blue || red
        if (my.contains(enty.getBlue())) {
            return false;
        }
        for (Integer e : enty.getRed()) {
            if (my.contains(e)) {
                return false;
            }
        }
        return true;
    }

    public static boolean blues(List<Integer> my) {
        if (my.size() < 1) {
            return true;
        }
//        if (!isIn(my, 16)) {
//            return true;
//        }
        if (my.containsAll(RandomBallsService.BLUELIST)) {
            return true;
        }
        return false;
    }

    public static boolean reds(List<Integer> my) {
        if (my.size() > 27) {
            return true;
        }
        if (my.size() < 6) {
            return true;
        }
//        if (!isIn(my, 33)) {
//            return true;
//        }
        return false;
    }

    public static boolean isIn(List<Integer> my, Integer max) {
        for (Integer e : my) {
            if (e > max) {
                return false;
            }
        }
        return true;
    }
}
