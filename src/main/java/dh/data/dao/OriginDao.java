package dh.data.dao;

import com.csvreader.CsvReader;
import dh.data.config.IConst;
import dh.data.model.Origin;
import dh.data.util.PathUtil;
import dh.data.util.TimeUtil;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by MT-T450 on 2017/6/7.
 */
public class OriginDao {
    private final static String file = "src/加工过源数据完整.csv";
    private final static String fileName = "加工过源数据完整";

    private static CsvReader csvReader = null;

    static {
        // 对读取Excel表格标题测试
        try {
            InputStream is = new FileInputStream(PathUtil.getClassPath() + file);
//            BufferedReader reader = new BufferedReader(new FileReader(PathUtil.getClassPath() + file));
            csvReader = new CsvReader(is,',', Charset.forName("GB2312"));
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

    public static List<Origin> getAll() throws IOException {
        List<Origin> list = new ArrayList<>();
        csvReader.readHeaders(); //逃过表头
        while(csvReader.readRecord()) {
            Origin origin = copy(csvReader);
//            if (origin.getId() == null || origin.getId().equals("")) {
//                break;
//            }
            list.add(origin);
        }
        return list;
    }

    public static List<Origin> getList(int start, int end) throws IOException {
        if (start < 0) {
            throw new ArrayIndexOutOfBoundsException(start);
        }
        if (start > end) {
            throw new RuntimeException("start 必须小于 end");
        }
        List<Origin> list = new ArrayList<>();
        csvReader.readHeaders();
        int i = 0;
        while(csvReader.readRecord()) {
            if (i >= end) {
                break;
            }
            if (i >= start) {
                Origin origin = copy(csvReader);
                list.add(origin);
            }
            i++;
        }
        return list;
    }

    /**
     * @param index > 0
     * @return
     */
    public static Origin get(int index) throws IOException {
        Origin origin = new Origin();
        csvReader.readHeaders(); //逃过表头
        int i = 0;
        while(csvReader.readRecord()) {
            if (i == index) {
                origin = copy(csvReader);
                break;
            }
            i++;
        }
        return origin;
    }

    private static Origin copy(CsvReader reader) {
        Origin origin = new Origin();
        try {
            origin.setId(Integer.parseInt(reader.get(0)));
            origin.setTime(TimeUtil.parseDate(reader.get(1), "HH:mm:ss"));
            origin.setWxd((int) (Float.parseFloat(reader.get(2)) * IConst.WXD_FACTOR));
            origin.setQnh(Integer.parseInt(reader.get(3)));
            origin.setHeight(Integer.parseInt(reader.get(4)));
            origin.setFlightId(Integer.parseInt(reader.get(5)));
            origin.setDate(TimeUtil.parseDate(reader.get(6), "yyyyMMdd"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return origin;
    }
}
