package com.example.utils;

import lombok.Getter;

/**
 * @description TODO
 * @auth chaijd
 * @date 2023/8/9
 */
@Getter
public enum PageContentSelectEnums {
    /**
     *
     */
    select_content("content", "01"),
    select_html("html", "02"),
    select_head("head", "03"),
    select_body("body", "04"),


    select_unknown("unknown", "-1"),
    ;
    private final String selectMethod;

    private final String selectType;

    PageContentSelectEnums(String selectMethod, String selectType) {
        this.selectMethod = selectMethod;
        this.selectType = selectType;
    }

    public static PageContentSelectEnums convertByType(String selectType) {
        if (selectType == null || selectType.trim().length() == 0) {
            return select_unknown;
        }
        for (PageContentSelectEnums enums : values()) {
            if (enums.selectType.equals(selectType)) {
                return enums;
            }
        }
        return select_unknown;
    }
}
