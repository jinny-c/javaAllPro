package com.example.service;

import com.example.service.bean.BallEnty;
import com.example.utils.GsonUtils;
import com.example.utils.LotteryProcessing;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @description 抽象类
 * @auth chaijd
 * @date 2023/6/21
 */
@Slf4j
public abstract class AbstractRandomBalls {
    public List<Integer> BLUELIST = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
            11, 12, 13, 14, 15, 16);
    public Double BLUE_PROBABILITIES = 0.0625;

    public List<Integer> REDLIST = new ArrayList<Integer>() {
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
    public Double RED_PROBABILITIES = (1.0 / 33);

    Integer[] MY_DEFAULT_ARR = {1, 2, 3, 5, 6, 9,
            11, 12, 13, 15, 16, 19,
            21, 22, 23, 25, 26, 29, 31, 32, 33};

    Integer[] MY_369_ARR = {3, 6, 9, 12, 15, 18,
            21, 24, 27, 30, 33};

    ConcurrentHashMap<String, List<Integer>> BALL_HASH_MAP = new ConcurrentHashMap<>();

    private void convertNeedList(boolean isInList, String redKey, String blueKey, List<Integer> redLt, List<Integer> blueLt) {
        List<Integer> reds = null;
        List<Integer> blues = null;
        if (isInList) {
            if (redLt == null || redLt.size() < 6) {
                List<Integer> defReds = new ArrayList<>(Arrays.asList(MY_DEFAULT_ARR));
                if (redLt != null) {
                    reds = Stream.concat(defReds.stream(), redLt.stream())
                            .distinct()
                            .collect(Collectors.toList());
                } else {
                    reds = defReds;
                }
            } else {
                reds = redLt;
            }

            List<Integer> defBlues = new ArrayList<>(Arrays.asList(MY_DEFAULT_ARR));
            defBlues.removeIf(e -> !BLUELIST.contains(e));

            if (blueLt == null || blueLt.size() < 1) {
                blues = defBlues;
                if (blueLt != null) {
                    blues = Stream.concat(defBlues.stream(), blueLt.stream())
                            .distinct()
                            .collect(Collectors.toList());
                }
            } else {
                blues = blueLt;
            }
            //过滤
            blues.removeIf(e -> !BLUELIST.contains(e));
            if (blues.size() < 1) {
                blues = defBlues;
            }

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
        BALL_HASH_MAP.put(redKey, reds);
        BALL_HASH_MAP.put(blueKey, blues);
    }

    public BallEnty getBalls(boolean isInList, String defType, List<Integer> redLt, List<Integer> blueLt, Map<Integer, Double> redMp, Map<Integer, Double> blueMp) {
        String redKey = defType + "<red>" + GsonUtils.toJson(redLt);
        String blueKey = defType + "<blue>" + GsonUtils.toJson(blueLt);
        List<Integer> reds = BALL_HASH_MAP.get(redKey);
        List<Integer> blues = BALL_HASH_MAP.get(blueKey);

        if (CollectionUtils.isEmpty(reds) || CollectionUtils.isEmpty(blues)) {
            log.info("lt having null");
            switch (defType) {
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
                    convertNeedList(isInList, redKey, blueKey, redLt, blueLt);
                    reds = BALL_HASH_MAP.get(redKey);
                    blues = BALL_HASH_MAP.get(blueKey);
                    break;
                case "01":
                default:
                    reds = new ArrayList<>(REDLIST);
                    blues = new ArrayList<>(BLUELIST);
                    break;
            }

            BALL_HASH_MAP.put(redKey, reds);
            BALL_HASH_MAP.put(blueKey, blues);
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

    public abstract BallEnty getBalls(List<Integer> redLt, List<Integer> blueLt, Map<Integer, Double> redMp, Map<Integer, Double> blueMp);

    protected Integer getBlueByMp(List<Integer> blueLt, Map<Integer, Double> blueMp) {
        Map<Integer, Double> blueMap = blueLt.stream().collect(Collectors.toMap(key -> key, key -> BLUE_PROBABILITIES));
        blueMap.putAll(blueMp);
        //概率
        List<Double> blueProbabilities = new ArrayList<>(blueMap.values());
        return blueLt.get(LotteryProcessing.getRandomIndex(blueProbabilities, new Random()));
    }

    protected List<Integer> getRedsByMp(List<Integer> redLt, Map<Integer, Double> redMp) {
        Map<Integer, Double> redMap = redLt.stream().collect(Collectors.toMap(key -> key, key -> RED_PROBABILITIES));
        redMap.putAll(redMp);
        //概率
        List<Double> redProbabilities = new ArrayList<>(redMap.values());
        List<Integer> reds = new ArrayList<>(redLt);
        List<Integer> myRed = new ArrayList<>();
        while (true) {
            int l = LotteryProcessing.getRandomIndex(redProbabilities, new Random());
            myRed.add(reds.get(l));
            reds.remove(l);
            redProbabilities.remove(l);
            if (myRed.size() > 5) {
                break;
            }
        }
        return myRed;
    }

}
