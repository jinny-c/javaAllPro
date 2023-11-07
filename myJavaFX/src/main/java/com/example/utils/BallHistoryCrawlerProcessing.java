package com.example.utils;


import com.example.bean.BallsInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @description TODO
 * @auth chaijd
 * @date 2023/2/13
 */
@Slf4j
public class BallHistoryCrawlerProcessing {

    private static String BASEURL = "https://datachart.500.com/ssq/history/newinc/history.php";

    public static List<BallsInfo> crawlerBall(Map<String, String> reqVo) {
        log.info("crawlerBall start,reqVo={}", reqVo);
        if (null == reqVo) {
            reqVo = new HashMap<>();
            reqVo.put(CommonConstant.filed_url, BASEURL);
        }
        if (StringUtils.isBlank(reqVo.get(CommonConstant.filed_url))) {
            reqVo.put(CommonConstant.filed_url, BASEURL);
        }
        if (StringUtils.isNoneBlank(reqVo.get(CommonConstant.filed_start), reqVo.get(CommonConstant.filed_end))) {
            //03001代表03年第一期彩票  21036代表21年第36期彩票
            //String url = StringUtils.join(new Object[]{BASEURL, "?start=", reqVo.get(CommonConstant.filed_start), "&end=", reqVo.get(CommonConstant.filed_end)});
            String url = StringUtils.join(BASEURL, "?start=", reqVo.get(CommonConstant.filed_start), "&end=", reqVo.get(CommonConstant.filed_end));
            //String url = BASEURL + "?start=" + reqVo.get(CommonConstant.filed_start) + "&end=" + reqVo.get(CommonConstant.filed_end);
            reqVo.put(CommonConstant.filed_url, url);
        }

        Document doc = PageProcessing.getDocument(reqVo.get(CommonConstant.filed_url));

        List<BallsInfo> list = PageProcessing.ballsGet(doc);
        return list;
    }

    public static List<String> crawlerBallByInfo(List<BallsInfo> ballsInfos, int defCount) {
        log.info("crawlerBallByInfo start");
        List<String> list = new ArrayList<>();
        int forCount = defCount;
        if (forCount > ballsInfos.size()) {
            forCount = ballsInfos.size();
        }
        for (int i = 0; i < forCount; i++) {
            BallsInfo info = ballsInfos.get(i);
            //date：red-red，blue
            list.add(StringUtils.join(info.getBallDate(), "：",
                    StringUtils.join(info.getRedBalls(), "-"), ",", info.getBlueBall()));
        }
        return list;
    }

    public static Map<String, String> crawlerBallStatistics(List<BallsInfo> ballsInfos) {
        //红
        List<String> allRedList = ballsInfos.stream().flatMap(info -> info.getRedBalls().stream()).collect(Collectors.toList());
        //蓝
        List<String> allBlueList = ballsInfos.stream().map(BallsInfo::getBlueBall).collect(Collectors.toList());
        Map<String, String> restMap = new HashMap<>();
        restMap.put("AllSortByValue", textValue(allRedList, allBlueList, "all"));


        Map<Integer, List<BallsInfo>> groupMap = IntStream.range(0, ballsInfos.size())
                .boxed()
                .collect(Collectors.groupingBy(i -> i % 3, Collectors.mapping(ballsInfos::get, Collectors.toList())));
        for (List<BallsInfo> infos : groupMap.values()) {
            //红
            List<String> redList = infos.stream().flatMap(info -> info.getRedBalls().stream()).collect(Collectors.toList());
            //蓝
            List<String> blueList = infos.stream().map(BallsInfo::getBlueBall).collect(Collectors.toList());
            restMap.put(infos.get(0).getBallDate() + "subSortByValue", textValue(redList, blueList, infos.get(0).getBallDate()));
        }

//        Map<String, Long> blueMap = statisticsFrequency(allBlueList);
//        Map<String, Long> redMap = statisticsFrequency(allRedList);
//        //restMap.put("redMap",redMap.toString());
//        //restMap.put("blueMap",blueMap.toString());
//        restMap.put("redMap-sortByValue", "结果排序：" + sortByValue(redMap, 0).toString());
//        restMap.put("blueMap-sortByValue", "排序后：" + sortByValue(blueMap, 0).toString());

        return restMap;
    }

    public static List<String> crawlerBallAndStatistics(List<BallsInfo> ballsInfos) {
        List<String> values = new ArrayList<>();
        //红
        List<String> allRedList = ballsInfos.stream().flatMap(info -> info.getRedBalls().stream()).collect(Collectors.toList());
        values.add(StringUtils.join(sortlist(allRedList), "-"));
        Map<String, Long> redMap = statisticsFrequency(allRedList);
        values.add(sortByValue(redMap, 0).toString());
        //蓝
        List<String> allBlueList = ballsInfos.stream().map(BallsInfo::getBlueBall).collect(Collectors.toList());
        values.add(StringUtils.join(sortlist(allBlueList), "-"));
        Map<String, Long> blueMap = statisticsFrequency(allBlueList);
        values.add(sortByValue(blueMap, 0).toString());
        return values;
    }

    public static String textValue(List<String> redList, List<String> blueList, String pre) {
        Map<String, Long> blueMap = statisticsFrequency(blueList);
        Map<String, Long> redMap = statisticsFrequency(redList);
        return StringUtils.join(pre, "：\n\r redMap=", sortByValue(redMap, 0).toString(), "\n\r blueMap=", sortByValue(blueMap, 0).toString());
    }

    private Integer convertIntDef(String val, int def) {
        try {
            return Integer.parseInt(val);
        } catch (Exception e) {
            log.error("convertIntDef Exception:{}", e.getMessage());
        }
        return def;
    }

    /**
     * 统计
     *
     * @param falcons
     * @return
     */
    private static Map<String, Long> statisticsFrequency(List<String> falcons) {
        //统计
        Map<String, Long> map = falcons.stream().collect(Collectors.groupingBy(k -> k, Collectors.counting()));
        //log.info("size={}", map.size());
        return map;
    }

    /**
     * key排序 并截取
     *
     * @param map
     * @param intercept
     * @return
     */
    private Map<String, Long> sortByKey(Map<String, Long> map, int intercept) {
        //根据key排序
        Map<String, Long> sorceKey = new TreeMap<>(map);
        log.info("sorceKey={}", sorceKey);
        if (intercept == 0 || intercept >= map.size()) {
            return sorceKey;
        }
        Map<String, Long> limitMap = new TreeMap<>();
        sorceKey.entrySet().stream().limit(intercept).forEachOrdered(e -> limitMap.put(e.getKey(), e.getValue()));
        return limitMap;
    }

    /**
     * value排序 并截取
     *
     * @param map
     * @param intercept
     * @return
     */
    private static Map<String, Long> sortByValue(Map<String, Long> map, int intercept) {
//        Map<String, Long> sorceMap1 = new LinkedHashMap<>();
//        map.entrySet().stream().sorted(Map.Entry.<String, Long>comparingByValue()).forEachOrdered(e->sorceMap1.put(e.getKey(),e.getValue()));
        Map<String, Long> sorceValue = new LinkedHashMap<>();
//        map.entrySet().stream().sorted(Map.Entry.<String, Long>comparingByValue().reversed()).forEach(e->sorceMap2.put(e.getKey(),e.getValue()));
        //forEachOrdered 通过happensbefore原则保证了它的内存可见性
        //根据value排序
        if (intercept == 0 || intercept >= map.size()) {
            map.entrySet().stream().sorted(Map.Entry.<String, Long>comparingByValue().reversed()).forEachOrdered(e -> sorceValue.put(e.getKey(), e.getValue()));
            //log.info("sorceValue={}", sorceValue);
        } else {
            map.entrySet().stream().sorted(Map.Entry.<String, Long>comparingByValue().reversed()).limit(intercept).forEachOrdered(e -> sorceValue.put(e.getKey(), e.getValue()));
        }
        return sorceValue;
    }


    private static List<String> sortlist(List<String> list) {
        // 使用流操作去重并排序
        List<String> distinctSorted = list.stream()
                .distinct() // 去重
                .sorted()    // 排序
                .collect(Collectors.toList());

        return distinctSorted;
    }
}
