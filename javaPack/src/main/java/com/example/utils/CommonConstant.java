package com.example.utils;

import java.util.regex.Pattern;

/**
 * @description TODO
 * @auth chaijd
 * @date 2023/6/15
 */
public interface CommonConstant {
    String filed_url = "url";
    String filed_start = "start";
    String filed_end = "end";

    String line_feed = "\n\r";
    /**
     * 换行符
     */
    String content_pattern = "\r\n|\n|\r";
    /**
     * 换行符 split用
     */
    String line_feed_pattern = Pattern.compile(content_pattern).pattern();
}
