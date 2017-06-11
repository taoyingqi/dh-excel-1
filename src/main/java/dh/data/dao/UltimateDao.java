package dh.data.dao;

import dh.data.config.IConst;
import dh.data.model.Sample;
import dh.data.model.Ultimate;
import dh.data.util.TimeUtil;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static dh.data.util.TimeUtil.TIME_MILLIS_TYPE;

/**
 * Created by MT-T450 on 2017/6/8.
 */
public class UltimateDao {
    private final static Logger LOG = LoggerFactory.getLogger(UltimateDao.class);

    private final static String fileName = "/dist/181个航班最终表 0411.xlsx";
    private final static String sheetName = "181个航班最终表";
    private final static Integer headerLines = 3;
    private static POIFSFileSystem fs;
    private static XSSFWorkbook workbook = null;
    private static XSSFSheet sheet = null;
    private static XSSFRow row = null;

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

    public static void delete() {
        File file = new File(IConst.PATH + fileName);
        if (file.exists()) {
            LOG.info("[删除文件->{}]", IConst.PATH + fileName);
            file.delete();
        }
    }

    public static void clear() {
        LOG.info("[清除旧数据]");
        for (int i = headerLines; i <= sheet.getLastRowNum(); i++) {
            sheet.removeRow(sheet.getRow(i));
        }
    }

    private static void create() {
        // 声明一个工作薄
        workbook = new XSSFWorkbook();
        // 生成一个表格
        workbook.createSheet(sheetName);
        // 设置表头
        sheet = workbook.getSheetAt(0);
        // 样式设置
        sheet.setColumnWidth(0, 1800);
        sheet.setColumnWidth(1, 4000);
        sheet.setColumnWidth(2, 6000);
        sheet.setColumnWidth(3, 4000);
        sheet.setColumnWidth(4, 6000);
        sheet.setColumnWidth(5, 4500);
        sheet.setColumnWidth(6, 4000);
        sheet.setColumnWidth(7, 4000);
        sheet.setColumnWidth(8, 2000);
        sheet.setColumnWidth(9, 4000);
        sheet.setColumnWidth(10, 4000);
        sheet.setColumnWidth(11, 2000);
        sheet.setColumnWidth(12, 4000);
        sheet.setColumnWidth(13, 4000);
        sheet.setColumnWidth(14, 2000);
        sheet.setColumnWidth(15, 2000);
        sheet.setColumnWidth(16, 4000);
        sheet.setColumnWidth(17, 4000);
        sheet.setColumnWidth(18, 2000);
        // 单元格样式
        XSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        // 合并单元格
        sheet.addMergedRegion(new CellRangeAddress(0, 2, 0, 0));
        sheet.addMergedRegion(new CellRangeAddress(0, 2, 1,1));
        sheet.addMergedRegion(new CellRangeAddress(0, 2, 2, 2));
        sheet.addMergedRegion(new CellRangeAddress(0, 2, 3, 3));
        sheet.addMergedRegion(new CellRangeAddress(0, 2, 4, 4));

        sheet.addMergedRegion(new CellRangeAddress(0, 2, 5, 5));
        // 三种来源各取一个口径最大下降率
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 6, 14));
        // 无线电高度最大下降率
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 6, 8));
        // QNH高度最大下降率
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 9, 11));
        // Height高度最大下降率
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 12, 14));
        // 下降率超过500英尺每分钟
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 15, 18));
        // 最长一次持续毫秒
        sheet.addMergedRegion(new CellRangeAddress(1, 2, 15, 15));
        // 最长一次持续毫秒
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 16, 18));
        // 第一行
        row = sheet.createRow(0);
        row.createCell(0).setCellValue("航班ID");
        row.createCell(1).setCellValue("下穿500英尺次数");
        row.createCell(2).setCellValue("最后一次下穿500英尽时刻");
        row.createCell(3).setCellValue("下穿0英尺次数");
        row.createCell(4).setCellValue("第一次下穿0英尺时刻");
        row.createCell(5).setCellValue("持续时长（分：秒）");
        row.createCell(6).setCellValue("三种来源各取一个口径最大下降率");
        row.createCell(15).setCellValue("下降率超过500英尺每分钟");
        // 第二行
        row = sheet.createRow(1);
        row.createCell(6).setCellValue("无线电高度最大下降率");
        row.createCell(9).setCellValue("QNH高度最大下降率");
        row.createCell(12).setCellValue("Height高度最大下降率");
        row.createCell(15).setCellValue("次数");
        row.createCell(16).setCellValue("最长一次持续毫秒");
        // 第三行
        row = sheet.createRow(2);
        row.createCell(6).setCellValue("起始时刻");
        row.createCell(7).setCellValue("截止时刻");
        row.createCell(8).setCellValue("下降率");
        row.createCell(9).setCellValue("起始时刻");
        row.createCell(10).setCellValue("截止时刻");
        row.createCell(11).setCellValue("下降率");
        row.createCell(12).setCellValue("起始时刻");
        row.createCell(13).setCellValue("截止时刻");
        row.createCell(14).setCellValue("下降率");
        row.createCell(16).setCellValue("起始时刻");
        row.createCell(17).setCellValue("截止时刻");
        row.createCell(18).setCellValue("持续毫秒");
        // 输出到excel
        output();
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
        ultimate.setLast1Down500Time(TimeUtil.parseDate(row.getCell(2).getStringCellValue(), TIME_MILLIS_TYPE));
        ultimate.setDown0n((int) row.getCell(3).getNumericCellValue());
        ultimate.setFirst1Down0Time(TimeUtil.parseDate(row.getCell(4).getStringCellValue(), TIME_MILLIS_TYPE));
        ultimate.setDurationTime(TimeUtil.parseDate(row.getCell(5).getStringCellValue(), TIME_MILLIS_TYPE));
        ultimate.setWxdMdc(new Sample(
                TimeUtil.parseDate(row.getCell(6).getStringCellValue(), TIME_MILLIS_TYPE),
                TimeUtil.parseDate(row.getCell(7).getStringCellValue(), TIME_MILLIS_TYPE),
                (int) row.getCell(8).getNumericCellValue(),
                null
        ));
        ultimate.setQnhMdc(new Sample(
                TimeUtil.parseDate(row.getCell(9).getStringCellValue(), TIME_MILLIS_TYPE),
                TimeUtil.parseDate(row.getCell(10).getStringCellValue(), TIME_MILLIS_TYPE),
                (int) row.getCell(11).getNumericCellValue(),
                null
        ));
        ultimate.setHeightMdc(new Sample(
                TimeUtil.parseDate(row.getCell(12).getStringCellValue(), TIME_MILLIS_TYPE),
                TimeUtil.parseDate(row.getCell(13).getStringCellValue(), TIME_MILLIS_TYPE),
                (int) row.getCell(14).getNumericCellValue(),
                null
        ));
        ultimate.setDownRateGt500n((int) row.getCell(15).getNumericCellValue());
        ultimate.setDownRateGt500Ld(new Sample(
                TimeUtil.parseDate(row.getCell(16).getStringCellValue(), TIME_MILLIS_TYPE),
                TimeUtil.parseDate(row.getCell(17).getStringCellValue(), TIME_MILLIS_TYPE),
                null,
                (int) row.getCell(18).getNumericCellValue()
        ));
        return ultimate;
    }

    // 对象写入 excel
    private static void write(Ultimate ultimate) {
        row = sheet.createRow(sheet.getLastRowNum() >= headerLines ? (sheet.getLastRowNum() + 1) : headerLines);
        row.createCell(0).setCellValue(ultimate.getFlightId());
        if (ultimate.getDown500n() != null) {
            row.createCell(1).setCellValue(ultimate.getDown500n());
        } else {
            row.createCell(1);
        }
        row.createCell(2).setCellValue(TimeUtil.formatDate(ultimate.getLast1Down500Time(), TIME_MILLIS_TYPE));
        if (ultimate.getDown0n() != null) {
            row.createCell(3).setCellValue(ultimate.getDown0n());
        } else {
            row.createCell(3);
        }
        row.createCell(4).setCellValue(TimeUtil.formatDate(ultimate.getFirst1Down0Time(), TIME_MILLIS_TYPE));
        row.createCell(5).setCellValue(TimeUtil.formatDate(ultimate.getDurationTime(), TIME_MILLIS_TYPE));
        // 无线电高度最大下降率
        row.createCell(6).setCellValue(TimeUtil.formatDate(ultimate.getWxdMdc().getStartTime(), TIME_MILLIS_TYPE));
        row.createCell(7).setCellValue(TimeUtil.formatDate(ultimate.getWxdMdc().getEndTime(), TIME_MILLIS_TYPE));
        if (ultimate.getWxdMdc().getDownRate() != null) {
            row.createCell(8).setCellValue(ultimate.getWxdMdc().getDownRate());
        } else {
            row.createCell(8);
        }
        // QNH高度最大下降率
        row.createCell(9).setCellValue(TimeUtil.formatDate(ultimate.getQnhMdc().getStartTime(), TIME_MILLIS_TYPE));
        row.createCell(10).setCellValue(TimeUtil.formatDate(ultimate.getQnhMdc().getEndTime(), TIME_MILLIS_TYPE));
        if (ultimate.getQnhMdc().getDownRate() != null) {
            row.createCell(11).setCellValue(ultimate.getQnhMdc().getDownRate());
        } else {
            row.createCell(11);
        }
        // Height高度最大下降率
        row.createCell(12).setCellValue(TimeUtil.formatDate(ultimate.getHeightMdc().getStartTime(), TIME_MILLIS_TYPE));
        row.createCell(13).setCellValue(TimeUtil.formatDate(ultimate.getHeightMdc().getEndTime(), TIME_MILLIS_TYPE));
        if (ultimate.getHeightMdc().getDownRate() != null) {
            row.createCell(14).setCellValue(ultimate.getHeightMdc().getDownRate());
        } else {
            row.createCell(14);
        }
        // 下降率超过500英尺每分钟
        if (ultimate.getDownRateGt500n() != null) {
            row.createCell(15).setCellValue(ultimate.getDownRateGt500n());
        } else {
            row.createCell(15);
        }
        row.createCell(16).setCellValue(TimeUtil.formatDate(ultimate.getDownRateGt500Ld().getStartTime(), TIME_MILLIS_TYPE));
        row.createCell(17).setCellValue(TimeUtil.formatDate(ultimate.getDownRateGt500Ld().getEndTime(), TIME_MILLIS_TYPE));
        if (ultimate.getDownRateGt500Ld().getDurationSec() != null) {
            row.createCell(18).setCellValue(ultimate.getDownRateGt500Ld().getDurationSec());
        } else {
            row.createCell(18);
        }

        //        for (int i = 0; i < 18; i++) {
//            sheet.autoSizeColumn((short)i); //自动调整第一列宽度
//        }
    }

}
