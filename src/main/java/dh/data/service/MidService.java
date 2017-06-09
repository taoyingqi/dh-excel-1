package dh.data.service;

import dh.data.dao.MidDao;
import dh.data.dao.OriginDao;
import dh.data.model.Mid;
import dh.data.model.Origin;
import dh.data.model.Sample;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by MT-T450 on 2017/6/8.
 */
public class MidService {

    public static void calc() throws IOException {
        List<Origin> originList = OriginDao.getAll();
        List<Mid> midList = new ArrayList<>();
        for (int i = 0; i < originList.size(); i++) {
            Origin origin = originList.get(i);
            Mid mid = new Mid();
            mid.setFlightId(origin.getFlightId());
            if (i < 2) {
                mid.setWxdFh(new Mid.FH(new Date(origin.getTime().getTime() + i % 4 * 250), origin.getWxd(),
                        new Sample(),
                        new Sample()));
            } else if (i < 10) {
                mid.setWxdFh(new Mid.FH(new Date(origin.getTime().getTime() + i % 4 * 250), origin.getWxd(),
                        new Sample(
                                new Date(origin.getTime().getTime() + i % 4 * 250 - 500),
                                new Date(origin.getTime().getTime() + i % 4 * 250),
                                (origin.getWxd() - originList.get(i - 2).getWxd()) * 120,
                                null
                        ),
                        new Sample(
                        )));
            } else {
                mid.setWxdFh(new Mid.FH(new Date(origin.getTime().getTime() + i % 4 * 250), origin.getWxd(),
                        new Sample(
                                new Date(origin.getTime().getTime() + i % 4 * 250 - 500),
                                new Date(origin.getTime().getTime() + i % 4 * 250),
                                (origin.getWxd() - originList.get(i - 2).getWxd()) * 120,
                                null
                        ),
                        new Sample(
                                new Date(origin.getTime().getTime() + i % 4 * 250 - 2000),
                                new Date(origin.getTime().getTime() + i % 4 * 250),
                                null,
                                null
                        )));
            }
            midList.add(mid);
        }
        MidDao.saveList(midList);
    }


}
