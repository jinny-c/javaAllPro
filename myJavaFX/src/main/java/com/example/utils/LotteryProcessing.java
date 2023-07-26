package com.example.utils;

import com.example.bean.BallsInfo;
import com.example.service.RandomBallsService;
import com.example.service.bean.BallEnty;
import com.example.service.impl.RandomBallsImpl1;
import com.example.service.impl.RandomBallsImpl3;
import com.google.common.base.Splitter;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @description 随机数 双色球
 * @auth chaijd
 * @date 2021/7/28
 */
@Slf4j
public class LotteryProcessing {
    public static List<String> getBallsByCondations(Boolean isIn, List<Integer> red, List<Integer> blue,
                                                    Map<Integer, Double> redMp, Map<Integer, Double> blueMp) {
        log.info("isIn={},red={},blue={},redMp={},blueMp={}", isIn, red, blue, redMp, blueMp);
        List<String> rstList = new ArrayList<>();
        try {
            BallEnty enty1 = null;
            BallEnty enty2 = null;
            RandomBallsService randomBalls = new RandomBallsImpl3();
            int count = 0;
            while (true) {
                enty1 = randomBalls.getBalls(isIn, red, blue, redMp, blueMp);
                enty2 = randomBalls.getBalls(isIn, red, blue, redMp, blueMp);

                if (enty1.equals(enty2)) {
                    log.info("enty1={},enty2={}", enty1, enty2);
                    break;
                }
                count++;
                if (count % 136592 == 0) {
                    log.info("count={}", count);
                }
            }
            log.info("end count={},isIn={}", count, isIn);
            rstList.add(StringUtils.join("count=", count, ",isIn=", isIn));
            String entyStr = enty2.toString();
            Collections.sort(enty2.getRed());
            String redStr = enty2.getRed().toString();
            rstList.add(entyStr + redStr);
        } catch (Exception e) {
            log.info("===", e);
        }
        return rstList;
    }

    /**
     * 根据条件获取
     *
     * @param isIn
     * @param red
     * @param blue
     * @return
     */
    public static List<String> getBallsByCondations(Boolean isIn, List<Integer> red, List<Integer> blue) {
        log.info("isIn={},red={},blue={}", isIn, red, blue);
        List<String> rstList = new ArrayList<>();
        try {
            BallEnty enty1 = null;
            BallEnty enty2 = null;
            RandomBallsService randomBalls1 = new RandomBallsImpl1();
            RandomBallsService randomBalls2 = new RandomBallsImpl3();
            int count = 0;
            while (true) {
                enty1 = randomBalls1.getBalls(isIn, red, blue);
                enty2 = randomBalls2.getBalls(isIn, red, blue);

                if (enty1.equals(enty2)) {
                    log.info("enty1={},enty2={}", enty1, enty2);
                    if (!enty1.getRed().contains(enty1.getBlue())) {
                        break;
                    }
                }
                count++;
                if (count % 136592 == 0) {
                    log.info("count={}", count);
                }
            }
            log.info("end count={},isIn={}", count, isIn);
            rstList.add(StringUtils.join("count=", count, ",isIn=", isIn));
            String entyStr = enty2.toString();
            Collections.sort(enty2.getRed());
            String redStr = enty2.getRed().toString();
            rstList.add(entyStr + redStr);
        } catch (Exception e) {
            log.info("===", e);
        }
        return rstList;
    }

    public static List<String> getBallsByCondations(Boolean isIn, List<Integer> list) {
        return getBallsByCondations(isIn, list, null);
    }

    public static List<String> getBallsByCondations(Boolean isIn, Boolean only) {
        log.info("isIn={},only={}", isIn, only);
        List<String> rstList = new ArrayList<>();
        try {
            BallEnty enty1 = null;
            BallEnty enty2 = null;
            RandomBallsService randomBalls1 = new RandomBallsImpl1();
            RandomBallsService randomBalls2 = new RandomBallsImpl3();
            int count = 0;
            while (true) {
                if (only) {
                    enty1 = randomBalls1.getBalls();
                    enty2 = randomBalls2.getBalls();
                } else {
                    enty1 = randomBalls1.getBalls(isIn);
                    enty2 = randomBalls2.getBalls(isIn);
                }

                if (enty1.equals(enty2)) {
                    log.info("enty1={},enty2={}", enty1, enty2);
                    if (!enty1.getRed().contains(enty1.getBlue())) {
                        break;
                    }
                }
                if (enty1.getRed().equals(enty2.getRed())) {
                    log.info("enty1={},enty2={}", enty1, enty2);
                    if (!enty2.getRed().contains(enty2.getBlue())) {
                        break;
                    }
                }
                count++;
                if (count % 136592 == 0) {
                    log.info("count={}", count);
                }
            }
            log.info("end count={},only={},isIn={}", count, only, isIn);
            rstList.add(StringUtils.join("count=", count, ",only=", only, ",isIn=", isIn));
            if (null != enty2) {
                String entyStr = enty2.toString();
                Collections.sort(enty2.getRed());
                String redStr = enty2.getRed().toString();
                rstList.add(entyStr + redStr);
            }
        } catch (Exception e) {
            log.info("===", e);
        }
        return rstList;
    }

    /**
     * 要根据概率随机获取给定数组中的某个值
     * 计算所有概率的总和。
     * 生成一个介于0和第一步计算出的总和之间的随机数。
     * 遍历数组，从随机数中减去每个元素的概率。当随机数变小于或等于零时，返回相应的值。
     *
     * @param probabilities
     * @param random
     * @return
     */

    public static int getRandomIndex(List<Double> probabilities, Random random) {
        //double randomNum = new Random().nextDouble() * sum;
        double sum = probabilities.stream().mapToDouble(Double::doubleValue).sum();
        return getRandomIndex(probabilities, sum, random);
    }

    public static int getRandomIndex(List<Double> probabilities, double sum, Random random) {
        //double randomNum = new Random().nextDouble() * sum;
        double randomNum = random.nextDouble() * sum;
        return getIndex(probabilities, randomNum);
    }

    public static int getRandomIndex(List<Double> probabilities) {
        double sum = probabilities.stream().mapToDouble(Double::doubleValue).sum();
        return getRandomIndex(probabilities, sum);
    }

    public static int getRandomIndex(List<Double> probabilities, double sum) {
        double randomNum = Math.random() * sum;
        return getIndex(probabilities, randomNum);
    }

    private static int getIndex(List<Double> probabilities, double randomNum) {
        for (int i = 0; i < probabilities.size(); i++) {
            randomNum -= probabilities.get(i);
            if (randomNum <= 0) {
                return i;
            }
        }
        // 概率不等于1时的默认情况
        return 0;
    }


    private static BallEnty getNeed1(ExecutorService executor) {
        Future<BallEnty> future = executor.submit(new LotteryGetTask(new RandomBallsImpl3()));
        try {
            return future.get();
        } catch (Exception ignored) {
        }
        return null;
    }

    private static BallEnty getNeed(ExecutorService executor) {
        List<BallEnty> needList = null;
        int count = 0;
        boolean allEqual = false;

        while (!allEqual) {
//            List<Future<BallEnty>> futures = new ArrayList<>();
//
//            for (int i = 0; i < 2; i++) {
//                futures.add(executor.submit(new LotteryGetTask(new RandomBallsImpl3())));
//                //System.out.println(count);
//
//            }
//
//            needList = futures.stream()
//                    .map(LotteryProcessing::futureGet)
//                    .collect(Collectors.toList());
            needList = new ArrayList<>();
            //RandomBallsService randomBalls2 = new RandomBallsImpl3();
            RandomBallsService randomBalls1 = new RandomBallsImpl3();
            needList.add(randomBalls1.getBalls());
            needList.add(randomBalls1.getBalls());

            allEqual = needList.stream().distinct().limit(2).count() <= 1;

            count++;
            if (count % 136592 == 0) {
                log.info("count={}", count);
            }
        }

        BallEnty ret = needList.get(0);
        System.out.println(ret);
        return ret;
    }

    private static BallEnty futureGet(Future<BallEnty> future) {
        try {
            return future.get();
        } catch (Exception ignored) {
        }
        return new BallEnty();
    }

    public static Map<String, String> lotteryResult(String content) {
        List<String> list = new ArrayList<>(Arrays.asList(content.split(Pattern.compile(CommonConstant.content_pattern).pattern())));
        List<BallsInfo> ballsInfos = list.stream()
                .map(LotteryProcessing::convertByStr2)
                .filter(Objects::nonNull).collect(Collectors.toList());
        //红
        List<String> allRedList = ballsInfos.stream().flatMap(info -> info.getRedBalls().stream()).collect(Collectors.toList());
        //蓝
        List<String> allBlueList = ballsInfos.stream().map(BallsInfo::getBlueBall).collect(Collectors.toList());
        Map<String, String> restMap = new HashMap<>();
        restMap.put("red", StringUtils.join(sortAndDeDuplication(allRedList), "-"));
        restMap.put("blue", StringUtils.join(sortAndDeDuplication(allBlueList), "-"));
        return restMap;
    }
    private static BallsInfo convertByStr2(String str) {
        try {
            str = StringUtils.substringAfter(str, "：");
            String[] arr = str.split(",");
            BallsInfo info = new BallsInfo();
            info.setBlueBall(arr[1]);
            info.setRedBalls(Arrays.asList(arr[0].split("-")));
            return info;
        } catch (Exception e) {
            log.error("Exception={}", e.getMessage());
        }
        return null;
    }

    public static Map<String, String> lotteryStatistics(String content) {
        List<String> list = new ArrayList<>(Arrays.asList(content.split(Pattern.compile(CommonConstant.content_pattern).pattern())));
        List<String> need = list.stream().filter(e -> {
            if (StringUtils.isNotBlank(e)) {
                return e.startsWith("BallEnty");
            }
            return false;
        }).collect(Collectors.toList());

        List<BallsInfo> ballsInfos = convertByString(need);
        //红
        List<String> allRedList = ballsInfos.stream().flatMap(info -> info.getRedBalls().stream()).collect(Collectors.toList());
        //蓝
        List<String> allBlueList = ballsInfos.stream().map(BallsInfo::getBlueBall).collect(Collectors.toList());
        Map<String, String> restMap = new HashMap<>();
        String all = StringUtils.join("all",
                "：\n\r redMap=", StringUtils.join(sortAndDeDuplication(allRedList), "-"),
                "\n\r blueMap=", StringUtils.join(sortAndDeDuplication(allBlueList), "-"));
        restMap.put("All Value", all);
        //概率
        restMap.put("AllSortByValue Chance", BallHistoryCrawlerProcessing.textValue(allRedList, allBlueList, "Chance"));

        return restMap;
    }

    private static List<String> sortAndDeDuplication(List<String> list) {
        List<String> rst = new ArrayList<>(new HashSet<>(list));
        Collections.sort(rst, Comparator.comparingInt(Integer::parseInt));
        return rst;
    }

    private static List<BallsInfo> convertByString(List<String> need){
        return need.stream()
                .map(LotteryProcessing::convertByStr)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private static BallsInfo convertByStr(String str) {
        try {
            if (StringUtils.contains(str, "(")) {
                str = StringUtils.substringAfter(str, "(");
            }
            if (StringUtils.contains(str, ")")) {
                str = StringUtils.substringBefore(str, ")");
            }

            Splitter.MapSplitter smsp = Splitter.on(",").trimResults().limit(2).withKeyValueSeparator("=");
            Map<String, String> smspMp = smsp.split(str);
            BallsInfo info = new BallsInfo();
            info.setBlueBall(smspMp.get("blue"));
            info.setRedBalls(GsonUtils.toType(smspMp.get("red"), new TypeToken<List<String>>() {
            }.getType()));
            return info;
        } catch (Exception e) {
            log.error("Exception={}", e.getMessage());
        }
        return null;
    }

    public static void main(String[] args) {
//        List<Integer> values = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
//                11, 12, 13, 14, 15, 16);
//
//        List<Double> probabilities = Arrays.asList(0.1, 0.05, 0.2, 0.15, 0.05, 0.1, 0.05, 0.1, 0.05, 0.05,
//                0.02, 0.01, 0.03, 0.02, 0.02, 0.02);
//
//        List<Double> probabilities2 = Collections.nCopies(16, 0.0625);
//
//        for (int i = 0; i < 10; i++) {
//            int randomIndex = getRandomIndex(probabilities);
//            int randomValue = values.get(randomIndex);
//            System.out.println("randomValue：" + randomValue);
//        }

//        ExecutorService executor = Executors.newFixedThreadPool(1);
//        getNeed1(executor);
//
//        executor.shutdown();

//        String str = "blue=15, red=[32, 3, 24, 5, 18, 8]";
//        Splitter.MapSplitter smsp = Splitter.on(",").trimResults().limit(2).withKeyValueSeparator("=");
//        Map<String, String> smspMp = smsp.split(str);
//        System.out.println(smspMp);
//        for(String val:Splitter.on(',').trimResults().split(str)){
//            System.out.println( val);
//        }
//        for (String st : str.split(",",2)){
//            System.out.println(st);
//        }

        //List<String> parts = Splitter.on('=').trimResults().splitToList(str);
    }
}
