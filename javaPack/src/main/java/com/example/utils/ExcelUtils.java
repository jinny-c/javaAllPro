package com.example.utils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description excel工具类
 * @Author phb
 * @Date 2019/11/5 17:23
 * @Version V1.0
 */
public class ExcelUtils {


    /**
     * 读取某一个 sheet
     *
     * @param workbook
     * @param sheet
     * @param start
     * @param cellLength
     * @return
     * @throws Exception
     */
    public static List<List<String>> oneSheetRead2007Excel(XSSFWorkbook workbook, int sheet, int start, int cellLength) throws Exception {
        // 得到excel中sheet总数
        int sheetCount = workbook.getNumberOfSheets();
        if (sheetCount <= sheet) {
            throw new Exception("Excel文件没有对应sheet");
        }
        // 数据的导出
        XSSFSheet excelSheet = workbook.getSheetAt(sheet - 1);
        if (excelSheet == null) {
            throw new Exception("Excel文件对应sheet中没有数据");
        }
        return read2007ExcelContentBySheet(excelSheet, start, cellLength);
    }

    private static List<List<String>> read2007ExcelContentBySheet(XSSFSheet excelSheet, int start, int cellLength) throws Exception {
        //读取excel返回结果
        List<List<String>> resultList = new ArrayList<>();
        int rowLength = excelSheet.getLastRowNum();
        // 将sheet转换为list
        for (int i = start; i <= rowLength; i++) {
            //一行
            XSSFRow row = excelSheet.getRow(i);
            //转成 list
            resultList.add(getRowCellValue(row, cellLength));
        }
        //减去最上面一行
//        log.info("读取excel的有效size={}", resultList.size() - 1);
        return resultList;
    }

    private static List<String> getRowCellValue(XSSFRow row, int cellLength) {
        List<String> rowValList = new ArrayList<>();
        ;
        for (int j = 0; j < cellLength; j++) {
            XSSFCell cell = row.getCell(j);
            if (cell == null) {
                rowValList.add("");
                continue;
            }
            String content = getCellValue(cell).trim();
            rowValList.add(content);
        }
        //return StringUtils.join(rowValList, IpsFileConstants.split_comma);
        return rowValList;
    }

    /**
     * 根据字段名给对象的字段赋值
     *
     * @param fieldName  字段名
     * @param fieldValue 字段值
     * @param o          对象
     */
    private static void setFieldValueByName(String fieldName, Object fieldValue, Object o) throws Exception {

        Field field = getFieldByName(fieldName, o.getClass());
        if (field != null) {
            field.setAccessible(true);
            // 获取字段类型
            Class<?> fieldType = field.getType();

            // 根据字段类型给字段赋值
            if (String.class == fieldType) {
                field.set(o, String.valueOf(fieldValue));
            } else if ((Integer.TYPE == fieldType) || (Integer.class == fieldType)) {
                field.set(o, Integer.parseInt(fieldValue.toString()));
            } else if ((Long.TYPE == fieldType) || (Long.class == fieldType)) {
                field.set(o, Long.valueOf(fieldValue.toString()));
            } else if ((Float.TYPE == fieldType) || (Float.class == fieldType)) {
                field.set(o, Float.valueOf(fieldValue.toString()));
            } else if ((Short.TYPE == fieldType) || (Short.class == fieldType)) {
                field.set(o, Short.valueOf(fieldValue.toString()));
            } else if ((Double.TYPE == fieldType) || (Double.class == fieldType)) {
                field.set(o, Double.valueOf(fieldValue.toString()));
            } else if (Character.TYPE == fieldType) {
                if ((fieldValue != null) && (fieldValue.toString().length() > 0)) {
                    field.set(o, fieldValue.toString().charAt(0));
                }
            } else if (Date.class == fieldType) {
                field.set(o, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(fieldValue.toString()));
            } else {
                field.set(o, fieldValue);
            }
        } else {
            throw new Exception(o.getClass().getSimpleName() + "类不存在字段名 " + fieldName);
        }
    }

    /**
     * 根据字段名获取字段
     *
     * @param fieldName 字段名
     * @param clazz     包含该字段的类
     * @return 字段
     */
    private static Field getFieldByName(String fieldName, Class<?> clazz) {
        // 拿到本类的所有字段
        Field[] selfFields = clazz.getDeclaredFields();
        // 如果本类中存在该字段，则返回
        for (Field field : selfFields) {
            if (field.getName().equals(fieldName)) {
                return field;
            }
        }
        // 否则，查看父类中是否存在此字段，如果有则返回
        Class<?> superClazz = clazz.getSuperclass();
        if (superClazz != null && superClazz != Object.class) {
            return getFieldByName(fieldName, superClazz);
        }
        // 如果本类和父类都没有，则返回空
        return null;
    }

    /**
     * 获取excel单元格的内容
     *
     * @param cell cell
     * @return String
     */
    public static String getCellValue(Cell cell) throws IllegalArgumentException {
        String cellValue;
        // 以下是判断数据的类型
        switch (cell.getCellType()) {
            case NUMERIC:
                // 数字
                if (DateUtil.isCellDateFormatted(cell)) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    cellValue = sdf.format(DateUtil.getJavaDate(cell.getNumericCellValue()));
                } else {
                    DecimalFormat df = new DecimalFormat("0");
                    cellValue = df.format(cell.getNumericCellValue());
                }
                break;
            case STRING:
                // 字符串
                cellValue = cell.getStringCellValue();
                break;
            case BOOLEAN:
                // Boolean
                cellValue = cell.getBooleanCellValue() + "";
                break;
            case FORMULA:
                // 公式
                cellValue = cell.getCellFormula() + "";
                break;
            case BLANK:
                // 空值
                cellValue = "";
                break;
            case ERROR:
                // 故障
                throw new IllegalArgumentException("读取异常，非法字符");
            default:
                throw new IllegalArgumentException("读取异常，未知类型");
        }
        return cellValue;
    }

}
