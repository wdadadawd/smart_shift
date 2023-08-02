package com.lsu.utils;


import com.lsu.entity.PassengerFlow;


import org.apache.poi.ss.usermodel.*;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


/**
 * @author zt
 * @create 2023-07-11 19:39
 * Excel工具类
 */
public class ExcelUtils {

    public static List<PassengerFlow> excelToShopIdList(InputStream inputStream,String suffix) {
        List<PassengerFlow> list = new ArrayList<>();
        Workbook workbook = null;
        try {
            // 1.根据传递过来的文件输入流创建一个workbook对象(对应Excel中的工作簿)
            workbook = WorkbookFactory.create(inputStream);
            inputStream.close();                            // 关闭输入流
            //2.获取第一张表
            Sheet sheet = workbook.getSheetAt(0);
            //3.获取工作表的总行数rowLength和总列数colLength
            int rowLength = sheet.getLastRowNum();
//            if (".xlsx".equals(suffix))
                rowLength++;
//            System.out.println(rowLength);
            Row row = sheet.getRow(0);                         //获取工作表第一行数据
            int colLength = row.getLastCellNum();
            for (int i=0;i<row.getLastCellNum();i++){           //去除空列名,获得真正的列数
                Cell cell = row.getCell(i);
                if (cell == null || cell.getCellType() == CellType.BLANK)
                    colLength--;
            }
            System.out.println(colLength);
            //4.遍历全部单元格,将每行保存为一个预测客流量对象
            Cell cell;                  // 创建一个单元格对象
            for (int a = 1; a < rowLength; a++) {           // 遍历表
                PassengerFlow passengerFlow = null;
                for (int b = 0; b < colLength; b++) {
                    Row nowRow = sheet.getRow(a);
                    if (nowRow==null)               //行不存在
                        continue;
                    cell = nowRow.getCell(b);    // 取出第a行b列的单元格数据
                    if (cell == null || cell.getCellType() == CellType.BLANK)     //判断单元格是否有值
                        continue;
                    if (passengerFlow == null)                   //第一次通过创建类型
                        passengerFlow = new PassengerFlow();
                    switch (b){                    //将数据保存到对象属性上
                        case 0: passengerFlow.setStoreId((int)cell.getNumericCellValue());break;
                        case 1: passengerFlow.setDate(cell.getDateCellValue());break;
                        case 2: passengerFlow.setStartTime(cell.getDateCellValue());break;
                        case 3: passengerFlow.setEndTime(cell.getDateCellValue());break;
                        case 4: passengerFlow.setCalculate(cell.getNumericCellValue());break;
                    }
                }
                System.out.println(passengerFlow);
                if (passengerFlow != null)
                    list.add(passengerFlow);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        //5.返回集合
//        System.out.println(list == null);
        return list;
    }
}
