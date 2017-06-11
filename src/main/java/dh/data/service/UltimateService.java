package dh.data.service;

import dh.data.dao.MidDao;
import dh.data.dao.OriginDao;
import dh.data.dao.OutcomeDao;
import dh.data.dao.UltimateDao;
import dh.data.model.Mid;
import dh.data.model.Origin;
import dh.data.model.Outcome;
import dh.data.model.Ultimate;
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
            ultimateList.add(ultimate);
        }

        UltimateDao.saveList(ultimateList);
    }


}
