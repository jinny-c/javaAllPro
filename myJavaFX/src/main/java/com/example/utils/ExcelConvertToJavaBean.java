package com.example.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @description excel文档转javabean
 * @auth chaijd
 * @date 2023/10/12
 */
@Slf4j
public class ExcelConvertToJavaBean {
    private static String line_1 = "/**";
    private static String line_2 = " * ";
    private static String line_3 = " * ";
    private static String line_4 = "*/";
    private static String line_5_1 = "@SerializedName(\"";
    private static String line_5_2 = "\")";
    private static String line_6_1 = "private ";
    private static String line_6_3 = ";";
    private static String line_6_string = "String ";
    private static String line_6_int = "Integer ";
    private static String line_6_object = "Object ";

    /* 换行符 */
    public static final String newline_default = "\r\n";

    public static List<List<String>> convertContent(List<List<String>> fileRestList, int filedCol, int filedDesc, int filedType, int filedRemark, boolean isChange, boolean havingType) {
        filedCol -= 1;
        filedDesc -= 1;
        filedType -= 1;
        filedRemark -= 1;

        String filed, desc, type, remark;
        List<String> lineSt = null;
        List<List<String>> resutlContent = new ArrayList<>();
        for (List<String> line : fileRestList) {
            try {
                filed = removeSpacesAndNewLines(line.get(filedCol));
                desc = removeSpacesAndNewLines(line.get(filedDesc));
                type = removeSpacesAndNewLines(line.get(filedType));
                remark = removeSpacesAndNewLines(line.get(filedRemark));
                if (StringUtils.isBlank(filed)) {
                    continue;
                }
                if (containsChinese(filed)) {
                    continue;
                }
                lineSt = new ArrayList<>();
                lineSt.add(line_1);
                if (StringUtils.isBlank(desc)) {
                    desc = filed;
                }
                if (havingType) {
                    desc = desc + " " + type;
                }
                lineSt.add(line_2 + desc);

                if (StringUtils.isNotBlank(remark)) {
                    lineSt.add(line_3 + remark);
                }
                lineSt.add(line_4);
                String line_6_2;
                if (StringUtils.containsIgnoreCase(type, "string")) {
                    line_6_2 = line_6_string;
                } else if (StringUtils.containsIgnoreCase(type, "object")) {
                    line_6_2 = line_6_object;
                } else if (StringUtils.containsIgnoreCase(type, "int") ||
                        StringUtils.containsIgnoreCase(type, "number") ||
                        StringUtils.containsIgnoreCase(type, "amount")) {
                    line_6_2 = line_6_int;
                } else {
                    line_6_2 = line_6_string;
                }
                if (isChange) {
                    lineSt.add(line_5_1 + filed + line_5_2);
                    lineSt.add(line_6_1 + line_6_2 + underscoreToCamelCase(filed) + line_6_3);
                } else {
                    lineSt.add(line_6_1 + line_6_2 + filed + line_6_3);
                }
                resutlContent.add(lineSt);
            } catch (Exception e) {
            }
        }
        return resutlContent;
    }


    public static String removeSpacesAndNewLines(String s) {
        if (s == null) {
            return null;
        }
        s = s.trim();
        // 去除空格、换行、制表符、回车符
        String result = s.replaceAll("[\\s\\n\\r]", "");
        // 去除 &nbsp;
        result = result.replace("&nbsp;", "");
        result = result.replace("\u00A0", "");

        return result;
    }

    public static String underscoreToCamelCase(String s) {
        if (s == null) {
            return null;
        }
        s = s.trim();
        StringBuilder camelCaseString = new StringBuilder();
        String[] parts = s.split("_");

        for (int i = 0; i < parts.length; i++) {
            String word = parts[i];

            if (i == 0) { // 第一个单词首字母小写
                camelCaseString.append(word.toLowerCase());
            } else { // 其他单词首字母大写
                camelCaseString.append(word.substring(0, 1).toUpperCase());
                camelCaseString.append(word.substring(1).toLowerCase());
            }
        }

        return camelCaseString.toString();
    }

    private static Pattern chinese = Pattern.compile("[\u4e00-\u9fa5]");

    public static boolean containsChinese(String str) {
        if (str == null || "".equals(str.trim())) {
            return false;
        }
        Matcher m = chinese.matcher(str);
        return m.find();
    }

    private static String NEED_LINE_1 = "| 字段名         | 中文名称       | 是否必填 | 类型       | 说明                          |";
    private static String NEED_LINE_2 = "| ------------- | -------------- | --------| ---------- | ---------------------------- |";
    private static String NEED_LINE_START = "| ";
    private static String NEED_LINE_MID = "  |  ";
    private static String NEED_LINE_END = "  |";

    public static List<String> convertContent(List<List<String>> fileRestList, ListIterator<Integer> iterator, boolean isChange) {
        int filed1 = iterator.next();
        int filed2 = iterator.next();
        int filed3 = iterator.next();
        int filed4 = iterator.next();
        int filed5 = iterator.next();
        filed1 -= 1;
        filed2 -= 1;
        filed3 -= 1;
        filed4 -= 1;
        filed5 -= 1;

        String filed, name, mustOr, type, desc;
        List<String> resutlContent = new ArrayList<>();
        resutlContent.add(NEED_LINE_1);
        resutlContent.add(NEED_LINE_2);
        StringBuilder sbt = null;

        for (List<String> line : fileRestList) {
            try {
                filed = removeSpacesAndNewLines(line.get(filed1));
                name = removeSpacesAndNewLines(line.get(filed2));
                mustOr = removeSpacesAndNewLines(line.get(filed3));
                type = removeSpacesAndNewLines(line.get(filed4));
                desc = removeSpacesAndNewLines(line.get(filed5));

                if (StringUtils.isBlank(filed)) {
                    continue;
                }
                if (containsChinese(filed)) {
                    continue;
                }

                sbt = new StringBuilder();
                sbt.append(NEED_LINE_START).append(isChange ? camelToSnake(filed) : snakeToCamel(filed)).append(NEED_LINE_MID);
                sbt.append(name).append(NEED_LINE_MID);
                sbt.append(mustOr).append(NEED_LINE_MID);
                sbt.append(type).append(NEED_LINE_MID);
                sbt.append(desc).append(NEED_LINE_END);

                resutlContent.add(sbt.toString());
            } catch (Exception e) {
            }
        }
        return resutlContent;
    }

    /**
     * 蛇形转驼峰
     * my_variable_name --》》myVariableName
     *
     * @param snakeCase
     * @return
     */
    public static String snakeToCamel(String snakeCase) {
        StringBuilder camelCase = new StringBuilder();
        boolean toUpperCase = false;

        for (char c : snakeCase.toCharArray()) {
            if (c == '_') {
                toUpperCase = true;
            } else {
                camelCase.append(toUpperCase ? Character.toUpperCase(c) : c);
                toUpperCase = false;
            }
        }

        return camelCase.toString();
    }

    /**
     * 驼峰转蛇形
     * myVariableName --》》my_variable_name
     *
     * @param camelCase
     * @return
     */
    public static String camelToSnake(String camelCase) {
        return camelCase.replaceAll("([a-z])([A-Z])", "$1_$2").toLowerCase();
    }

    public static void main(String[] args) {
        List<List<String>> resutlContent = null;
        String localPath = "C:\\Users\\jidd\\Desktop\\111111.xlsx";
        try (InputStream in = new FileInputStream(localPath)) {
            try (XSSFWorkbook xssfWorkbook = new XSSFWorkbook(in)) {
                List<List<String>> fileRestList = ExcelUtils.oneSheetRead2007Excel(xssfWorkbook, 1, 3, 7);
                resutlContent = convertContent(fileRestList, 2, 3, 4, 7, false, false);
            }

            for (List<String> line : resutlContent) {
                System.out.println(StringUtils.join(line, newline_default));
            }
        } catch (Exception e) {
//            log.error("Exception", e);
        }
    }
}
