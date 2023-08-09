package com.example.utils;

import com.example.bean.BallsInfo;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;
import org.jsoup.select.NodeVisitor;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public static String pagerGetBySelect(String url, String select) {
        Document document = getDocument(url);
        Element linkElement = document.select(select).first();
        // 获取绝对UR
        String absoluteHref = linkElement.attr("abs:href");
        return absoluteHref;
    }
    public static String pagerGetBySelect(String url, String select, boolean isLineFeed) {
        Document document = getDocument(url);
        Elements elements = document.select(select);

        List<String> valList = new ArrayList<>();

        elements.traverse(new NodeVisitor() {
            @Override
            public void head(Node node, int i) {
                if (node instanceof TextNode) {
                    String val = ((TextNode) node).text();
                    if (StringUtils.isNotBlank(StringUtils.trim(val))) {
                        valList.add(StringUtils.trim(val));
                    }
                }
            }

            @Override
            public void tail(Node node, int i) {
            }
        });

        return StringUtils.join(valList, isLineFeed ? CommonConstant.line_feed : StringUtils.SPACE);
//        return elements.text();
//        return elements.outerHtml();
    }

    public static String pagerElementsGetByContent(String url, String content) {
        Document document = getDocument(url);
        Elements elementsContainingText = document.getElementsContainingOwnText(content);

        List<String> eleList = new ArrayList<>();
        for (Element element : elementsContainingText) {
            //eleList.add(element.text());
            eleList.add(element.outerHtml());
        }
        return StringUtils.join(eleList, CommonConstant.line_feed);
    }

    public static String pagerGet(String url, String startKey, String endKey) {
        return pagerGet(url, startKey, endKey, PageContentSelectEnums.select_content.getSelectType());
    }

    public static String pagerGet(String url, String startKey, String endKey, String type) {
        log.info("startKey={},endKey={}", startKey, endKey);
        Document document = getDocument(url);
        //String allValue = document.text();
        String allValue = null;
        PageContentSelectEnums selectEnums = PageContentSelectEnums.convertByType(type);
        switch (selectEnums) {
            case select_content:
                allValue = document.text();
                break;
            case select_body:
                allValue = document.body().text();
                break;
            case select_head:
                allValue = document.head().text();
                break;
            case select_html:
                allValue = document.html();
                break;
            default:
                //allValue = document.outerHtml();
                String outerHtml = document.outerHtml().replaceAll("\\\\u\\w{4}", "");
                String unicodeDecoded = unicodeDecode(outerHtml);
                allValue = unicodeDecoded.replaceAll("<.*?>", "");
                break;
        }
//        log.info("1111111=={}",document.text());
//        log.info("2222222=={}",document.body().text());
//        log.info("3333333=={}",document.head().text());
//        log.info("4444444=={}",document.html());
//        log.info("5555555=={}",document.outerHtml());

        String betweenValue = StringUtils.substringBetween(allValue, startKey, endKey);
        //String subValue = allValue.replaceAll("\\\\u\\w{4}", "");
        String subValue = allValue;
        if (StringUtils.isNotBlank(startKey)) {
            subValue = StringUtils.substringAfter(subValue, startKey);

        }
        if (StringUtils.isNotBlank(endKey)) {
            subValue = StringUtils.substringBefore(subValue, endKey);

        }
        return subValue;
    }

    static Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");

    private static String unicodeDecode(String str) {
        Matcher matcher = pattern.matcher(str);
        char ch;
        while (matcher.find()) {
            ch = (char) Integer.parseInt(matcher.group(2), 16);
            str = str.replace(matcher.group(1), ch + "");
        }
        return str;
    }

//    public static void main(String[] args) {
//        String url = "https://www.sdk.cn/details/9pPQD6wqK0Jo8ozvNy#title-8";
//        url = "https://blog.csdn.net/lansefangzhou/article/details/81091407";
//        url = "https://www.xpiaotian.com/book/215870/207674278.html";
//        String startKey = "登陆界面";
//        String endKey = "关注";
//        String content = "下一章";
//        String select = "a[id=pager_next]";
//        String select1 = "div[id=content]";
////        System.out.println(getDocument(url));
////        System.out.println(pagerElementsGetByContent(url, content));
//        System.out.println(pagerGetBySelect(url, select));
//        System.out.println(pagerGetBySelect(url, select1));
//    }

}
