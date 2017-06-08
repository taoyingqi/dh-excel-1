package dh.data.handle;

import dh.data.dao.MidDao;
import dh.data.model.Mid;
import dh.data.model.Sample;
import org.junit.Test;

import java.util.Date;

/**
 * Created by MT-T450 on 2017/6/8.
 */
public class MidHandler {

    @Test
    public void save() {
        Mid mid = new Mid();
        mid.setFlightId(14861);
        mid.setWxdFh(new Mid.FH(new Date(), 2343
                , new Sample(new Date(), new Date(), 32123, null)
                , new Sample(new Date(), new Date(), 43532, null)));
        mid.setQnhFh(new Mid.FH(new Date(), 2343
                , new Sample(new Date(), new Date(), 32123, null)
                , new Sample(new Date(), new Date(), 43532, null)));
        mid.setHeightFh(new Mid.FH(new Date(), 2343
                , new Sample(new Date(), new Date(), 32123, null)
                , new Sample(new Date(), new Date(), 43532, null)));
        mid.setWxdCond(true);
        mid.setQnhCond(false);
        mid.setHeightCond(true);
        mid.setMultiCond(true);
        mid.setDurationSec(23000);
        MidDao.save(mid);
    }

    @Test
    public void get() {
    }

    @Test
    public void getAll() {
    }

}
