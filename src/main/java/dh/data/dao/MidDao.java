package dh.data.dao;

import dh.data.config.IConst;
import dh.data.dto.eo.MidEo;
import dh.data.model.Mid;
import dh.data.model.Sample;
import dh.data.util.TimeUtil;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
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
 * Created by MT-T450 on 2017/6/7.
 */
public class MidDao {
    private final static Logger LOG = LoggerFactory.getLogger(MidDao.class);
    private final static String fileName = "/dist/181个航班中间表 0411.xlsx";
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
            LOG.info("[创建新文件->{}]", IConst.PATH + fileName);
            create();
        } catch (IOException e) {
            e.printStackTrace();
        }
        sheet = workbook.getSheetAt(0);
    }

    public static void save(Mid mid) {
        write(new MidEo(mid));
        output();
    }

    public static void saveList(List<Mid> list) {
        for (Mid mid : list) {
            write(new MidEo(mid));
        }
        output();
    }


    public static Mid get(int index) {
        if (index < headerLines) {
            throw new ArrayIndexOutOfBoundsException("从第" + headerLines + "行开始读");
        }
        row = sheet.getRow(index);
        return parse(row);
    }

    public static List<Mid> getAll() {
        List<Mid> list = new ArrayList<>();
        for (int i = headerLines; i <= sheet.getLastRowNum(); i++) {
            list.add(parse(sheet.getRow(i)));
        }
        return list;
    }


    public static List<Mid> getList(int start, int end) {
        if (start < 0) {
            throw new ArrayIndexOutOfBoundsException(start);
        }
        if (start > end) {
            throw new RuntimeException("start 必须小于 end");
        }
        List<Mid> list = new ArrayList<>();
        for (int i = start; i <= sheet.getLastRowNum(); i++) {
            if (i == end) {
                break;
            }
            list.add(parse(sheet.getRow(i)));
        }
        return list;
    }

    // 创建新的excel，并生成表头
    public static void create() {
        // 声明一个工作薄
        workbook = new XSSFWorkbook();
        // 生成一个表格
        workbook.createSheet(sheetName);
        // 设置表头
        sheet = workbook.getSheetAt(0);
        // 样式设置
        sheet.setColumnWidth(0, 1800);
        sheet.setColumnWidth(1, 4000);
        sheet.setColumnWidth(2, 2000);
        sheet.setColumnWidth(3, 4000);
        sheet.setColumnWidth(4, 4000);
        sheet.setColumnWidth(5, 2000);
        sheet.setColumnWidth(6, 4000);
        sheet.setColumnWidth(7, 4000);
        sheet.setColumnWidth(8, 2000);
        sheet.setColumnWidth(9, 4000);
        sheet.setColumnWidth(10, 2000);
        sheet.setColumnWidth(11, 4000);
        sheet.setColumnWidth(12, 4000);
        sheet.setColumnWidth(13, 2000);
        sheet.setColumnWidth(14, 4000);
        sheet.setColumnWidth(15, 4000);
        sheet.setColumnWidth(16, 2000);
        sheet.setColumnWidth(17, 4000);
        sheet.setColumnWidth(18, 2000);
        sheet.setColumnWidth(19, 4000);
        sheet.setColumnWidth(20, 4000);
        sheet.setColumnWidth(21, 2000);
        sheet.setColumnWidth(22, 4000);
        sheet.setColumnWidth(23, 4000);
        sheet.setColumnWidth(24, 2000);
        sheet.setColumnWidth(25, 1500);
        sheet.setColumnWidth(26, 1300);
        sheet.setColumnWidth(27, 1500);
        sheet.setColumnWidth(28, 4000);
        sheet.setColumnWidth(29, 4000);
        // 合并单元格
        sheet.addMergedRegion(new CellRangeAddress(0,2,0,0));
        // 无线电高度口径
        sheet.addMergedRegion(new CellRangeAddress(0,0,1,8));
        sheet.addMergedRegion(new CellRangeAddress(1,2,1,1));
        sheet.addMergedRegion(new CellRangeAddress(1,2,2,2));
        sheet.addMergedRegion(new CellRangeAddress(1,1,3,5));
        sheet.addMergedRegion(new CellRangeAddress(1,1,6,8));
        // QNH高度口径
        sheet.addMergedRegion(new CellRangeAddress(0,0,9,16));
        sheet.addMergedRegion(new CellRangeAddress(1,2,9,9));
        sheet.addMergedRegion(new CellRangeAddress(1,2,10,10));
        sheet.addMergedRegion(new CellRangeAddress(1,1,11,13));
        sheet.addMergedRegion(new CellRangeAddress(1,1,14,16));
        // Height高度口径
        sheet.addMergedRegion(new CellRangeAddress(0,0,17,24));
        sheet.addMergedRegion(new CellRangeAddress(1,2,17,17));
        sheet.addMergedRegion(new CellRangeAddress(1,2,18,18));
        sheet.addMergedRegion(new CellRangeAddress(1,1,19,21));
        sheet.addMergedRegion(new CellRangeAddress(1,1,22,24));
        //
        sheet.addMergedRegion(new CellRangeAddress(1,2,25,25));
        sheet.addMergedRegion(new CellRangeAddress(1,2,26,26));
        sheet.addMergedRegion(new CellRangeAddress(1,2,27,27));
        sheet.addMergedRegion(new CellRangeAddress(1,2,28,28));
        sheet.addMergedRegion(new CellRangeAddress(1,2,29,29));

        // 第一行
        row = sheet.createRow(0);
        row.createCell(0).setCellValue("航班ID");
        row.createCell(1).setCellValue("无线电高度口径");
        row.createCell(9).setCellValue("QNH高度口径");
        row.createCell(17).setCellValue("Height高度口径");
        // 第二行
        row = sheet.createRow(1);
        row.createCell(1).setCellValue("时刻");
        row.createCell(2).setCellValue("高度");
        row.createCell(3).setCellValue("0.25秒采样/0.5秒高度变化 即时下降率");
        row.createCell(6).setCellValue("0.25/0.5/2 持续下降率（平均值）");
        row.createCell(9).setCellValue("时刻");
        row.createCell(10).setCellValue("高度");
        row.createCell(11).setCellValue("1秒采样/1秒高度变化 即时下降率");
        row.createCell(14).setCellValue("1秒/1秒/持续2秒 持续下降率（平均值）");
        row.createCell(17).setCellValue("时刻");
        row.createCell(18).setCellValue("高度");
        row.createCell(19).setCellValue("1秒采样/1秒高度变化 即时下降率");
        row.createCell(22).setCellValue("1秒/1秒/持续2秒 持续下降率（平均值）");
        row.createCell(25).setCellValue("无线电");
        row.createCell(26).setCellValue("QNH");
        row.createCell(27).setCellValue("Height");
        row.createCell(28).setCellValue("综合至少两个满足");
        row.createCell(29).setCellValue("持续时间");
        // 第三行
        row = sheet.createRow(2);
        row.createCell(3).setCellValue("起始时刻");
        row.createCell(4).setCellValue("截止时刻");
        row.createCell(5).setCellValue("下降率");
        row.createCell(6).setCellValue("起始时刻");
        row.createCell(7).setCellValue("截止时刻");
        row.createCell(8).setCellValue("下降率");
        row.createCell(11).setCellValue("起始时刻");
        row.createCell(12).setCellValue("截止时刻");
        row.createCell(13).setCellValue("下降率");
        row.createCell(14).setCellValue("起始时刻");
        row.createCell(15).setCellValue("截止时刻");
        row.createCell(16).setCellValue("下降率");
        row.createCell(19).setCellValue("起始时刻");
        row.createCell(20).setCellValue("截止时刻");
        row.createCell(21).setCellValue("下降率");
        row.createCell(22).setCellValue("起始时刻");
        row.createCell(23).setCellValue("截止时刻");
        row.createCell(24).setCellValue("下降率");
        //输出到excel
        output();
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
                TimeUtil.parseDate(row.getCell(1).getStringCellValue(), TIME_MILLIS_TYPE),
                (int) row.getCell(2).getNumericCellValue(),
                new Sample(
                        TimeUtil.parseDate(row.getCell(3).getStringCellValue(), TIME_MILLIS_TYPE),
                        TimeUtil.parseDate(row.getCell(4).getStringCellValue(), TIME_MILLIS_TYPE),
                        (int) row.getCell(5).getNumericCellValue(),
                        null
                ),
                new Sample(
                        TimeUtil.parseDate(row.getCell(6).getStringCellValue(), TIME_MILLIS_TYPE),
                        TimeUtil.parseDate(row.getCell(7).getStringCellValue(), TIME_MILLIS_TYPE),
                        (int) row.getCell(8).getNumericCellValue(),
                        null
                )));
        // QNH高度口径
        mid.setQnhFh(new Mid.FH(
                TimeUtil.parseDate(row.getCell(9).getStringCellValue(), TIME_MILLIS_TYPE),
                (int) row.getCell(10).getNumericCellValue(),
                new Sample(
                        TimeUtil.parseDate(row.getCell(11).getStringCellValue(), TIME_MILLIS_TYPE),
                        TimeUtil.parseDate(row.getCell(12).getStringCellValue(), TIME_MILLIS_TYPE),
                        (int) row.getCell(13).getNumericCellValue(),
                        null
                ),
                new Sample(
                        TimeUtil.parseDate(row.getCell(14).getStringCellValue(), TIME_MILLIS_TYPE),
                        TimeUtil.parseDate(row.getCell(15).getStringCellValue(), TIME_MILLIS_TYPE),
                        (int) row.getCell(16).getNumericCellValue(),
                        null
                )));
        // Height高度口径
        mid.setQnhFh(new Mid.FH(
                TimeUtil.parseDate(row.getCell(17).getStringCellValue(), TIME_MILLIS_TYPE),
                (int) row.getCell(18).getNumericCellValue(),
                new Sample(
                        TimeUtil.parseDate(row.getCell(19).getStringCellValue(), TIME_MILLIS_TYPE),
                        TimeUtil.parseDate(row.getCell(20).getStringCellValue(), TIME_MILLIS_TYPE),
                        (int) row.getCell(21).getNumericCellValue(),
                        null
                ),
                new Sample(
                        TimeUtil.parseDate(row.getCell(22).getStringCellValue(), TIME_MILLIS_TYPE),
                        TimeUtil.parseDate(row.getCell(23).getStringCellValue(), TIME_MILLIS_TYPE),
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

    private static void write(MidEo midEo) {
        row = sheet.createRow(sheet.getLastRowNum() + 1);
        row.createCell(0).setCellValue(midEo.getFlightId());
        // 无线电高度口径
        row.createCell(1).setCellValue(midEo.getWxdFh().getTime());
        row.createCell(2).setCellValue(midEo.getWxdFh().getHeight());
        row.createCell(3).setCellValue(midEo.getWxdFh().getSample1().getStartTime());
        row.createCell(4).setCellValue(midEo.getWxdFh().getSample1().getEndTime());
        row.createCell(5).setCellValue(midEo.getWxdFh().getSample1().getDownRate());
        row.createCell(6).setCellValue(midEo.getWxdFh().getSample2().getStartTime());
        row.createCell(7).setCellValue(midEo.getWxdFh().getSample2().getEndTime());
        row.createCell(8).setCellValue(midEo.getWxdFh().getSample2().getDownRate());
        // QNH高度口径
        row.createCell(9).setCellValue(midEo.getQnhFh().getTime());
        row.createCell(10).setCellValue(midEo.getQnhFh().getHeight());
        row.createCell(11).setCellValue(midEo.getQnhFh().getSample1().getStartTime());
        row.createCell(12).setCellValue(midEo.getQnhFh().getSample1().getEndTime());
        row.createCell(13).setCellValue(midEo.getQnhFh().getSample1().getDownRate());
        row.createCell(14).setCellValue(midEo.getQnhFh().getSample2().getStartTime());
        row.createCell(15).setCellValue(midEo.getQnhFh().getSample2().getEndTime());
        row.createCell(16).setCellValue(midEo.getQnhFh().getSample2().getDownRate());
        // Height高度口径
        row.createCell(17).setCellValue(midEo.getHeightFh().getTime());
        row.createCell(18).setCellValue(midEo.getHeightFh().getHeight());
        row.createCell(19).setCellValue(midEo.getHeightFh().getSample1().getStartTime());
        row.createCell(20).setCellValue(midEo.getHeightFh().getSample1().getEndTime());
        row.createCell(21).setCellValue(midEo.getHeightFh().getSample1().getDownRate());
        row.createCell(22).setCellValue(midEo.getHeightFh().getSample2().getStartTime());
        row.createCell(23).setCellValue(midEo.getHeightFh().getSample2().getEndTime());
        row.createCell(24).setCellValue(midEo.getHeightFh().getSample2().getDownRate());

        row.createCell(25).setCellValue(midEo.getWxdCond());
        row.createCell(26).setCellValue(midEo.getQnhCond());
        row.createCell(27).setCellValue(midEo.getHeightCond());
        row.createCell(28).setCellValue(midEo.getMultiCond());
        row.createCell(29).setCellValue(midEo.getDurationSec());
    }

}
