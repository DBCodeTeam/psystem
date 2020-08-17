package com.um.psystem.utils;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.um.psystem.authorization.UserAuthRealm;
import com.um.psystem.entity.ExcelHeader;

import java.io.*;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.BaseRowModel;
import com.alibaba.excel.metadata.Sheet;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;



/**
 * @Author: zzj
 * @Description: easyexcel工具类
 * @Date: 2020/6/3
 */
public class EasyExcelUtils {
    private static final Logger log = LoggerFactory.getLogger(EasyExcelUtils.class);
    private static Sheet initSheet;

    static {
        initSheet = new Sheet(1, 0);
        initSheet.setSheetName("sheet");
        //设置自适应宽度
        initSheet.setAutoWidth(Boolean.TRUE);
    }
    public static OutputStream exportDataToExcel(List<ExcelHeader> headerList, List<Map<String, Object>> dataList){
        BigDecimal total = new BigDecimal("0");
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        WriteSheet sheet = new WriteSheet();
        sheet.setSheetNo(0);
        sheet.setSheetName("");
        List<List<Object>> rows = new ArrayList<>();
        //表头
        List<List<String>> headList = new ArrayList<>();
        if (headerList != null) {
            for (int j = 0; j < headerList.size(); j++) {
                List<String> columnHeader = new ArrayList<>();
                columnHeader.add(headerList.get(j).getHeaderName());
                headList.add(columnHeader);
            }
            sheet.setHead(headList);
            //表体
            if (dataList != null) {
                Iterator<Map<String, Object>> listIterator = dataList.listIterator();
                //记录前一分组完成后的到达位置
                //i = 0;//重置本表行数记录
                while (listIterator.hasNext()) {
                    Map<String, Object> record = listIterator.next();
                    //行
                    List<Object> row = new ArrayList<>();
                    for (int j = 0; j < headerList.size(); j++) {
                        Object cellValue = record.get(headerList.get(j).getHeaderField());
                        String cellStrValue = cellValue == null ? "" : cellValue.toString();//将null转为空字符串
                        if (headerList.get(j).getDataType() == ExcelHeader.NUMBER) {
                            cellStrValue = StringUtils.isEmpty(cellStrValue) ? "0" : cellStrValue;
                            BigDecimal itemNum = new BigDecimal(cellStrValue);
                            if (headerList.get(j).isCountTotal())
                                total = total.add(itemNum);
                            itemNum.setScale(2, BigDecimal.ROUND_HALF_UP);
                            row.add(itemNum.doubleValue());
                            //cell.setCellValue(decimalFormat.format(itemNum));
                        } else if (headerList.get(j).getDataType() == ExcelHeader.DATE) {
                            if (cellValue == null) {
                                row.add("");
                            } else {
                                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                cellStrValue = dateFormat.format(cellValue);
                                row.add(cellStrValue + "");
                            }
                        } else {
                            row.add(cellStrValue);
                        }
                    }
                    rows.add(row);
                    listIterator.remove();
                }
            }
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ExcelWriterBuilder excelWriterBuilder = EasyExcelFactory.write(out);
        ExcelWriter excelWriter = null;
        excelWriter = excelWriterBuilder.build();
        excelWriter.write(rows, sheet);
        excelWriter.finish();
        return out;
    }


    /**
     * 读取少于1000行数据
     * @param filePath 文件绝对路径
     * @return
     */
    public static List<Map<String,Object>> readLessThan1000Row(String filePath){
        return readLessThan1000RowBySheet(filePath,null);
    }

    /**
     * 读小于1000行数据, 带样式
     * filePath 文件绝对路径
     * initSheet ：
     *      sheetNo: sheet页码，默认为1
     *      headLineMun: 从第几行开始读取数据，默认为0, 表示从第一行开始读取
     *      clazz: 返回数据List<Object> 中Object的类名
     */
    public static List<Map<String,Object>> readLessThan1000RowBySheet(String filePath, Sheet sheet){
        if(!StringUtils.hasText(filePath)){
            return null;
        }

        sheet = sheet != null ? sheet : initSheet;

        InputStream fileStream = null;
        try {
            fileStream = new FileInputStream(filePath);
            return null;
            //return EasyExcelFactory.read(fileStream, sheet);
        } catch (FileNotFoundException e) {
            log.info("找不到文件或文件路径错误, 文件：{}", filePath);
        }finally {
            try {
                if(fileStream != null){
                    fileStream.close();
                }
            } catch (IOException e) {
                log.info("excel文件读取失败, 失败原因：{}", e);
            }
        }
        return null;
    }

    /**
     * 读大于1000行数据
     * @param filePath 文件觉得路径
     * @return
     */
    public static List<Object> readMoreThan1000Row(String filePath){
        return readMoreThan1000RowBySheet(filePath,null);
    }

    /**
     * 读大于1000行数据, 带样式
     * @param filePath 文件觉得路径
     * @return
     */
    public static List<Object> readMoreThan1000RowBySheet(String filePath, Sheet sheet){
        if(!StringUtils.hasText(filePath)){
            return null;
        }

        sheet = sheet != null ? sheet : initSheet;

        InputStream fileStream = null;
        try {
            fileStream = new FileInputStream(filePath);
            ExcelListener excelListener = new ExcelListener(null);
            EasyExcelFactory.readBySax(fileStream, sheet, excelListener);
            return excelListener.getDatas();
        } catch (FileNotFoundException e) {
            log.error("找不到文件或文件路径错误, 文件：{}", filePath);
        }finally {
            try {
                if(fileStream != null){
                    fileStream.close();
                }
            } catch (IOException e) {
                log.error("excel文件读取失败, 失败原因：{}", e);
            }
        }
        return null;
    }

    /**
     * 生成excle
     * @param filePath  绝对路径, 如：/home/chenmingjian/Downloads/aaa.xlsx
     * @param data 数据源
     * @param head 表头
     */
    public static void writeBySimple(String filePath, List<List<Object>> data, List<String> head){
        writeSimpleBySheet(filePath,data,head,null);
    }

    /**
     * 生成excle
     * @param filePath 绝对路径, 如：/home/chenmingjian/Downloads/aaa.xlsx
     * @param data 数据源
     * @param sheet excle页面样式
     * @param head 表头
     */
    public static void writeSimpleBySheet(String filePath, List<List<Object>> data, List<String> head, Sheet sheet){
        sheet = (sheet != null) ? sheet : initSheet;

        if(head != null){
            List<List<String>> list = new ArrayList<>();
            head.forEach(h -> list.add(Collections.singletonList(h)));
            sheet.setHead(list);
        }

        OutputStream outputStream = null;
        ExcelWriter writer = null;
        try {
            outputStream = new FileOutputStream(filePath);
            writer = EasyExcelFactory.getWriter(outputStream);
            writer.write1(data,sheet);
        } catch (FileNotFoundException e) {
            log.error("找不到文件或文件路径错误, 文件：{}", filePath);
        }finally {
            try {
                if(writer != null){
                    writer.finish();
                }

                if(outputStream != null){
                    outputStream.close();
                }

            } catch (IOException e) {
                log.error("excel文件导出失败, 失败原因：{}", e);
            }
        }

    }


    /*********************匿名内部类开始，可以提取出去******************************/

    /*@Data
    public static class MultipleSheelPropety{

        private List<? extends BaseRowModel> data;

        private Sheet sheet;
    }*/

    /**
     * 解析监听器，
     * 每解析一行会回调invoke()方法。
     * 整个excel解析结束会执行doAfterAllAnalysed()方法
     *
     * @author: zzj
     * @date: 2020-06-05
     */
    @Getter
    @Setter
    public static class ExcelListener extends AnalysisEventListener {

        private List<Object> datas = new ArrayList<>();
        private List<Map<String,Object>> data_excel = new ArrayList<>();
        private List<String> list_field = new ArrayList<>();
        public ExcelListener(List<String> list_field){
            this.list_field = list_field;
        }

        /**
         * 逐行解析
         * object : 当前行的数据
         */
        @Override
        public void invoke(Object object, AnalysisContext context) {
            //当前行
            // context.getCurrentRowNum()
            if (object != null) {
                Map<String,Object> modelMap = new HashMap<>();
                for(int i=0;i<list_field.size();i++){
                    String key = list_field.get(i);
                    List list_value = (List)object;
                    modelMap.put(key,list_value.get(i));
                }
                data_excel.add(modelMap);
                datas.add(object);
            }
        }


        /**
         * 解析完所有数据后会调用该方法
         */
        @Override
        public void doAfterAllAnalysed(AnalysisContext context) {
            //解析结束销毁不用的资源
        }

    }

    /************************匿名内部类结束，可以提取出去***************************/


}




