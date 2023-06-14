package com.example.utils;

import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

/**
 * @description TODO
 * @auth chaijd
 * @date 2023/6/14
 */
public class PageProcessing {
    @SneakyThrows
    public static Document getDocument(String url) {
        //log.info("getDocument start,url={}", url);
        Connection mozilla = Jsoup.connect(URLDecoder.decode(url, String.valueOf(StandardCharsets.UTF_8))).userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/94.0.4606.54 Safari/537.36");
        Document document = mozilla.get();
        //Document document = mozilla.timeout(5000).get();
        return document;
    }

    public static String pagerGet(String url, String startKey, String endKey) {
        Document document = getDocument(url);
        String allValue = document.text();
        //String betweenValue = StringUtils.substringBetween(allValue, startKey, endKey);
        String subValue = allValue;
        if (StringUtils.isNotBlank(startKey)) {
            subValue = StringUtils.substringAfter(allValue, startKey);

        }
        if (StringUtils.isNotBlank(endKey)) {
            subValue = StringUtils.substringBefore(allValue, endKey);

        }
        return subValue;
    }

    public static void main(String[] args) {
        String url = "https://www.sdk.cn/details/9pPQD6wqK0Jo8ozvNy#title-8";
        url = "https://blog.csdn.net/lansefangzhou/article/details/81091407";
        String startKey = "登陆界面";
        String endKey = "关注";
//        System.out.println(getDocument(url));
        System.out.println(pagerGet(url, startKey, endKey));
    }
}
