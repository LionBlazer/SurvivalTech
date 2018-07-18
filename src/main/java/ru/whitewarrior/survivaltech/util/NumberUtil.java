package ru.whitewarrior.survivaltech.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Date: 2017-12-29.
 * Time: 21:20:42.
 *
 * @author WhiteWarrior
 */
public class NumberUtil {

    public static int toTick(float sec) {
        return (int) (sec * 20);
    }

    public static int toK(float unit) {
        return (int) (unit * 1000);
    }

    public static int toM(float unit) {
        return (int) (unit * 1000000);
    }

    public static double rounding(double number) {
        return new BigDecimal(number).setScale(2, RoundingMode.HALF_DOWN).doubleValue();
    }
}
