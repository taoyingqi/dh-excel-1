package dh.data.dao;

import dh.data.config.IConst;
import dh.data.model.Mid;
import dh.data.model.Sample;
import dh.data.util.TimeUtil;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by MT-T450 on 2017/6/7.
 */
public class MidDao {
    private final static String fileName = "/dist/181个航班中间表 0411.xlsx";
    private final static String sheetName = "181个航班最终表";
    private final static Integer headerLines = 3;
    private static POIFSFileSystem fs;
    private static XSSFWorkbook workbook = null;
    private static XSSFSheet sheet = null;
    private static XSSFRow row = null;

    public final static String TIME_TYPE = "HH:mm:ss:SSS"; //时间格式

    static {
        try {
            InputStream is = new FileInputStream(IConst.PATH + fileName);
//            fs = new POIFSFileSystem(is);
            workbook = new XSSFWorkbook(is);
        } catch (FileNotFoundException e) {
            System.out.println("创建新文件：" + IConst.PATH + fileName);
            create();
        } catch (IOException e) {
            e.printStackTrace();
        }
        sheet = workbook.getSheetAt(0);
    }

    public static void save(Mid mid) {
        write(mid);
        output();
    }

    public static void saveList(List<Mid> list) {
        for (Mid mid : list) {
            write(mid);
        }
        output();
    }

    public static List<Mid> getAll() {
        List<Mid> list = null;
        return list;
    }


    public static Mid get(int index) {
        if (index < headerLines) {
            throw new ArrayIndexOutOfBoundsException("从第" + headerLines + "行开始读");
        }
        row = sheet.getRow(index);
        return parse(row);
    }

    public static List<Mid> getList(int start, int end) {
        List<Mid> list = new ArrayList<>();
        for (int i = headerLines; i <= sheet.getLastRowNum(); i++) {
            list.add(parse(sheet.getRow(i)));
        }
        return list;
    }

    private static void create() {
        // 声明一个工作薄
        workbook = new XSSFWorkbook();
        // 生成一个表格
        workbook.createSheet(sheetName);
        // 设置表头

        //
        try {
            OutputStream os = new FileOutputStream(IConst.PATH + fileName);
            workbook.write(os);
            os.flush();
            os.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 写入到文件
    public static void output() {
        try {
            OutputStream os = new FileOutputStream(IConst.PATH + fileName);
            workbook.write(os);
            os.flush();
            os.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Mid parse(Row row) {
        Mid mid = new Mid();
        mid.setFlightId((int) row.getCell(0).getNumericCellValue());
        // 无线电高度口径
        mid.setWxdFh(new Mid.FH(
                TimeUtil.parseDate(row.getCell(1).getStringCellValue(), TIME_TYPE),
                (int) row.getCell(2).getNumericCellValue(),
                new Sample(
                        TimeUtil.parseDate(row.getCell(3).getStringCellValue(), TIME_TYPE),
                        TimeUtil.parseDate(row.getCell(4).getStringCellValue(), TIME_TYPE),
                        (int) row.getCell(5).getNumericCellValue(),
                        null
                ),
                new Sample(
                        TimeUtil.parseDate(row.getCell(6).getStringCellValue(), TIME_TYPE),
                        TimeUtil.parseDate(row.getCell(7).getStringCellValue(), TIME_TYPE),
                        (int) row.getCell(8).getNumericCellValue(),
                        null
                )));
        // QNH高度口径
        mid.setQnhFh(new Mid.FH(
                TimeUtil.parseDate(row.getCell(9).getStringCellValue(), TIME_TYPE),
                (int) row.getCell(10).getNumericCellValue(),
                new Sample(
                        TimeUtil.parseDate(row.getCell(11).getStringCellValue(), TIME_TYPE),
                        TimeUtil.parseDate(row.getCell(12).getStringCellValue(), TIME_TYPE),
                        (int) row.getCell(13).getNumericCellValue(),
                        null
                ),
                new Sample(
                        TimeUtil.parseDate(row.getCell(14).getStringCellValue(), TIME_TYPE),
                        TimeUtil.parseDate(row.getCell(15).getStringCellValue(), TIME_TYPE),
                        (int) row.getCell(16).getNumericCellValue(),
                        null
                )));
        // Height高度口径
        mid.setQnhFh(new Mid.FH(
                TimeUtil.parseDate(row.getCell(17).getStringCellValue(), TIME_TYPE),
                (int) row.getCell(18).getNumericCellValue(),
                new Sample(
                        TimeUtil.parseDate(row.getCell(19).getStringCellValue(), TIME_TYPE),
                        TimeUtil.parseDate(row.getCell(20).getStringCellValue(), TIME_TYPE),
                        (int) row.getCell(21).getNumericCellValue(),
                        null
                ),
                new Sample(
                        TimeUtil.parseDate(row.getCell(22).getStringCellValue(), TIME_TYPE),
                        TimeUtil.parseDate(row.getCell(23).getStringCellValue(), TIME_TYPE),
                        (int) row.getCell(24).getNumericCellValue(),
                        null
                )));
        mid.setWxdCond(row.getCell(25).getBooleanCellValue());
        mid.setQnhCond(row.getCell(26).getBooleanCellValue());
        mid.setHeightCond(row.getCell(27).getBooleanCellValue());
        mid.setMultiCond(row.getCell(28).getBooleanCellValue());
        mid.setDurationSec((int) row.getCell(29).getNumericCellValue());
        return mid;
    }

    private static void write(Mid mid) {
        row = sheet.createRow(sheet.getLastRowNum() + 1);
        row.createCell(0).setCellValue(mid.getFlightId());
        // 无线电高度口径
        row.createCell(1).setCellValue(TimeUtil.formatDate(mid.getWxdFh().getTime().getTime(), TIME_TYPE));
        row.createCell(2).setCellValue(mid.getWxdFh().getHeight());
        row.createCell(3).setCellValue(TimeUtil.formatDate(mid.getWxdFh().getSample1().getStartTime().getTime(), TIME_TYPE));
        row.createCell(4).setCellValue(TimeUtil.formatDate(mid.getWxdFh().getSample1().getEndTime().getTime(), TIME_TYPE));
        row.createCell(5).setCellValue(mid.getWxdFh().getSample1().getDownRate());
        row.createCell(6).setCellValue(TimeUtil.formatDate(mid.getWxdFh().getSample2().getStartTime().getTime(), TIME_TYPE));
        row.createCell(7).setCellValue(TimeUtil.formatDate(mid.getWxdFh().getSample2().getEndTime().getTime(), TIME_TYPE));
        row.createCell(8).setCellValue(mid.getWxdFh().getSample2().getDownRate());
        // QNH高度口径
        row.createCell(9).setCellValue(TimeUtil.formatDate(mid.getQnhFh().getTime().getTime(), TIME_TYPE));
        row.createCell(10).setCellValue(mid.getQnhFh().getHeight());
        row.createCell(11).setCellValue(TimeUtil.formatDate(mid.getQnhFh().getSample1().getStartTime().getTime(), TIME_TYPE));
        row.createCell(12).setCellValue(TimeUtil.formatDate(mid.getQnhFh().getSample1().getEndTime().getTime(), TIME_TYPE));
        row.createCell(13).setCellValue(mid.getQnhFh().getSample1().getDownRate());
        row.createCell(14).setCellValue(TimeUtil.formatDate(mid.getQnhFh().getSample2().getStartTime().getTime(), TIME_TYPE));
        row.createCell(15).setCellValue(TimeUtil.formatDate(mid.getQnhFh().getSample2().getEndTime().getTime(), TIME_TYPE));
        row.createCell(16).setCellValue(mid.getQnhFh().getSample2().getDownRate());
        // Height高度口径
        row.createCell(17).setCellValue(TimeUtil.formatDate(mid.getQnhFh().getTime().getTime(), TIME_TYPE));
        row.createCell(18).setCellValue(mid.getQnhFh().getHeight());
        row.createCell(19).setCellValue(TimeUtil.formatDate(mid.getQnhFh().getSample1().getStartTime().getTime(), TIME_TYPE));
        row.createCell(20).setCellValue(TimeUtil.formatDate(mid.getQnhFh().getSample1().getEndTime().getTime(), TIME_TYPE));
        row.createCell(21).setCellValue(mid.getQnhFh().getSample1().getDownRate());
        row.createCell(22).setCellValue(TimeUtil.formatDate(mid.getQnhFh().getSample2().getStartTime().getTime(), TIME_TYPE));
        row.createCell(23).setCellValue(TimeUtil.formatDate(mid.getQnhFh().getSample2().getEndTime().getTime(), TIME_TYPE));
        row.createCell(24).setCellValue(mid.getQnhFh().getSample2().getDownRate());

        row.createCell(25).setCellValue(mid.getWxdCond());
        row.createCell(26).setCellValue(mid.getQnhCond());
        row.createCell(27).setCellValue(mid.getMultiCond());
        row.createCell(28).setCellValue(mid.getDurationSec());
    }

}
