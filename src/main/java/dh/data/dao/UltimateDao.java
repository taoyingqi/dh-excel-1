package dh.data.dao;

import dh.data.config.IConst;
import dh.data.model.Sample;
import dh.data.model.Ultimate;
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
 * Created by MT-T450 on 2017/6/8.
 */
public class UltimateDao {

    private final static String fileName = "/dist/181个航班最终表 0411.xlsx";
    private final static String sheetName = "181个航班最终表";
    private final static Integer headerLines = 4;
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


    public static void save(Ultimate ultimate) {
        write(ultimate);
        output();
    }

    public static void saveList(List<Ultimate> list) {
        for (Ultimate ultimate : list) {
            write(ultimate);
        }
        output();
    }

    public static Ultimate get(int index) {
        if (index < headerLines) {
            throw new ArrayIndexOutOfBoundsException("从第" + headerLines + "行开始读");
        }
        row = sheet.getRow(index);
        return parse(row);
    }

    public static List<Ultimate> getAll() {
        List<Ultimate> list = new ArrayList<>();
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

    // excel 解析成 对象
    private static Ultimate parse(Row row) {
        Ultimate ultimate = new Ultimate();
        ultimate.setFlightId((int) row.getCell(0).getNumericCellValue());
        ultimate.setDown500n((int) row.getCell(1).getNumericCellValue());
        ultimate.setLast1Down500Time(TimeUtil.parseDate(row.getCell(2).getStringCellValue(), TIME_TYPE));
        ultimate.setDown0n((int) row.getCell(3).getNumericCellValue());
        ultimate.setFirst1Down0Time(TimeUtil.parseDate(row.getCell(4).getStringCellValue(), TIME_TYPE));
        ultimate.setDurationTime(TimeUtil.parseDate(row.getCell(5).getStringCellValue(), TIME_TYPE));
        ultimate.setWxdMdc(new Sample(
                TimeUtil.parseDate(row.getCell(6).getStringCellValue(), TIME_TYPE),
                TimeUtil.parseDate(row.getCell(7).getStringCellValue(), TIME_TYPE),
                (int) row.getCell(8).getNumericCellValue(),
                null
        ));
        ultimate.setQnhMdc(new Sample(
                TimeUtil.parseDate(row.getCell(9).getStringCellValue(), TIME_TYPE),
                TimeUtil.parseDate(row.getCell(10).getStringCellValue(), TIME_TYPE),
                (int) row.getCell(11).getNumericCellValue(),
                null
        ));
        ultimate.setHeightMdc(new Sample(
                TimeUtil.parseDate(row.getCell(12).getStringCellValue(), TIME_TYPE),
                TimeUtil.parseDate(row.getCell(13).getStringCellValue(), TIME_TYPE),
                (int) row.getCell(14).getNumericCellValue(),
                null
        ));
        ultimate.setDownRateGt500n((int) row.getCell(15).getNumericCellValue());
        ultimate.setDownRateGt500Ld(new Sample(
                TimeUtil.parseDate(row.getCell(16).getStringCellValue(), TIME_TYPE),
                TimeUtil.parseDate(row.getCell(17).getStringCellValue(), TIME_TYPE),
                null,
                (int) row.getCell(18).getNumericCellValue()
        ));
        return ultimate;
    }

    // 对象写入 excel
    private static void write(Ultimate ultimate) {
        row = sheet.createRow(sheet.getLastRowNum() + 1);
        row.createCell(0).setCellValue(ultimate.getFlightId());
        row.createCell(1).setCellValue(ultimate.getDown500n());
        row.createCell(2).setCellValue(TimeUtil.formatDate(ultimate.getLast1Down500Time().getTime(), TIME_TYPE));
        row.createCell(3).setCellValue(ultimate.getDown0n());
        row.createCell(4).setCellValue(TimeUtil.formatDate(ultimate.getFirst1Down0Time().getTime(), TIME_TYPE));
        row.createCell(5).setCellValue(TimeUtil.formatDate(ultimate.getDurationTime().getTime(), TIME_TYPE));
        // 无线电高度最大下降率
        row.createCell(6).setCellValue(TimeUtil.formatDate(ultimate.getWxdMdc().getStartTime().getTime(), TIME_TYPE));
        row.createCell(7).setCellValue(TimeUtil.formatDate(ultimate.getWxdMdc().getEndTime().getTime(), TIME_TYPE));
        row.createCell(8).setCellValue(ultimate.getWxdMdc().getDownRate());
        // QNH高度最大下降率
        row.createCell(9).setCellValue(TimeUtil.formatDate(ultimate.getQnhMdc().getStartTime().getTime(), TIME_TYPE));
        row.createCell(10).setCellValue(TimeUtil.formatDate(ultimate.getQnhMdc().getEndTime().getTime(), TIME_TYPE));
        row.createCell(11).setCellValue(ultimate.getQnhMdc().getDownRate());
        // Height高度最大下降率
        row.createCell(12).setCellValue(TimeUtil.formatDate(ultimate.getHeightMdc().getStartTime().getTime(), TIME_TYPE));
        row.createCell(13).setCellValue(TimeUtil.formatDate(ultimate.getHeightMdc().getEndTime().getTime(), TIME_TYPE));
        row.createCell(14).setCellValue(ultimate.getHeightMdc().getDownRate());
        // 下降率超过500英尺每分钟
        row.createCell(15).setCellValue(ultimate.getDownRateGt500n());
        row.createCell(16).setCellValue(TimeUtil.formatDate(ultimate.getDownRateGt500Ld().getStartTime().getTime(), TIME_TYPE));
        row.createCell(17).setCellValue(TimeUtil.formatDate(ultimate.getDownRateGt500Ld().getEndTime().getTime(), TIME_TYPE));
        row.createCell(18).setCellValue(ultimate.getDownRateGt500Ld().getDurationSec());

        for (int i = 0; i < 18; i++) {
            sheet.autoSizeColumn((short)i); //自动调整第一列宽度
        }
    }

}
