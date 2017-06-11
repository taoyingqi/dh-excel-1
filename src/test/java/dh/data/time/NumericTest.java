package dh.data.time;

import dh.data.util.MidUtil;
import dh.data.util.NumericUtil;
import org.junit.Test;

import java.math.BigDecimal;

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
        System.out.println(NumericUtil.toDecimal2(12.6676f , 2));
    }

    @Test
    public void testDecimal() {
        System.out.println(NumericUtil.toDecimal2(MidUtil.devWxdFactor(7386), 0));
    }

    @Test
    public void testBigDecimal() {
        float f = 123.7855f;
        BigDecimal bd = new BigDecimal(f);
        f = bd.setScale(-1, BigDecimal.ROUND_HALF_UP).floatValue();
        System.out.println(f);
        System.out.println(1f);
    }

    @Test
    public void testParseInt() {
        System.out.println((int)Double.parseDouble("3348.0"));
    }

}
