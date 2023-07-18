package com.example.utils;

import com.example.bean.BallsInfo;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @description TODO
 * @auth chaijd
 * @date 2023/6/14
 */
@Slf4j
public class PageProcessing {
    @SneakyThrows
    public static Document getDocument(String url) {
        log.info("getDocument start,url={}", url);
        Connection mozilla = Jsoup.connect(URLDecoder.decode(url, String.valueOf(StandardCharsets.UTF_8))).userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/94.0.4606.54 Safari/537.36");
        mozilla.timeout(50 * 1000);
        Document document = mozilla.get();
        //Document document = mozilla.timeout(5000).get();
        return document;
    }

    public static List<BallsInfo> ballsGet(Document doc) {
        Elements trs = doc.select("tbody[id=tdata]").select("tr");
        List<BallsInfo> ballList = new ArrayList<>();
        trs.forEach(e -> {
            Elements tds = e.select("td");
            String date = tds.get(0).text();
            String[] redArr = new String[]{tds.get(1).text(), tds.get(2).text(), tds.get(3).text(),
                    tds.get(4).text(), tds.get(5).text(), tds.get(6).text()};
            List<String> reds = Arrays.asList(redArr.clone());
            String red = StringUtils.join(redArr, "-");
            String blue = tds.get(7).text();
            //list.add(StringUtils.join(date, "期，红：", red, "，蓝：", blue));
            BallsInfo info = new BallsInfo();
            info.setBallDate(date);
            info.setRedBalls(reds);
            info.setBlueBall(blue);
            ballList.add(info);
        });
        return ballList;
    }

    public static String pagerGet(String url, String startKey, String endKey) {
        log.info("startKey={},endKey={}", startKey, endKey);
        Document document = getDocument(url);
        String allValue = document.text();
        //String betweenValue = StringUtils.substringBetween(allValue, startKey, endKey);
        String subValue = allValue;
        if (StringUtils.isNotBlank(startKey)) {
            subValue = StringUtils.substringAfter(subValue, startKey);

        }
        if (StringUtils.isNotBlank(endKey)) {
            subValue = StringUtils.substringBefore(subValue, endKey);

        }
        return subValue;
    }

//    public static void main(String[] args) {
//        String url = "https://www.sdk.cn/details/9pPQD6wqK0Jo8ozvNy#title-8";
//        url = "https://blog.csdn.net/lansefangzhou/article/details/81091407";
//        String startKey = "登陆界面";
//        String endKey = "关注";
////        System.out.println(getDocument(url));
//        System.out.println(pagerGet(url, startKey, endKey));
//    }

}
