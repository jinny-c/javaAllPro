package com.example.utils;

import com.example.bean.BallsInfo;
import com.google.common.base.Splitter;
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

import javax.annotation.PostConstruct;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @description TODO
 * @auth chaijd
 * @date 2023/6/14
 */
@Slf4j
public class PageProcessing {
    private static AbstractCacheFactory<Document> cache = new AbstractCacheFactory<Document>(new CacheConfigBean()) {
        @Override
        protected Document loadByKey(String key) throws Exception {
            return loadDateByKey(key);
        }
    };
    //private AbstractCacheFactory<Document> cache;


    private static Map<String, String> needParams = new HashMap<String, String>() {{
        put(key_method, "GET");
    }};

    private static String key_method = "method";
    private static String key_cookies = "cookies";

    @PostConstruct
    private void init() {
        log.info("init start");
    }


//    @SneakyThrows(Exception.class)
//    public static Document getDocumentByRest(String url) {
//        log.info("getDocumentByRest start,url={}", url);
//        if (!StringUtils.startsWithAny(url, "http://", "https://")) {
//            url = "http://" + url;
//        }
//        RestTemplate restTemplate = new RestTemplate();
//        String htmlContent = restTemplate.getForObject(url, String.class);
//        Document document = Jsoup.parse(htmlContent);
//        return document;
//    }

    private static String convertUrl(String url) {
        log.info("convertUrl start,url={}", url);
        if (StringUtils.startsWithAny(url, "http://", "https://")) {
            return url;
        }
        url = "http://" + url;
        log.info("convertUrl end,url={}", url);
        return url;
    }

    @SneakyThrows(Exception.class)
    public static Document getDocumentByHttp(String url) {
        return getDocumentByHttp(url, needParams.get(key_method), needParams.get(key_cookies));
    }

    @SneakyThrows(Exception.class)
    public static Document getDocumentByHttp(String url, String method, String cookies) {
        String finallyUrl = convertUrl(url);
        URL urlObj = new URL(finallyUrl);
        HttpURLConnection connection = (HttpURLConnection) urlObj.openConnection();
        connection.setRequestMethod(method);
        connection.setRequestProperty("Cookie", cookies);
        InputStream inputStream = connection.getInputStream();
        return Jsoup.parse(inputStream, "UTF-8", finallyUrl);
//        Document document = Jsoup.parse(inputStream, "UTF-8", finallyUrl);
//        return document;
    }

    @SneakyThrows(Exception.class)
    public static Document getDocumentByConnect(String url) {
        Map<String, String> cookies = null;
        String needCookies = needParams.get(key_cookies);
        if (StringUtils.isNotBlank(needCookies)) {
            cookies = Splitter.on("&").trimResults().withKeyValueSeparator(Splitter.on("=").trimResults()).split(needCookies);
        }
        return getDocumentByConnect(url, needParams.get(key_method), cookies);
    }

    @SneakyThrows(Exception.class)
    public static Document getDocumentByConnect(String url, String method, Map<String, String> cookies) {
        Connection mozilla = Jsoup.connect(URLDecoder.decode(convertUrl(url), String.valueOf(StandardCharsets.UTF_8))).userAgent(RandomUserAgent.getRandomUserAgent());
        mozilla.timeout(30 * 1000);
        boolean isNull = null == cookies || cookies.isEmpty();
        if (!isNull) {
            mozilla.cookies(cookies);
        }
        if (StringUtils.equalsIgnoreCase(method, "GET")) {
            return mozilla.get();
        }
        return mozilla.post();
//        Document document = mozilla.get();
//        return document;
    }

    @SneakyThrows(Exception.class)
    public static Document loadDateByKey(String url) {
        Document document = null;
        try {
            document = getDocumentByConnect(url);
            //return getDocumentByHttp(url);
        } catch (Exception e) {
            //log.debug("loadDateByKey Exception", e);
            log.error("getDocumentByConnect Exception={}", e.getMessage());
            //return getDocumentByRest(url);
            document = getDocumentByHttp(url);
            //return getDocumentByConnect(url);
        }
        return document;
    }

    @SneakyThrows(Exception.class)
    public static Document getDocument(String url) {
        return cache.getCacheByKey(convertUrl(url));
    }

    @SneakyThrows(Exception.class)
    public static Document getDocument(String url, String method, String cookies, boolean change) {
        changeDef(method, cookies, change);
        return cache.getCacheByKey(convertUrl(url));
    }

    private static void changeDef(String method, String cookies, boolean change) {
        log.info("changeDef method={},change={}", method, change);
        if (needParams == null) {
            log.info("needParams is null");
            needParams = new HashMap<>();
            needParams.put(key_method, StringUtils.isNotBlank(method) ? method : "GET");
            needParams.put(key_cookies, cookies);
            return;
        }
        if (StringUtils.isBlank(needParams.get(key_method))) {
            log.info("key_method is null");
            needParams.put(key_method, StringUtils.isNotBlank(method) ? method : "GET");
        }
        if (change) {
            needParams.put(key_method, StringUtils.isNotBlank(method) ? method : "GET");
            needParams.put(key_cookies, cookies);
        }
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

    public static String pagerGetBySelect(Document document, String select) {
        //Document document = getDocument(url);
        Element linkElement = document.select(select).first();
        if (null == linkElement) {
            return null;
        }
        // 获取绝对UR
        String absoluteHref = linkElement.attr("abs:href");
        return absoluteHref;
    }

    public static String pagerGetBySelect(String url, String select, boolean isLineFeed) {
        Document document = getDocument(url);
        return pagerGetBySelect(document, select, isLineFeed);
    }

    public static String pagerGetBySelect(Document document, String select, boolean isLineFeed) {

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

    public static String pagerOneElementGetByContent(Document document, String content) {
        Element linkElement = document.getElementsContainingOwnText(content).first();
        if (null == linkElement) {
            return null;
        }
        // 获取绝对UR
        String absoluteHref = linkElement.attr("abs:href");
        return absoluteHref;
    }

    public static String pagerElementsGetByContent(Document document, String content) {
        Elements elementsContainingText = document.getElementsContainingOwnText(content);

        List<String> eleList = new ArrayList<>();
        for (Element element : elementsContainingText) {
            //eleList.add(element.text());
            eleList.add(element.outerHtml());
        }
        return StringUtils.join(eleList, CommonConstant.line_feed);
    }

    public static String pagerGet(Document document, String startKey, String endKey, String type) {
        log.info("startKey={},endKey={}", startKey, endKey);
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
//        url = "https://www.itshang.com/as/35285/19799725.html";
//        url = "http://www.ixianzong.com/125682.html";
//        String startKey = "登陆界面";
//        String endKey = "关注";
//        String content = "下一章";
//        String select = "a[id=pager_next]";
//        String select1 = "div[id=content]";
////        System.out.println(getDocument(url));
////        System.out.println(pagerElementsGetByContent(url, content));
//
//        try {
//            Document document = loadDateByKey(url);
//            //System.out.println(pagerGetBySelect(document, select));
//            //System.out.println(pagerGetBySelect(document, select1));
//        } catch (Exception e) {
//            log.error("11111111111111", e);
//        }
//    }
}
