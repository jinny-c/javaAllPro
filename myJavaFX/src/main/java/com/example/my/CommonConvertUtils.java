package com.example.my;

import com.google.common.base.Splitter;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import org.apache.commons.lang3.RegExUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @description 通用方法
 * @auth chaijd
 * @date 2023/11/6
 */
public class CommonConvertUtils {

    /* 换行 分隔符   */
    public static final String  line_feed_pattern =  "\r\n|\n|\r";
    public static final String pattern =  Pattern.compile(line_feed_pattern).pattern();

    public static int getSameCount(String text) {
        try {
            //return Integer.parseInt(textField.getText());
            return Integer.parseInt(text);
        } catch (Exception e) {
        }
        return 0;
    }

    public static List<Integer> convertList(String str) {
        if (StringUtils.isBlank(str)) {
            return null;
        }
        //str.replaceAll(pattern,"");
        str = RegExUtils.replaceAll(str, pattern, "");
        List<Integer> lt = null;
        try {
            String[] arr = str.split("-");
            if (arr.length > 0) {
                lt = new ArrayList<>();
                for (String st : arr) {
                    lt.add(Integer.parseInt(st.trim()));
                }
            }
        } catch (Exception e) {
        }
        return lt;
    }

    public static Map<Integer, Double> convertMap(String str, Integer length) {
        if (StringUtils.isBlank(str)) {
            return null;
        }
        str = RegExUtils.replaceAll(str, pattern, "");
        try {
            if (StringUtils.startsWith(str, "{")) {
                str = StringUtils.substringAfter(str, "{");
            }
            if (StringUtils.endsWith(str, "}")) {
                str = StringUtils.substringBefore(str, "}");
            }

            Splitter.MapSplitter smsp = Splitter.on(",").withKeyValueSeparator("=");
            Map<String, String> smspMp = smsp.split(str);
            // 使用Stream和Lambda表达式进行类型转换
            Map<Integer, Double> mp = smspMp.entrySet()
                    .stream()
                    .collect(Collectors.toMap(
                            entry -> Integer.parseInt(entry.getKey().trim()),
                            entry -> Double.parseDouble(entry.getValue().trim()) / length
                    ));

            return mp;
        } catch (Exception e) {
        }
        return null;
    }

    public static List<Integer> convertList(HBox hBox) {
        List<Integer> result = new ArrayList<>();
        for (Node child : hBox.getChildren()) {
            if (child instanceof TextField) {
                TextField textField = (TextField) child;
                result.add(Integer.parseInt(textField.getText()));
            }
        }
        return result;
    }
    public static List<String> convertListValues(Pane pane) {
        List<String> result = new ArrayList<>();
        for (Node child : pane.getChildren()) {
            if (child instanceof TextInputControl) {
                TextInputControl field = (TextInputControl) child;
                result.add(field.getText());
            }
        }
        return result;
    }

    public static <T> List<T> convertSubNode(Pane pane, Class<T> clazz) {
        List<T> result = new ArrayList<>();
        for (Node child : pane.getChildren()) {
            if (clazz.isInstance(child)) {
                result.add((T) child);
            }
        }
        return result;
    }

    public static String convertPaneValue(Pane pane, int index) {
        List<String> result = convertListValues(pane);
        if (result.size() < index) {
            return null;
        }
        return result.get(index - 1);
    }

    public static boolean convertSelect(Pane pane) {
        for (Node child : pane.getChildren()) {
            if (child instanceof ToggleButton) {
                ToggleButton toggleButton = (ToggleButton) child;
                return toggleButton.isSelected();
            }
        }
        return false;
    }

}
