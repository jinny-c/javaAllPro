package com.example.service;

import com.example.service.bean.BallEnty;
import com.example.service.bean.BeanConvert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @description TODO
 * @auth chaijd
 * @date 2023/6/21
 */
public interface RandomBallsService {
    List<Integer> BLUELIST = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
            11, 12, 13, 14, 15, 16);
    List<Integer> REDLIST = new ArrayList<Integer>() {
        {
            add(1);
            add(2);
            add(3);
            add(4);
            add(5);
            add(6);
            add(7);
            add(8);
            add(9);
            add(10);
            add(11);
            add(12);
            add(13);
            add(14);
            add(15);
            add(16);
            add(17);
            add(18);
            add(19);
            add(20);
            add(21);
            add(22);
            add(23);
            add(24);
            add(25);
            add(26);
            add(27);
            add(28);
            add(29);
            add(30);
            add(31);
            add(32);
            add(33);
        }
    };
    Integer[] MY_ARR = {1, 2, 3, 5, 6, 9,
            11, 12, 13, 15, 16, 19,
            21, 22, 23, 25, 26, 29, 31, 32, 33};

    BallEnty getBalls();

    default BallEnty getBalls(Boolean isInArr) {
        return getBalls(isInArr, Arrays.asList(MY_ARR));
    }

    default BallEnty getBalls(Boolean isInArr, List<Integer> list) {
        return getBalls(isInArr, list, list);
    }

    default BallEnty getBalls(Boolean isInArr, List<Integer> red, List<Integer> blue) {
        BallEnty enty = null;
        while (true) {
            enty = getBalls();
            if (isInArr) {
                if (BeanConvert.isAllInMy(red, blue, enty)) {
                    break;
                }
            } else {
                if (BeanConvert.isAllNotInMy(red, blue, enty)) {
                    break;
                }
            }
        }
        return enty;
    }
}
