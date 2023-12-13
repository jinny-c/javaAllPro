package com.example.service.bean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author chaijd
 * @description 结果类
 * @date 2023-12-12
 */
@Getter
@Setter
@ToString
public class LotteryResultDto implements Serializable {
    private BallEnty ballEnty;
    private Boolean isInList;
    private String defType;
    private Integer count;
    private Boolean isOrNotAllSame;

    public List<String> convertShow() {
        List<String> rstList = new ArrayList<>();
        rstList.add(StringUtils.join("count=", count, ",defType=", defType,
                ",isInList=", isInList, ",isOrNotAllSame=,", isOrNotAllSame));
        String entyStr = ballEnty.toString();
        Collections.sort(ballEnty.getRed());
        String redStr = ballEnty.getRed().toString();
        rstList.add(entyStr + redStr);
        return rstList;
    }
}
