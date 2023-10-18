package com.example.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
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
