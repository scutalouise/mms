package com.agama.excel.poi2;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
/**
 * POI-Excel手动输入数据导出
 * @author XX帅
 * @修改日期 2014-8-19下午2:20:05
 */
public class CreateSimpleExcelToDisk {
    /**
     * 手动创建的数据
     * @return
     * @throws Exception
     */
    public static List<Student> getStudents () throws Exception{
        List<Student> list = new ArrayList<Student>();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        //Student student = new Student(id, age, name, days);
        Student student1 = new Student(1, 18, "tom", format.parse("2014-08-19"));
        Student student2 = new Student(2, 19, "tom", format.parse("2014-08-20"));
        Student student3 = new Student(3, 20, "tom", format.parse("2014-08-21"));
        list.add(student1);
        list.add(student2);
        list.add(student3);
        return list;
    }
    public static void main(String[] args) throws Exception {
        // 第一步，创建一个workbook，对应一个Excel文件
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet hssfSheet = workbook.createSheet("sheet1");
        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
        HSSFRow hssfRow = hssfSheet.createRow(0);
        // 第四步，创建单元格，并设置值表头 设置表头居中
        HSSFCellStyle hssfCellStyle = workbook.createCellStyle();
        //居中样式
        hssfCellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        HSSFFont font = workbook.createFont();
        font.setColor(HSSFColor.VIOLET.index);
        font.setFontHeightInPoints((short) 16);
        hssfCellStyle.setFont(font);
         
        HSSFCell hssfCell = hssfRow.createCell(0);//列索引从0开始
        hssfCell.setCellValue("学号");//列名1
        hssfCell.setCellStyle(hssfCellStyle);//列居中显示
        hssfCell = hssfRow.createCell(1);
        hssfCell.setCellValue("年龄");//列名2
        hssfCell.setCellStyle(hssfCellStyle);
        hssfCell = hssfRow.createCell(2);
        hssfCell.setCellValue("姓名");//列名3
        hssfCell.setCellStyle(hssfCellStyle);
        hssfCell = hssfRow.createCell(3);
        hssfCell.setCellValue("生日");//列名4
        hssfCell.setCellStyle(hssfCellStyle);
         
        // 第五步，写入实体数据 实际应用中这些数据从数据库得到，
        List<Student> list = CreateSimpleExcelToDisk.getStudents();
        for (int i = 0; i < list.size(); i++) {
            hssfRow = hssfSheet.createRow(i+1);
            Student student = list.get(i);
            // 第六步，创建单元格，并设置值
            hssfRow.createCell(0).setCellValue(student.getId());
            hssfRow.createCell(1).setCellValue(student.getAge());
            hssfRow.createCell(2).setCellValue(student.getName());
            hssfRow.createCell(3).setCellValue(new SimpleDateFormat("yyyy-MM-dd").format(student.getDays()));
        }
        // 第七步，将文件存到指定位置
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("D:/student.xls");//指定路径与名字和格式
            workbook.write(fileOutputStream);//讲数据写出去
            fileOutputStream.close();//关闭输出流
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}