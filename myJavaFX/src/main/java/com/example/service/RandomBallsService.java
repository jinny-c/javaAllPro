package com.example.service;

import com.example.service.bean.BallEnty;
import com.example.service.bean.BeanConvert;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @description TODO
 * @auth chaijd
 * @date 2023/6/21
 */
public interface RandomBallsService {
    List<Integer> BLUELIST = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
            11, 12, 13, 14, 15, 16);
    Double BLUE_PROBABILITIES = 0.0625;

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
    Double RED_PROBABILITIES = (1.0 / 33);

    Integer[] MY_DEFAULT_ARR = {1, 2, 3, 5, 6, 9,
            11, 12, 13, 15, 16, 19,
            21, 22, 23, 25, 26, 29, 31, 32, 33};

    Integer[] MY_369_ARR = {3, 6, 9, 12, 15, 18,
            21, 24, 27, 30, 33};

    BallEnty getBalls();

    BallEnty getBalls(Map<Integer, Double> red, Map<Integer, Double> blue);

    BallEnty getBalls(List<Integer> red, List<Integer> blue, Map<Integer, Double> redMap, Map<Integer, Double> blueMap);

    default BallEnty getBalls(Boolean isInArr) {
        if(isInArr){
            List<Integer> list = Arrays.asList(MY_DEFAULT_ARR);
            return getBalls(isInArr, list, list);
        }
        return getBalls();
    }

//    default BallEnty getBalls(Boolean isInArr, List<Integer> list) {
//        return getBalls(isInArr, list, list);
//    }

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

    default BallEnty getBalls(Boolean isIn, List<Integer> redLt, List<Integer> blueLt, Map<Integer, Double> redMp, Map<Integer, Double> blueMp) {
        List<Integer> reds = null;
        List<Integer> blues = null;
        if (isIn) {
            reds = new ArrayList<>(Arrays.asList(MY_DEFAULT_ARR));

            blues = new ArrayList<>(Arrays.asList(MY_DEFAULT_ARR));
            blues.removeIf(e -> !BLUELIST.contains(e));
        } else {
            reds = new ArrayList<>(REDLIST);
            if (redLt != null) {
                reds.removeIf(redLt::contains);
            }

            blues = new ArrayList<>(BLUELIST);
            if (blueLt != null) {
                blues.removeIf(blueLt::contains);
            }
        }
        if (redMp == null) {
            redMp = new HashMap<>();
        }
        List<Integer> finalReds = reds;
        Map<Integer, Double> filteredRedMap = redMp.entrySet()
                .stream().filter(entry -> finalReds.contains(entry.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        if (blueMp == null) {
            blueMp = new HashMap<>();
        }
        List<Integer> finalBlues = blues;
        Map<Integer, Double> filteredBluedMap = blueMp.entrySet()
                .stream().filter(entry -> finalBlues.contains(entry.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        return getBalls(reds, blues, filteredRedMap, filteredBluedMap);
    }

    default BallEnty getBalls(String type, List<Integer> redLt, List<Integer> blueLt, Map<Integer, Double> redMp, Map<Integer, Double> blueMp) {
        List<Integer> reds = null;
        List<Integer> blues = null;
        switch (type){
            //case "01":
            case "02":
                reds = new ArrayList<>(Arrays.asList(MY_DEFAULT_ARR));
                blues = new ArrayList<>(Arrays.asList(MY_DEFAULT_ARR));
                blues.removeIf(e -> !BLUELIST.contains(e));
                break;
            case "03":
                reds = new ArrayList<>(Arrays.asList(MY_369_ARR));
                blues = new ArrayList<>(Arrays.asList(MY_369_ARR));
                blues.removeIf(e -> !BLUELIST.contains(e));
                break;
            case "04":
                reds = new ArrayList<>(REDLIST);
                blues = new ArrayList<>(BLUELIST);
                if (redLt != null) {
                    reds = redLt;
                }
                if (blueLt != null) {
                    blues = blueLt;
                    blues.removeIf(e -> !BLUELIST.contains(e));
                }
                break;
            default:
                reds = new ArrayList<>(REDLIST);
                blues = new ArrayList<>(BLUELIST);
                break;
        }



        if (redMp == null) {
            redMp = new HashMap<>();
        }
        List<Integer> finalReds = reds;
        Map<Integer, Double> filteredRedMap = redMp.entrySet()
                .stream().filter(entry -> finalReds.contains(entry.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        if (blueMp == null) {
            blueMp = new HashMap<>();
        }
        List<Integer> finalBlues = blues;
        Map<Integer, Double> filteredBluedMap = blueMp.entrySet()
                .stream().filter(entry -> finalBlues.contains(entry.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        return getBalls(reds, blues, filteredRedMap, filteredBluedMap);
    }
}
