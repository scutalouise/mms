package com.agama.excel.poi;
public class Main {  
      
    public static void main(String[] args) throws Exception {  
        IRowReader reader = new RowReader();  
        //ExcelReaderUtil.readExcel(reader, "F://te03.xls");  
        ExcelReaderUtil.readExcel(reader, "F://test07.xlsx");  
    }  
}  