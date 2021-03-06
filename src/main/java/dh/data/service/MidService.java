package dh.data.service;

import dh.data.config.IConst;
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
        // fi 同一 flightId 序号
        for (int i = 0, fi = 0; i < originList.size(); i++) {
            Origin origin = originList.get(i);
            Mid mid = new Mid();
            mid.setFlightId(origin.getFlightId());
            if (i > 0) {
                if (originList.get(i).getFlightId().equals(originList.get(i - 1).getFlightId())) {
                    fi++;
                } else {
                    fi = 0;
                }
            }
            // Wxd
            Mid.FH wxdFH = new Mid.FH();
            wxdFH.setTime(new Date(origin.getTime().getTime() + fi % 4 * 250));
            wxdFH.setHeight(origin.getWxd());
            // 1.计算无线电高度每0.5秒下降率：从第3个高度开始，用当下的高度减去后移2个单位的高度，乘以120
            if (fi > 1) {
                wxdFH.setSample1(new Sample(
                        midList.get(i - 2).getWxdFh().getTime(),
                        wxdFH.getTime(),
                        (origin.getWxd() - originList.get(i - 2).getWxd()) * 120,
                        null));
            }
            // 2.计算无线电高度的每2秒平均下降率：从第11个高度开始，计算前9个无线电高度下降率的平均值
            if (fi > 9) {
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
            qnhFh.setTime(new Date(origin.getTime().getTime() + fi % 4 * 250));
            if (fi % 4 == 0) {
                qnhFh.setHeight(origin.getQnh());
                // 4.计算气压/Height高度每秒下降率：从第2个整秒高度开始，用当下的高度减去后移1个单位的高度，乘以60
                if (fi > 3) {
                    qnhFh.setSample1(new Sample(
                            midList.get(i - 4).getQnhFh().getTime(),
                            qnhFh.getTime(),
                            (qnhFh.getHeight() - midList.get(i - 4).getQnhFh().getHeight()) * 60,
                            null
                    ));
                }
                // 5.计算气压/Height高度每2秒平均下降率：从第4个整秒高度开始，计算前3个下降率的平均值
                if (fi > 11) {
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
            // Height
            Mid.FH heightFH = new Mid.FH();
            heightFH.setTime(new Date(origin.getTime().getTime() + fi % 4 * 250));
            if (fi % 4 == 0) {
                heightFH.setHeight(origin.getHeight());
                // 4.计算气压/Height高度每秒下降率：从第2个整秒高度开始，用当下的高度减去后移1个单位的高度，乘以60
                if (fi > 3) {
                    heightFH.setSample1(new Sample(
                            midList.get(i - 4).getHeightFh().getTime(),
                            heightFH.getTime(),
                            (heightFH.getHeight() - midList.get(i - 4).getHeightFh().getHeight()) * 60,
                            null
                    ));
                }
                // 5.计算气压/Height高度每2秒平均下降率：从第4个整秒高度开始，计算前3个下降率的平均值
                if (fi > 11) {
                    heightFH.setSample2(new Sample(
                            midList.get(i - 8).getHeightFh().getTime(),
                            heightFH.getTime(),
                            (heightFH.getSample1().getDownRate()
                                    + midList.get(i - 4).getHeightFh().getSample1().getDownRate()
                                    + midList.get(i - 8).getHeightFh().getSample1().getDownRate()) / 3,
                            null
                    ));
                }
            }
            // 设置 Height
            mid.setHeightFh(heightFH);

            if ((fi - 3) % 4 == 0) {
                //秒内四个时刻的平均下降率超过500
                if (wxdFH.getSample2().getDownRate() != null && wxdFH.getSample2().getDownRate() < -500 * IConst.WXD_FACTOR
                        && midList.get(i - 1).getWxdFh().getSample2().getDownRate() != null && midList.get(i - 1).getWxdFh().getSample2().getDownRate() < -500 * IConst.WXD_FACTOR
                        && midList.get(i - 2).getWxdFh().getSample2().getDownRate() != null && midList.get(i - 2).getWxdFh().getSample2().getDownRate() < -500 * IConst.WXD_FACTOR
                        && midList.get(i - 3).getWxdFh().getSample2().getDownRate() != null && midList.get(i - 3).getWxdFh().getSample2().getDownRate() < -500 * IConst.WXD_FACTOR
                        ) {
                    midList.get(i - 3).setWxdCond(true);
                } else {
                    midList.get(i - 3).setWxdCond(false);
                }
                int n = 0;
                if (midList.get(i - 3).getWxdCond()) n++;
                if (midList.get(i - 3).getQnhCond()) n++;
                if (midList.get(i - 3).getHeightCond()) n++;
                midList.get(i - 3).setMultiCond(n > 1); // 至少两个平均下降率均超过500英尺
            }
            if (fi % 4 == 0) {
                mid.setQnhCond(qnhFh.getSample2().getDownRate() != null && qnhFh.getSample2().getDownRate() < -500);
                mid.setHeightCond(heightFH.getSample2().getDownRate() != null && heightFH.getSample2().getDownRate() < -500);
            }
            midList.add(mid);
        }
        int start = 0, end = 0;
        for (int i = 0, fi = 0; i < midList.size(); i++) {
            if (i > 0) {
                if (midList.get(i).getFlightId().equals(midList.get(i - 1).getFlightId())) {
                    fi++;
                } else {
                    fi = 0;
                }
            }
            if (fi % 4 == 0) {
                Mid mid = midList.get(i);
                if (mid.getMultiCond() != null && mid.getMultiCond()) {
                    if (start == 0L) {
                        start = i;
                    }
                }
                if (mid.getMultiCond() != null && !mid.getMultiCond()) {
                    end = i;
                }
                if (start != 0 && end != 0 && start < end) {
                    // 计算持续时间
                    midList.get(start).setDurationSec(1);
                    midList.get(end -1).setDurationSec((int) (midList.get(end - 1).getHeightFh().getTime().getTime() - midList.get(start).getHeightFh().getTime().getTime() + 250));
                    start = 0; end = 0;
                }
            }
        }
        LOG.info("[保存]");
        MidDao.saveList(midList);
    }


}
