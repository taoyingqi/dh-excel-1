package dh.data.service;

import dh.data.dao.MidDao;
import dh.data.dao.OriginDao;
import dh.data.model.Mid;
import dh.data.model.Origin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by MT-T450 on 2017/6/8.
 */
public class MidService {

    public void calc() throws IOException {
        List<Origin> originList = OriginDao.getAll();
        List<Mid> midList = new ArrayList<>();
        for (Origin origin : originList) {
            Mid mid = new Mid();
            mid.setFlightId(origin.getFlightId());
            mid.setWxdFh(new Mid.FH());
        }
    }


}
