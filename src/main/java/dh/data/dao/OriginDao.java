package dh.data.dao;

import dh.data.model.Origin;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by MT-T450 on 2017/6/7.
 */
public class OriginDao {
    private final static String file = "/src/加工过源数据完整.csv";
    private final static String fileName = "加工过源数据完整";

    private POIFSFileSystem fs;
    private HSSFWorkbook wb;
    private HSSFSheet sheet;
    private HSSFRow row;

    static {
        // 对读取Excel表格标题测试
        try {
            InputStream is = new FileInputStream(OriginDao.class.getClassLoader().getResource("/").getPath()+file);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static boolean save(Origin origin) {
        return true;
    }

    public static boolean saveList(List<Origin> list) {
        return true;
    }

    public static List<Origin> getAll() {
        List<Origin> list = null;

        return list;
    }

    public static List<Origin> getList(int start, int end) {
        List<Origin> list = null;
        return list;
    }

    public static Origin get(int index) {
        Origin origin = new Origin();
        return origin;
    }
}
