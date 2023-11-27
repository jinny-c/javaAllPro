package com.example.service.bean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.*;

/**
 * @description TODO
 * @auth chaijd
 * @date 2023/6/21
 */
@Getter
@Setter
@ToString
public class BallEnty implements Serializable {
    Integer blue;
    List<Integer> red;

    public boolean equalsAny(Object o) {
        if (this == o) return false;
        if (o == null || getClass() != o.getClass()) return false;
        BallEnty ballEnty = (BallEnty) o;
        if (Objects.equals(blue, ballEnty.blue)) return true;
        return equalsAnyList(red, ballEnty.red);
    }

    private boolean equalsAnyList(List<Integer> red1, List<Integer> red2) {
        if (null == red1 || red1.isEmpty() || null == red2 || red2.isEmpty()) {
            return false;
        }
        List<Integer> tmpRed1 = new ArrayList<>(red1);
        List<Integer> tmpRed2 = new ArrayList<>(red2);
        Optional<Integer> rst = tmpRed1.stream().filter(tmpRed2::contains).findAny();
        return rst.isPresent();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BallEnty ballEnty = (BallEnty) o;
        return Objects.equals(blue, ballEnty.blue) && equalsList(red, ballEnty.red);
    }

    @Override
    public int hashCode() {
        return Objects.hash(blue, red);
    }

    private boolean equalsList(List<Integer> red1, List<Integer> red2) {
        if (null == red1 || red1.isEmpty() || null == red2 || red2.isEmpty()) {
            return false;
        }
        List<Integer> tmpRed1 = new ArrayList<>(red1);
        List<Integer> tmpRed2 = new ArrayList<>(red2);
        Collections.sort(tmpRed1);
        Collections.sort(tmpRed2);
        return tmpRed1.equals(tmpRed2);
//            red1.forEach(e->{
//                red2.forEach(r->{
//                    if(!e.equals(r)){
//                        return;
//                    }
//                });
//            });
//            return false;
    }
}
