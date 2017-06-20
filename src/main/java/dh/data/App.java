package dh.data;

import dh.data.dao.MidDao;
import dh.data.dao.UltimateDao;
import dh.data.service.MidService;
import dh.data.service.UltimateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Hello world!
 *
 */
public class App {
    private static final Logger LOG = LoggerFactory.getLogger(App.class);

    public static void main( String[] args ) throws IOException {
        long start = System.currentTimeMillis();
        LOG.info("[处理中间表]");
        MidDao.clear();
        MidService.calc();
        LOG.info("[处理最终表]");
        UltimateDao.clear();
        UltimateService.calc();
        long end = System.currentTimeMillis();
        System.out.println((end - start) / 1000 + "s");
    }
}
