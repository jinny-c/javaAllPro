package com.example.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Description excel工具类
 * @Author phb
 * @Date 2019/11/5 17:23
 * @Version V1.0
 */
@Slf4j
public class ExcelUtils {
    /***
     * 工作簿 2003版 excel
     * .xls
     */
    private static HSSFWorkbook hssfWorkbook;
    /**
     * 工作簿 2007及以上版 excel
     * .xlsx
     */
    private static XSSFWorkbook xssfWorkbook;
    /***
     * sheet
     */
    private static HSSFSheet sheet;
    /***
     * 标题行开始位置
     */
    private static final int TITLE_START_POSITION = 0;
    /***
     * 时间行开始位置
     */
    private static final int DATE_HEAD_START_POSITION = 1;
    /***
     * 表头行开始位置
     */
    private static final int HEAD_START_POSITION = 2;
    /***
     * 文本行开始位置
     */
    private static final int CONTENT_START_POSITION = 3;

    /**
     * 是否是2003的excel，返回true是2003
     *
     * @param fileName 文件名
     * @return boolean
     */
    public static boolean isExcel2003(String fileName) {
        return fileName.matches("^.+\\.(?i)(xls)$");
    }

    /**
     * 是否是2007的excel，返回true是2007
     *
     * @param fileName 文件名
     * @return boolean
     */
    public static boolean isExcel2007(String fileName) {
        return fileName.matches("^.+\\.(?i)(xlsx)$");
    }

    /***
     *
     * @param sheetName
     *        sheetName
     */
    private static void initHssfWorkbook(String sheetName) {
        hssfWorkbook = new HSSFWorkbook();
        sheet = hssfWorkbook.createSheet(sheetName);
    }

    /**
     * 生成标题（第零行创建）
     *
     * @param titleMap  对象属性名称->表头显示名称
     * @param sheetName sheet名称
     */
    private static void createTitleRow(Map<String, String> titleMap, String sheetName) {
        if (titleMap == null || titleMap.isEmpty()) {
            return;
        }
        CellRangeAddress titleRange = new CellRangeAddress(0, 0, 0, titleMap.size() - 1);
        sheet.addMergedRegion(titleRange);
        HSSFRow titleRow = sheet.createRow(TITLE_START_POSITION);
        HSSFCell titleCell = titleRow.createCell(0);
        titleCell.setCellValue(sheetName);
    }

    /**
     * 创建表头行（第二行创建）
     *
     * @param titleMap 对象属性名称->表头显示名称
     */
    private static void createHeadRow(Map<String, String> titleMap) {
        if (titleMap == null || titleMap.isEmpty()) {
            return;
        }
        // 第1行创建
        HSSFRow headRow = sheet.createRow(HEAD_START_POSITION);
        int i = 0;
        for (String entry : titleMap.keySet()) {
            HSSFCell headCell = headRow.createCell(i);
            headCell.setCellValue(titleMap.get(entry));
            i++;
        }
    }

    /**
     * @param dataList 对象数据集合
     * @param titleMap 表头信息
     */
    private static void createContentRow(List<?> dataList, Map<String, String> titleMap) {
        try {
            int i = 0;
            for (Object obj : dataList) {
                HSSFRow textRow = sheet.createRow(CONTENT_START_POSITION + i);
                int j = 0;
                for (String entry : titleMap.keySet()) {
                    String method = "get" + entry.substring(0, 1).toUpperCase() + entry.substring(1);
                    Method m = obj.getClass().getMethod(method);
                    Object invoke = m.invoke(obj);
                    HSSFCell textCell = textRow.createCell(j);
                    if (invoke == null) {
                        textCell.setCellValue("");
                    } else {
                        textCell.setCellValue(invoke.toString());
                    }
                    j++;
                }
                i++;
            }

        } catch (Exception e) {
            log.error("createContentRow异常，{}", e.getMessage());
        }
    }

    /**
     * 自动伸缩列（如非必要，请勿打开此方法，耗内存）
     *
     * @param size 列数
     */
    private static void autoSizeColumn(Integer size) {
        for (int j = 0; j < size; j++) {
            sheet.autoSizeColumn(j);
        }
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
//                cellValue = "非法字符";
//                break;
            default:
                throw new IllegalArgumentException("读取异常，未知类型");
//                cellValue = "未知类型";
//                break;
        }
        return cellValue;
    }

    /**
     * 读取2003版excel
     *
     * @param workbook    03版workbook
     * @param entityClass 实体类
     * @param fields      字段（<商户号,merchantNo>）
     * @param startRow    开始从第几行读取
     * @param head        标题行所在行数
     * @throws Exception 异常
     */
    public static <T> List<T> read2003Excel(HSSFWorkbook workbook, Class<T> entityClass, Map<String, String> fields, int startRow, int head) throws Exception {
        //读取excel返回结果
        List<T> resultList = new ArrayList<T>();
        // excel中字段的中英文名字数组
        String[] egTitles = new String[fields.size()];
        String[] cnTitles = new String[fields.size()];
        Iterator<String> it = fields.keySet().iterator();
        int count = 0;
        while (it.hasNext()) {
            String cnTitle = it.next();
            String egTitle = fields.get(cnTitle);
            egTitles[count] = egTitle;
            cnTitles[count] = cnTitle;
            count++;
        }
        // 得到excel中sheet总数
        int sheetCount = workbook.getNumberOfSheets();
        if (sheetCount == 0) {
            workbook.close();
            throw new Exception("Excel文件中没有任何数据");
        }
        // 数据的导出,第一个sheet
        HSSFSheet sheet = workbook.getSheetAt(0);
        if (sheet == null) {
            return resultList;
        }
        // 获取标题行，对标题行的特殊处理
        HSSFRow headRow = sheet.getRow(head);
        int cellLength = headRow.getLastCellNum();
        String[] excelFieldNames = new String[cellLength];
        LinkedHashMap<String, Integer> colMap = new LinkedHashMap<>();
        // 获取Excel中的列名
        for (int f = 0; f < cellLength; f++) {
            HSSFCell cell = headRow.getCell(f);
            excelFieldNames[f] = cell.getStringCellValue().trim();
            // 将列名和列号放入Map中,这样通过列名就可以拿到列号
            for (int g = 0; g < excelFieldNames.length; g++) {
                colMap.put(excelFieldNames[g], g);
            }
        }
        // 由于数组是根据长度创建的，所以值是空值，这里对列名map做了去空键的处理
        colMap.remove(null);
        // 判断需要的字段在Excel中是否都存在
        // 需要注意的是这个方法中的map中：中文名为键，英文名为值
        boolean isExist = true;
        List<String> excelFieldList = Arrays.asList(excelFieldNames);
        for (String cnName : fields.keySet()) {
            if (!excelFieldList.contains(cnName)) {
                isExist = false;
                break;
            }
        }
        // 如果有列名不存在，则抛出异常，提示错误
        if (!isExist) {
            workbook.close();
            throw new Exception("Excel中缺少必要的字段，或字段名称有误");
        }
        // 将sheet转换为list
        for (int j = startRow; j <= sheet.getLastRowNum(); j++) {
            HSSFRow row = sheet.getRow(j);
            // 根据泛型创建实体类
            T entity = entityClass.newInstance();
            // 给对象中的字段赋值
            for (Map.Entry<String, String> entry : fields.entrySet()) {
                // 获取中文字段名
                String cnNormalName = entry.getKey();
                // 获取英文字段名
                String enNormalName = entry.getValue();
                // 根据中文字段名获取列号
                int col = colMap.get(cnNormalName);
                // 获取当前单元格中的内容
                HSSFCell cell = row.getCell(col);
                if (null == cell) {
                    // 给对象赋值
                    setFieldValueByName(enNormalName, "", entity);
                    continue;
                }
                String content = getCellValue(row.getCell(col)).trim();
                // 给对象赋值
                setFieldValueByName(enNormalName, content, entity);
            }
            //判断属性是否都为null
            if (isAllFieldNull(entity)) {
                continue;
            }
            //重复的不添加
            if (!resultList.contains(entity)) {
                resultList.add(entity);
            }
        }
        workbook.close();
        log.info("读取excel的size={}", resultList.size());
        return resultList;
    }

    /**
     * 读取2007版excel
     *
     * @param workbook    07版workbook
     * @param entityClass 实体类
     * @param fields      字段（<商户号,merchantNo>）
     * @param startRow    开始从第几行读取
     * @param head        标题行所在行数
     * @throws Exception 异常
     */
    public static <T> List<T> read2007Excel(XSSFWorkbook workbook, Class<T> entityClass, Map<String, String> fields, int startRow, int head) throws Exception {
        //读取excel返回结果
        List<T> resultList = new ArrayList<T>();
        // excel中字段的中英文名字数组
        String[] egTitles = new String[fields.size()];
        String[] cnTitles = new String[fields.size()];
        Iterator<String> it = fields.keySet().iterator();
        int count = 0;
        while (it.hasNext()) {
            String cnTitle = it.next();
            String egTitle = fields.get(cnTitle);
            egTitles[count] = egTitle;
            cnTitles[count] = cnTitle;
            count++;
        }
        // 得到excel中sheet总数
        int sheetCount = workbook.getNumberOfSheets();
        if (sheetCount == 0) {
            workbook.close();
            throw new Exception("Excel文件中没有任何数据");
        }
        // 数据的导出,第一个sheet
        XSSFSheet sheet = workbook.getSheetAt(0);
        if (sheet == null) {
            return resultList;
        }
        // 获取标题行，对标题行的特殊处理
        XSSFRow headRow = sheet.getRow(head);
        int cellLength = headRow.getLastCellNum();
        String[] excelFieldNames = new String[cellLength];
        LinkedHashMap<String, Integer> colMap = new LinkedHashMap<>();
        // 获取Excel中的列名
        for (int f = 0; f < cellLength; f++) {
            XSSFCell cell = headRow.getCell(f);
            excelFieldNames[f] = cell.getStringCellValue().trim();
            // 将列名和列号放入Map中,这样通过列名就可以拿到列号
            for (int g = 0; g < excelFieldNames.length; g++) {
                colMap.put(excelFieldNames[g], g);
            }
        }
        // 由于数组是根据长度创建的，所以值是空值，这里对列名map做了去空键的处理
        colMap.remove(null);
        // 判断需要的字段在Excel中是否都存在
        // 需要注意的是这个方法中的map中：中文名为键，英文名为值
        boolean isExist = true;
        List<String> excelFieldList = Arrays.asList(excelFieldNames);
        for (String cnName : fields.keySet()) {
            if (!excelFieldList.contains(cnName)) {
                isExist = false;
                break;
            }
        }
        // 如果有列名不存在，则抛出异常，提示错误
        if (!isExist) {
            workbook.close();
            throw new Exception("Excel中缺少必要的字段，或字段名称有误");
        }
        // 将sheet转换为list
        for (int j = startRow; j <= sheet.getLastRowNum(); j++) {
            XSSFRow row = sheet.getRow(j);
            // 根据泛型创建实体类
            T entity = entityClass.newInstance();
            // 给对象中的字段赋值
            for (Map.Entry<String, String> entry : fields.entrySet()) {
                // 获取中文字段名
                String cnNormalName = entry.getKey();
                // 获取英文字段名
                String enNormalName = entry.getValue();
                // 根据中文字段名获取列号
                int col = colMap.get(cnNormalName);
                // 获取当前单元格中的内容
                XSSFCell cell = row.getCell(col);
                if (cell == null) {
                    // 给对象赋值
                    setFieldValueByName(enNormalName, "", entity);
                    continue;
                }
                String content = getCellValue(row.getCell(col)).trim();
                // 给对象赋值
                setFieldValueByName(enNormalName, content, entity);
            }
            //判断属性是否都为null
            if (isAllFieldNull(entity)) {
                continue;
            }
            //重复的不添加
            if (!resultList.contains(entity)) {
                resultList.add(entity);
            }
        }
        workbook.close();
        log.info("读取excel的有效size={}", resultList.size());
        return resultList;
    }

    /**
     * 读取所有 sheet
     *
     * @param workbook
     * @param start
     * @param title
     * @return
     * @throws Exception
     */
    public static Map<Integer, List<List<String>>> read2007Excel(XSSFWorkbook workbook, int start, int title) throws Exception {
        // 得到excel中sheet总数
        int sheetCount = workbook.getNumberOfSheets();
        if (sheetCount == 0) {
            throw new Exception("Excel文件中没有数据");
        }
        Map<Integer, List<List<String>>> resMap = new HashMap<>();
        for (int i = 1; i <= sheetCount; i++) {
            // 数据的导出,第一个sheet
            XSSFSheet excelSheet = workbook.getSheetAt(i);
            if (excelSheet == null) {
                resMap.put(i, null);
                continue;
            }
            resMap.put(i, oneSheetRead2007Excel(workbook, i, start, title));
        }
        return resMap;
    }

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
        if (sheetCount < sheet) {
            throw new Exception("Excel文件没有对应sheet");
        }
        // 数据的导出
        XSSFSheet excelSheet = workbook.getSheetAt(sheet - 1);
        if (excelSheet == null) {
            throw new Exception("Excel文件对应sheet中没有数据");
        }
        return read2007ExcelContentBySheet(excelSheet, start, cellLength);
    }

    private static List<List<String>> read2007ExcelTitleBySheet(XSSFSheet excelSheet, int title) throws Exception {
        //读取excel返回结果
        List<List<String>> resultList = new ArrayList<>();
        // 获取标题行，对标题行的特殊处理
        XSSFRow titleRow = excelSheet.getRow(title);
        int cellLength = titleRow.getLastCellNum();
        List<String> titleList = getRowCellValue(titleRow, cellLength);
        log.info("titleList={}", titleList);
        //把title行 放入list
        resultList.add(titleList);
        return resultList;
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
        log.info("读取excel的有效size={}", resultList.size() - 1);
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
     * 判断该对象是否: 返回true表示所有属性为null  返回false表示不是所有属性都是null
     * 排除序列化参数
     *
     * @param obj
     * @return
     * @throws Exception
     */
    private static boolean isAllFieldNull(Object obj) throws Exception {
        // 得到类对象
        Class stuCla = obj.getClass();
        //得到属性集合
        Field[] fs = stuCla.getDeclaredFields();
        boolean flag = true;
        for (Field f : fs) {
            f.setAccessible(true);
            if ("serialVersionUID".equalsIgnoreCase(f.getName())) {
                continue;
            }
            Object val = f.get(obj);
            if (val != null) {
                flag = false;
                break;
            }
        }
        return flag;
    }

}
