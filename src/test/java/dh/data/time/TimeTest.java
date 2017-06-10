package dh.data.time;

import dh.data.util.TimeUtil;
import org.junit.Test;

import java.util.Date;

/**
 * Created by lonel on 2017/6/9.
 */
public class TimeTest {

    @Test
    public void after() {
        Date date = new Date();
        System.out.println(TimeUtil.formatDate(date.getTime(), TimeUtil.DATE_TIME_MILLIS_TYPE));
        System.out.println(TimeUtil.formatDate(date.getTime() + 200L, TimeUtil.DATE_TIME_MILLIS_TYPE));
    }


}
