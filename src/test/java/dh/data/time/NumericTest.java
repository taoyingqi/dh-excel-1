package dh.data.time;

import dh.data.util.MidUtil;
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
        System.out.println(NumericUtil.toDecimal(MidUtil.devWxdFactor(7386), 0));
    }

}
