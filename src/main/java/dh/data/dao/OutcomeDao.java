package dh.data.dao;

import com.csvreader.CsvReader;
import dh.data.config.IConst;
import dh.data.model.Outcome;
import dh.data.util.TimeUtil;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lonel on 2017/6/10.
 */
public class OutcomeDao {
    private final static String fileName = "/src/结果数据（只有上限）.csv";
    private final static String sheetName = "结果数据（只有上限）";

    private static CsvReader csvReader = null;

    static {
        // 对读取Excel表格标题测试
        try {
            InputStream is = new FileInputStream(IConst.PATH + fileName);
//            BufferedReader reader = new BufferedReader(new FileReader(PathUtil.getClassPath() + file));
            csvReader = new CsvReader(is,',', Charset.forName("GB2312"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<Outcome> getAll() throws IOException {
        List<Outcome> list = new ArrayList<>();
        csvReader.readHeaders(); //逃过表头
        while(csvReader.readRecord()) {
            Outcome origin = parse(csvReader);
//            if (origin.getId() == null || origin.getId().equals("")) {
//                break;
//            }
            list.add(origin);
        }
        return list;
    }

    /**
     * @param index > 0
     * @return
     */
    public static Outcome get(int index) throws IOException {
        Outcome origin = new Outcome();
        csvReader.readHeaders(); //逃过表头
        int i = 0;
        while(csvReader.readRecord()) {
            if (i == index) {
                origin = parse(csvReader);
                break;
            }
            i++;
        }
        return origin;
    }

    private static Outcome parse(CsvReader reader) {
        Outcome outcome = new Outcome();
        try {
            outcome.setId(Integer.parseInt(reader.get(0)));
            outcome.setTime(TimeUtil.parseDate(reader.get(1), "HH:mm:ss"));
            outcome.setRaltc((int) (Float.parseFloat(reader.get(2)) * IConst.RALTC_FACTOR));
            outcome.setQnh(Integer.parseInt(reader.get(3)));
            outcome.setHeight(Integer.parseInt(reader.get(4)));
            outcome.setFlightId(Integer.parseInt(reader.get(5)));
            outcome.setDate(TimeUtil.parseDate(reader.get(6), "yyyyMMdd"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return outcome;
    }

}
