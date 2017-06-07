package dh.data.handle;

import dh.data.dao.OriginDao;
import dh.data.model.Origin;
import org.junit.Test;

import java.util.List;

/**
 * Created by MT-T450 on 2017/6/7.
 */
public class OriginHandler {

    @Test
    public void testGetAll() {
        List<Origin> originList = OriginDao.getAll();
        System.out.println(originList.size());
    }

}
