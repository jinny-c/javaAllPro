package com.example.bean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @description TODO
 * @auth chaijd
 * @date 2023/1/30
 */
@Getter
@Setter
@ToString
public class GetBallReqVo implements Serializable {
    private String url;
    private String start;
    private String end;
    private String defaultNumber;
    private String listHide;
    private String blueSize;
    private String redSize;
}
