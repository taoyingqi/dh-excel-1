package dh.data.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * Created by lonel on 2017/6/10.
 */
public class NumericUtil {

    public static String toStr(Float f) {
        return f == null ? "" : "" + f;
    }

    public static String toStr(Integer i) {
        return i == null ? "" : "" + i;
    }

    public static String toStr(Boolean b) {
        return b == null ? "" : "" + b;
    }

    public static Float parseFloat(String s) {
        return s == null ? null : Float.parseFloat(s);
    }

    public static Integer parseInt(String s) {
        return s == null ? null : Integer.parseInt(s);
    }

    /**
     *
     * @param f
     * @param n 保留位数
     * @return
     */
    public static String toDecimal2(Float f, int n) {
        if (f == null) {
            return "";
        }
        String str = "0";
        if (n > 0) {
            str = str.concat(".");
        }
        for (int i = 0; i < n; i++) {
            str = str.concat("0");
        }
        DecimalFormat format = new DecimalFormat(str);
        return format.format(f);
//        BigDecimal bd = new BigDecimal(f);
//        return bd.setScale(n, BigDecimal.ROUND_HALF_UP).floatValue();
    }

    public static float toDecimal(float f, int n) {
        BigDecimal bd = new BigDecimal(f);
        return bd.setScale(n, BigDecimal.ROUND_HALF_UP).floatValue();
    }
}
