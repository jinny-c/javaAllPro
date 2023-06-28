package com.example.service.bean;

import com.example.service.RandomBallsService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @description TODO
 * @auth chaijd
 * @date 2023/6/21
 */
public class BeanConvert {
    public static boolean isAllInMy(List<Integer> myDef, BallEnty enty) {
        return isAllNotInMy(myDef, myDef, enty);
    }

    public static boolean isAllNotInMy(List<Integer> myDef, BallEnty enty) {
        return isAllNotInMy(myDef, myDef, enty);
    }

    public static boolean isAllInMy(List<Integer> red, List<Integer> blue, BallEnty enty) {
        boolean bluesIsNotRightful = bluesIsNotRightful(blue, true);
        boolean redsIsNotRightful = redsIsNotRightful(red, true);
        //blue合法
        if (!bluesIsNotRightful) {
            //不在list里
            if (!blue.contains(enty.getBlue())) {
                return false;
            }
        }
        //red合法
        if (!redsIsNotRightful) {
            //不在list里
            if (!red.containsAll(enty.getRed())) {
                return false;
            }
        }
        return true;
    }

    public static boolean isAllNotInMy(List<Integer> red, List<Integer> blue, BallEnty enty) {
        boolean bluesIsNotRightful = bluesIsNotRightful(blue, false);
        boolean redsIsNotRightful = redsIsNotRightful(red, false);
        //blue合法
        if (!bluesIsNotRightful) {
            //在list里
            if (blue.contains(enty.getBlue())) {
                return false;
            }
        }
        //red合法
        if (!redsIsNotRightful) {
            //在list里
            for (Integer e : enty.getRed()) {
                if (red.contains(e)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 是否是不合法的
     *
     * @param my
     * @param isIn
     * @return 合法：false；不合法：true
     */
    private static boolean bluesIsNotRightful(List<Integer> my, boolean isIn) {
        if (my == null || my.isEmpty()) {
            return true;
        }
        //合法list
        List<Integer> rigntful = my.stream().filter(element -> element <= 16).collect(Collectors.toList());
        if (rigntful.isEmpty()) {
            return true;
        }
        if (isIn) {
            return false;
        }
        return rigntful.containsAll(RandomBallsService.BLUELIST);
    }


    /**
     * 是否是不合法的
     *
     * @param my
     * @param isIn
     * @return 合法：false；不合法：true
     */
    private static boolean redsIsNotRightful(List<Integer> my, boolean isIn) {
        if (my == null || my.isEmpty()) {
            return true;
        }
        //合法list
        List<Integer> rigntful = my.stream().filter(element -> element <= 33).collect(Collectors.toList());
        if (rigntful.isEmpty()) {
            return true;
        }

        if (isIn) {
            return rigntful.size() < 6;
        }
        return rigntful.size() > 27;
    }
}
