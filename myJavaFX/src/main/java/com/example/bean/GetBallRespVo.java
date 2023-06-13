package com.example.bean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @description TODO
 * @auth chaijd
 * @date 2023/1/30
 */
@Getter
@Setter
@ToString
public class GetBallRespVo implements Serializable {
    private String url;

    private String historyCount;
    private List<String> recently;

    private String blueOrderByKey;
    private String blueOrderByValue;

    private String redOrderByKey;
    private String redOrderByValue;
}
