package dh.data.handle;

import dh.data.dao.UltimateDao;
import dh.data.model.Sample;
import dh.data.model.Ultimate;
import dh.data.service.UltimateService;
import dh.data.util.TimeUtil;
import org.apache.poi.ss.usermodel.DateUtil;
import org.junit.Test;

import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * Created by MT-T450 on 2017/6/8.
 */
public class UltimateHandler {

    @Test
    public void save() {
        Ultimate ultimate = new Ultimate();
        ultimate.setFlightId(14861);
        ultimate.setDown500n(3);
        ultimate.setLast1Down500Time(new Date());
        ultimate.setDown0n(1);
        ultimate.setFirst1Down0Time(new Date());
        ultimate.setDurationTime(new Date());
        ultimate.setWxdMdc(new Sample(
                new Date(),
                new Date(),
                3,
                null
        ));
        ultimate.setQnhMdc(new Sample(
                new Date(),
                new Date(),
                4,
                null
        ));
        ultimate.setHeightMdc(new Sample(
                new Date(),
                new Date(),
                -390,
                null
        ));
        ultimate.setDownRateGt500n(5);
        ultimate.setDownRateGt500Ld(new Sample(
                new Date(),
                new Date(),
                null,
                32000
        ));
        UltimateDao.save(ultimate);
    }

    @Test
    public void calc() throws IOException {
        UltimateDao.clear();
        UltimateService.calc();
    }

    @Test
    public void get() {
        Ultimate ultimate = UltimateDao.get(7);
        System.out.println(ultimate);
        System.out.println(TimeUtil.formatDate(ultimate.getLast1Down500Time().getTime(), "HH:mm:ss:SSS"));
    }

    @Test
    public void getAll() {
        List<Ultimate> list = UltimateDao.getAll();
        System.out.println(list.size());
    }

}
