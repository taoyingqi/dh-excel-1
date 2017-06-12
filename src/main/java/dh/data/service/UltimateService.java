package dh.data.service;

import dh.data.config.IConst;
import dh.data.dao.MidDao;
import dh.data.dao.OriginDao;
import dh.data.dao.OutcomeDao;
import dh.data.dao.UltimateDao;
import dh.data.model.*;
import dh.data.util.TimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;

/**
 * Created by lonel on 2017/6/10.
 */
public class UltimateService {
    private final static Logger LOG = LoggerFactory.getLogger(UltimateDao.class);

    public static void calc() throws IOException {
//        List<Origin> originList = OriginDao.getAll();
        List<Outcome> outcomeList = OutcomeDao.getAll();
        List<Mid> midList = MidDao.getAll();
        List<Ultimate> ultimateList = new ArrayList<>();
        Set<Integer> flightIdSet = new LinkedHashSet<>();
        for (int i = 0; i < midList.size(); i++) {
            flightIdSet.add(midList.get(i).getFlightId());
        }

        for (Integer flightId : flightIdSet) {
            Ultimate ultimate = new Ultimate();
            ultimate.setFlightId(flightId);
            // 下穿标志
            boolean gt500Flag = false, gt0Flag = false;
            for (int j = 1, fj = 0; j < outcomeList.size(); j++) {
                Outcome outcome = outcomeList.get(j);
                if (j > 0) {
                    if (outcome.getFlightId().equals(outcomeList.get(j - 1).getFlightId())) {
                        fj++;
                    } else {
                        fj = 0;
                    }
                }
                if (outcome.getFlightId().equals(flightId)) {
                    if (outcome.getRaltc() > 500 * IConst.RALTC_FACTOR && !gt500Flag) {
                        gt500Flag = true;
                    }
                    if (outcome.getRaltc() < 500 * IConst.RALTC_FACTOR && gt500Flag) {
                        // 设置下穿500英尺次数
                        if (ultimate.getDown500n() == null) {
                            ultimate.setDown500n(1);
                        } else {
                            ultimate.setDown500n(ultimate.getDown500n() + 1);
                        }
                        // 设置最后一次下穿500英尺时刻
                        ultimate.setLast1Down500Time(new Date(outcome.getTime().getTime() + fj % 4 * 250));
                        // 恢复下穿500英尺-标志
                        gt500Flag = false;
                    }
                    if (outcome.getRaltc() > 0 && !gt0Flag) {
                        gt0Flag = true;
                    }
                    if (outcome.getRaltc() < 0 && gt0Flag) {
                        // 设置下穿0英尺次数
                        if (ultimate.getDown0n() == null) {
                            ultimate.setDown0n(1);
                        } else {
                            ultimate.setDown0n(ultimate.getDown0n() + 1);
                        }
                        // 设置首次下穿0英尺时刻
                        if (ultimate.getFirst1Down0Time() == null) {
                            ultimate.setFirst1Down0Time(new Date(outcome.getTime().getTime() +  + fj % 4 * 250));
                        }
                        // 恢复下穿0英尺-标志
                        gt0Flag = false;
                    }
                }
            }
            // 设置持续时间
            if (ultimate.getFirst1Down0Time() != null && ultimate.getLast1Down500Time() != null) {
                ultimate.setDurationTime(new Date(ultimate.getFirst1Down0Time().getTime() - ultimate.getLast1Down500Time().getTime()));
            } else {
                LOG.warn("[航班{}，数据不全。{}]", flightId, ultimate);
                continue;
            }
            // 三个高度的最大下降率
            int wxdi = 0, qnhi = 0, heighti = 0;
            int wxdDownRate = Math.abs(midList.get(0).getWxdFh().getSample2().getDownRate())
                    , qnhDownRate = Math.abs(midList.get(0).getQnhFh().getSample2().getDownRate())
                    , heightDownRate = Math.abs(midList.get(0).getHeightFh().getSample2().getDownRate());
            for (int j = 1; j < midList.size(); j++) {
                Mid mid = midList.get(j);
                if (mid.getFlightId().equals(flightId)) {
                    if (wxdDownRate < Math.abs(mid.getWxdFh().getSample2().getDownRate())) {
                        wxdi = j;
                        wxdDownRate = Math.abs(mid.getWxdFh().getSample2().getDownRate());
                    }
                    if (qnhDownRate < Math.abs(mid.getQnhFh().getSample2().getDownRate())) {
                        qnhi = j;
                        qnhDownRate = Math.abs(mid.getQnhFh().getSample2().getDownRate());
                    }
                    if (heightDownRate < Math.abs(mid.getHeightFh().getSample2().getDownRate())) {
                        heighti = j;
                        heightDownRate = Math.abs(mid.getHeightFh().getSample2().getDownRate());
                    }
                }
            }
            ultimate.setWxdMdc(midList.get(wxdi).getWxdFh().getSample2());
            ultimate.setQnhMdc(midList.get(qnhi).getQnhFh().getSample2());
            ultimate.setHeightMdc(midList.get(heighti).getHeightFh().getSample2());
            // 下降率超过500英尺次数
            Sample downRateGt500LdSample = new Sample();
            for (int j = 0; j < midList.size(); j++) {
                Mid mid = midList.get(j);
                if (mid.getFlightId().equals(flightId)) {
                    if (mid.getDurationSec().equals(1)) {
                        downRateGt500LdSample.setStartTime(mid.getHeightFh().getTime());
                    }
                    if (mid.getDurationSec() > 1) {
                        if (ultimate.getDownRateGt500n() == null) {
                            ultimate.setDownRateGt500n(1);
                        } else {
                            ultimate.setDownRateGt500n(ultimate.getDownRateGt500n() + 1);
                        }
                        downRateGt500LdSample.setEndTime(mid.getHeightFh().getTime());
                        downRateGt500LdSample.setDurationSec(mid.getDurationSec());
                        //
                        if (ultimate.getDownRateGt500Ld() == null) {
                            ultimate.setDownRateGt500Ld(new Sample(
                                    downRateGt500LdSample.getStartTime(),
                                    new Date(downRateGt500LdSample.getEndTime().getTime() + 250),
                                    null,
                                    downRateGt500LdSample.getDurationSec()
                            ));
                        } else {
                            if (ultimate.getDownRateGt500Ld().getDurationSec() < downRateGt500LdSample.getDurationSec()) {
                                ultimate.setDownRateGt500Ld(new Sample(
                                        downRateGt500LdSample.getStartTime(),
                                        new Date(downRateGt500LdSample.getEndTime().getTime() + 250),
                                        null,
                                        downRateGt500LdSample.getDurationSec()
                                ));
                            }
                        }
                    }
                }
            }
            ultimateList.add(ultimate);
        }
        LOG.info("[保存]");
        UltimateDao.saveList(ultimateList);
    }


}
