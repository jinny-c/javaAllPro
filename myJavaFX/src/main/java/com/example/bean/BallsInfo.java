package com.example.bean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @description TODO
 * @auth chaijd
 * @date 2023/6/15
 */
@Getter
@Setter
@ToString
public class BallsInfo {
    private String ballDate;
    private List<String> redBalls;
    private String blueBall;
}
