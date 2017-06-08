package dh.data.handle;

import dh.data.dao.OriginDao;
import dh.data.model.Origin;
import dh.data.util.TimeUtil;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

/**
 * Created by MT-T450 on 2017/6/7.
 */
public class OriginHandler {

    @Test
    public void testGetAll() {
        List<Origin> originList = null;
        try {
            originList = OriginDao.getAll();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(originList.size());
    }

    @Test
    public void testGetList() throws IOException {
        List<Origin> list = OriginDao.getList(2, 3);
        System.out.println(list.size());
        for (Origin origin : list) {
            System.out.println(origin);
        }
    }

    @Test
    public void testGet() {
        Origin origin = null;
        try {
            origin = OriginDao.get(6);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(origin.toString());
    }

}
