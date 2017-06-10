package dh.data.time;

import dh.data.util.NumericUtil;
import org.junit.Test;

import java.text.DecimalFormat;

/**
 * Created by lonel on 2017/6/10.
 */
public class NumericTest {


    @Test
    public void testFloat() {
        System.out.println(Float.parseFloat(null));
    }

    @Test
    public void testRound() {
        System.out.println(NumericUtil.toDecimal(12.6676f , 2));
    }

    @Test
    public void testDecimal() {
        String str = "";
        for (int i = 0; i < 4; i++) {
            str = str.concat("0");
        }
        System.out.println(str);
        DecimalFormat format = new DecimalFormat(".000000");
        System.out.println(format.format(23.4489f));
    }

}
