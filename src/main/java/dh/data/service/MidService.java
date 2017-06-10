package dh.data.service;

import dh.data.dao.MidDao;
import dh.data.dao.OriginDao;
import dh.data.model.Mid;
import dh.data.model.Origin;
import dh.data.model.Sample;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by MT-T450 on 2017/6/8.
 */
public class MidService {
    private static final Logger LOG = LoggerFactory.getLogger(MidService.class);

    public static void calc() throws IOException {
        List<Origin> originList = OriginDao.getAll();
        List<Mid> midList = new ArrayList<>();
        for (int i = 0; i < originList.size(); i++) {
            Origin origin = originList.get(i);
            Mid mid = new Mid();
            mid.setFlightId(origin.getFlightId());
            // Wxd
            Mid.FH wxdFH = new Mid.FH();
            wxdFH.setTime(new Date(origin.getTime().getTime() + i % 4 * 250));
            wxdFH.setHeight(origin.getWxd());

            if (i > 1) {
                wxdFH.setSample1(new Sample(
                        originList.get(i - 2).getTime(),
                        wxdFH.getTime(),
                        (origin.getWxd() - originList.get(i - 2).getWxd()) * 120,
                        null));
            }
            if (i > 9) {
                Integer sumSample1DownRate = wxdFH.getSample1().getDownRate();
                for (int j = i - 8; j < i; j++) {
                    sumSample1DownRate += midList.get(j).getWxdFh().getSample1().getDownRate();
                }
                wxdFH.setSample2(new Sample(
                        midList.get(i - 8).getWxdFh().getSample1().getEndTime(),
                        wxdFH.getTime(),
                        sumSample1DownRate / 9,
                        null
                ));
            }
            // 设置 Wxd
            mid.setWxdFh(wxdFH);

            // Qnh
            Mid.FH qnhFh = new Mid.FH();
            qnhFh.setTime(new Date(origin.getTime().getTime() + i % 4 * 250));
            if (i % 4 == 0) {
                qnhFh.setHeight(origin.getQnh());
                if (i > 3) {
                    qnhFh.setSample1(new Sample(
                            midList.get(i - 4).getQnhFh().getTime(),
                            qnhFh.getTime(),
                            (qnhFh.getHeight() - midList.get(i - 4).getQnhFh().getHeight()) * 60,
                            null
                    ));
                }
                if (i > 11) {
                    qnhFh.setSample2(new Sample(
                            midList.get(i - 8).getQnhFh().getTime(),
                            qnhFh.getTime(),
                            (qnhFh.getSample1().getDownRate()
                                    + midList.get(i - 4).getQnhFh().getSample1().getDownRate()
                                    + midList.get(i - 8).getQnhFh().getSample1().getDownRate()) / 3,
                            null
                    ));
                }
            }
            // 设置 Qnh
            mid.setQnhFh(qnhFh);
            midList.add(mid);
        }
        LOG.info("[保存]");
        MidDao.saveList(midList);
    }


}
