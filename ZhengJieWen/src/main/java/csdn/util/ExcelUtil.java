package csdn.util;

import csdn.pojo.StaffSalary;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 读入excel表格数据和数据写入excle表格
 */
public class ExcelUtil {
    private static final DateFormat FORMAT = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    /**
     * 读取各个部门的数据,装入map集合里
     * @param sourceFileInputStream 输入流
     * @param map 数据存储的集合
     */
    public static void readExcel_remuneration(String departmentName,InputStream sourceFileInputStream, Map<Integer,StaffSalary> map){

        try {
            //创建一个工作簿对象
            XSSFWorkbook workbook = new XSSFWorkbook(sourceFileInputStream);
            //获取第一个sheet
            XSSFSheet sheetAt = workbook.getSheetAt(0);
            //获取行迭代器
            Iterator<Row> rowIterator = sheetAt.rowIterator();
            rowIterator.hasNext();
            rowIterator.next();
            while (rowIterator.hasNext()){
                //获取一行信息
                Row next = rowIterator.next();
                Object jobNumber = getValue(next.getCell(0));
                Object name = getValue(next.getCell(1));
                Object diXing = getValue(next.getCell(2));
                Object gangWeiGonGzi = getValue(next.getCell(3));
                Object jiXiaoJiangJin = getValue(next.getCell(4));
                Object quanQinJiangJin = getValue(next.getCell(5));
                Object kaoQinKoChu = getValue(next.getCell(6));
                Object weiGuiChuFa = getValue(next.getCell(7));
                Object jiaoTongBuZhu = getValue(next.getCell(8));
                Object tongXinBuZhu = getValue(next.getCell(9));

                StaffSalary staffSalary = new StaffSalary();
                staffSalary.setDepartment(departmentName);
                staffSalary.setId(Integer.parseInt(jobNumber.toString()));
                staffSalary.setName((String) name);
                staffSalary.setDiXing(Double.parseDouble(diXing.toString()));
                staffSalary.setGangWeiGonGzi(Double.parseDouble(gangWeiGonGzi.toString()));
                staffSalary.setJiXiaoJiangJin(Double.parseDouble(jiXiaoJiangJin.toString()));
                staffSalary.setQuanQinJiangJin(Double.parseDouble(quanQinJiangJin.toString()));
                staffSalary.setKaoQinKoChu(Double.parseDouble(kaoQinKoChu.toString()));
                staffSalary.setWeiGuiChuFa(Double.parseDouble(weiGuiChuFa.toString()));
                staffSalary.setJiaoTongBuZhu(Double.parseDouble(jiaoTongBuZhu.toString()));
                staffSalary.setTongXinBuZhu(Double.parseDouble(tongXinBuZhu.toString()));
                map.put(staffSalary.getId(),staffSalary);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 读取五险一金申报表的数据,装入map集合里
     * @param sourceFileInputStream 输入流
     * @param map 数据存储的集合
     */
    public static void readExcel_WXYJ(InputStream sourceFileInputStream, Map<Integer,StaffSalary> map){
        try {
            //创建一个工作簿对象
            XSSFWorkbook workbook = new XSSFWorkbook(sourceFileInputStream);
            //获取第一个sheet
            XSSFSheet sheetAt = workbook.getSheetAt(0);
            //获取行迭代器
            Iterator<Row> rowIterator = sheetAt.rowIterator();
            rowIterator.hasNext();
            rowIterator.next();
            while (rowIterator.hasNext()){
                //获取一行信息
                Row next = rowIterator.next();
                Object jobNumber = getValue(next.getCell(0));
                //Object name = getValue(next.getCell(1));
                Object jishu = getValue(next.getCell(2));
                Object gongJiJinBiLie = getValue(next.getCell(3));
                StaffSalary staffSalary = map.get(Integer.parseInt(jobNumber.toString()));
                //五险一金计算
                staffSalary.setWuXianYiJin(Double.parseDouble(jishu.toString()),Double.parseDouble(gongJiJinBiLie.toString()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 存储数据
     * @param data 存储数据的集合
     * @param destFile 存储目标文件
     * @throws IOException
     */
    public static void fillExcel_Department(Map<Integer,StaffSalary> data, File destFile) throws IOException {

        destFile.createNewFile();

        //创建一个工作簿
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook();
        //在工作簿里创建一张表
        XSSFSheet sheet = xssfWorkbook.createSheet("用户");
        //
        XSSFRow row = sheet.createRow(0);
        row.createCell(0).setCellValue("工号");
        row.createCell(1).setCellValue("姓名");
        row.createCell(2).setCellValue("部门");
        row.createCell(3).setCellValue("工资");
        row.createCell(4).setCellValue("扣款");
        row.createCell(5).setCellValue("养老(个人)");
        row.createCell(6).setCellValue("医疗(个人)");
        row.createCell(7).setCellValue("失业(个人)");
        row.createCell(8).setCellValue("工伤(个人)");
        row.createCell(9).setCellValue("生育(个人)");
        row.createCell(10).setCellValue("公积金(个人)");
        row.createCell(11).setCellValue("合计(个人)");
        row.createCell(12).setCellValue("养老(公司)");
        row.createCell(13).setCellValue("医疗(公司)");
        row.createCell(14).setCellValue("失业(公司)");
        row.createCell(15).setCellValue("工伤(公司)");
        row.createCell(16).setCellValue("生育(公司)");
        row.createCell(17).setCellValue("公积金(公司)");
        row.createCell(18).setCellValue("合计(公司)");
        row.createCell(19).setCellValue("个税金额");
        row.createCell(20).setCellValue("应发工资");
        row.createCell(21).setCellValue("实发工资");
        row.createCell(22).setCellValue("企业支出成本");
        //行中添加数据
        Set<Integer> keys = data.keySet();
        int i=1;
        for(Integer key:keys){
            StaffSalary staffSalary = data.get(key);

            XSSFRow r = sheet.createRow(i++);
            //工号
            r.createCell(0).setCellValue(staffSalary.getId());
            //姓名
            r.createCell(1).setCellValue(staffSalary.getName());
            //部门
            r.createCell(2).setCellValue(staffSalary.getDepartment());
            //工资
            r.createCell(3).setCellValue(staffSalary.getGongzi());
            //扣款
            r.createCell(4).setCellValue(staffSalary.getKokuan());
            //养老(个人)
            r.createCell(5).setCellValue(staffSalary.getYangLaoBaoXianSelf());
            //医疗(个人)
            r.createCell(6).setCellValue(staffSalary.getYiLiaoBaoXianSelf());
            //失业(个人)
            r.createCell(7).setCellValue(staffSalary.getShiYeBaoXianSelf());
            //工伤(个人)
            r.createCell(8).setCellValue(staffSalary.getGongShangBaoXianSelf());
            //生育(个人)
            r.createCell(9).setCellValue(staffSalary.getShengYuBaoXianSelf());
            //公积金(个人)
            r.createCell(10).setCellValue(staffSalary.getGongJiJinSelf());
            //合计(个人)
            r.createCell(11).setCellValue(staffSalary.getSelf_WXYJ_All());
            //养老(公司)
            r.createCell(12).setCellValue(staffSalary.getYangLaoBaoXianCompany());
            //医疗(公司)
            r.createCell(13).setCellValue(staffSalary.getYiLiaoBaoXianCompany());
            //失业(公司)
            r.createCell(14).setCellValue(staffSalary.getShiYeBaoXianCompany());
            //工伤(公司)
            r.createCell(15).setCellValue(staffSalary.getGongShangBaoXianCompany());
            //生育(公司)
            r.createCell(16).setCellValue(staffSalary.getShengYuBaoXianCompany());
            //公积金(公司)
            r.createCell(17).setCellValue(staffSalary.getGongJiJinCompany());
            //合计(公司)
            r.createCell(18).setCellValue(staffSalary.getCompany_WXYJ_All());
            //个税金额
            r.createCell(19).setCellValue(staffSalary.getTax());
            //应发工资
            r.createCell(20).setCellValue(staffSalary.getYingFaGongZi());
            //实发工资
            r.createCell(21).setCellValue(staffSalary.getShiFaGongZi());
            //企业支出成本
            r.createCell(22).setCellValue(staffSalary.getCompany());
        }
        FileOutputStream fileOutputStream = new FileOutputStream(destFile);
        xssfWorkbook.write(fileOutputStream);
        fileOutputStream.close();
        xssfWorkbook.close();
    }


    /**
     * 解析单元格中的值
     * @param cell 单元格
     * @return 单元格内的值
     */
    private static Object getValue(Cell cell) {
        if (null == cell) {
            return null;
        }
        Object value = null;
        switch (cell.getCellType()) {
            case BOOLEAN:
                value = cell.getBooleanCellValue();
                break;
            case NUMERIC:
                // 日期类型，转换为日期
                if (DateUtil.isCellDateFormatted(cell)) {
                    value = cell.getDateCellValue();
                }
                // 数值类型
                else {

                    // 默认返回double，创建BigDecimal返回准确值
                    value = new BigDecimal(cell.getNumericCellValue());
                }
                break;
            default:
                value = cell.toString();
                break;
        }
        return value;
    }

    /**
     * 设置单元格值
     *
     * @param cell  单元格
     * @param value 值
     */
    private static void setValue(Cell cell, Object value) {
        if (null == cell) {
            return;
        }
        if (null == value) {
            cell.setCellValue((String) null);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        } else if (value instanceof Date) {
            cell.setCellValue(FORMAT.format((Date) value));
        } else if (value instanceof Double) {
            cell.setCellValue((Double) value);
        } else {
            cell.setCellValue(value.toString());
        }
    }
}
