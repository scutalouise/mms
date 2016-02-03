package com.agama.pemm.service.impl;

import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.stereotype.Service;

import com.agama.pemm.domain.ThStatus;
import com.agama.pemm.domain.UpsStatus;
import com.agama.pemm.service.IExcelService;
import com.agama.tool.service.excel.JsGridReportBase;

@Service
public class ExcelServiceImpl implements IExcelService {

	@Override
	public void exportUpsStatus(String title, String[] headers,
			List<UpsStatus> dataset, String creator,
			HttpServletRequest request, HttpServletResponse response) {
		SimpleDateFormat formater = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分");
		String create_time = formater.format(new Date());

		// 声明一个工作薄

		HSSFWorkbook workbook = new HSSFWorkbook();

		JsGridReportBase jsGridReportBase = new JsGridReportBase();
		HashMap<String, HSSFCellStyle> styles = jsGridReportBase
				.initStyles(workbook);

		// 生成一个表格

		HSSFSheet sheet = workbook.createSheet(title);
		sheet.setDisplayGridlines(false);// 设置表标题是否有表格边框
		sheet.setDefaultColumnWidth(15);
		// 创建标题
		HSSFRow row = sheet.createRow(0);// 创建新行
		HSSFCell cell = row.createCell(0);// 创建新列

		cell.setCellValue(new HSSFRichTextString(title));
		HSSFCellStyle style = styles.get("TITLE");// 设置标题样式
		if (style != null) {
			cell.setCellStyle(style);
		}
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, headers.length - 1));// 合并标题行：起始行号，终止行号，
																					// 起始列号，终止列号
		// 创建副标题
		row = sheet.createRow(1);
		cell = row.createCell(0);
		cell.setCellValue(new HSSFRichTextString("创建人:"));
		style = styles.get("SUB_TITLE");
		if (style != null)
			cell.setCellStyle(style);

		cell = row.createCell(1);
		cell.setCellValue(new HSSFRichTextString(creator));
		style = styles.get("SUB_TITLE2");
		if (style != null) {
			cell.setCellStyle(style);
		}

		cell = row.createCell(2);
		cell.setCellValue(new HSSFRichTextString("创建时间:"));
		style = styles.get("SUB_TITLE");
		if (style != null) {
			cell.setCellStyle(style);
		}

		cell = row.createCell(3);
		style = styles.get("SUB_TITLE2");
		cell.setCellValue(new HSSFRichTextString(create_time));
		if (style != null) {
			cell.setCellStyle(style);
		}

		int rownum = 3;// 如果rownum = 1，则去掉创建人、创建时间等副标题；如果rownum = 0， 则把标题也去掉

		HSSFCellStyle headerstyle = styles.get("TABLE_HEADER");

		row = sheet.createRow(2);

		for (int i = 0; i < headers.length; i++) {

			cell = row.createCell(i);

			cell.setCellStyle(headerstyle);

			HSSFRichTextString text = new HSSFRichTextString(headers[i]);

			cell.setCellValue(text);

		}
		for (UpsStatus upsStatus:dataset) {
			 row = sheet.createRow(rownum);
			style = styles.get("STRING");
			if (rownum % 2 != 0) {
				style = styles.get("STRING_C");
				
			}
			cell = row.createCell(0);
			cell.setCellStyle(style);
			Integer communicationStatus=upsStatus.getCommunicationStatus();
			String value="";
			if(communicationStatus==0){
				value="正常";
			}else if(communicationStatus==1){
				value="UPS无响应";
			}else if(communicationStatus==2){
				value="UPS未识别";
			}
			cell.setCellValue(value);
			cell = row.createCell(1);
			cell.setCellStyle(style);
			cell.setCellValue(upsStatus.getBatteryVoltage()+"V");
			cell = row.createCell(2);
			cell.setCellStyle(style);
			String ups_State=upsStatus.getUpsStatus();
			if(ups_State.equals("0")){
				value= "正常";
			}else{
				value="异常";
			}
			cell.setCellValue(value);
			cell = row.createCell(3);
			cell.setCellStyle(style);
			cell.setCellValue(upsStatus.getFrequency()+"Hz");
			cell = row.createCell(4);
			cell.setCellStyle(style);
			cell.setCellValue(upsStatus.getInternalTemperature()+"℃");
			cell = row.createCell(5);
			cell.setCellStyle(style);
			cell.setCellValue((upsStatus.getBypassVoltage()!=null?upsStatus.getBypassVoltage():"0")+"V");
			cell = row.createCell(6);
			cell.setCellStyle(style);
			cell.setCellValue(upsStatus.getBypassFrequency());
			cell = row.createCell(7);
			cell.setCellStyle(style);
			cell.setCellValue(upsStatus.getInputVoltage());
			cell = row.createCell(8);
			cell.setCellStyle(style);
			cell.setCellValue(upsStatus.getOutputVoltage());
			cell = row.createCell(9);
			cell.setCellStyle(style);
			cell.setCellValue(upsStatus.getErrorVoltage()+"V");
			cell = row.createCell(10);
			cell.setCellStyle(style);
			cell.setCellValue(upsStatus.getUpsLoad());
			cell = row.createCell(11);
			cell.setCellStyle(style);
			cell.setCellValue(upsStatus.getOutputFrenquency()+"Hz");
			cell = row.createCell(12);
			cell.setCellStyle(style);
			cell.setCellValue(upsStatus.getSingleVoltage()+"V");
			cell = row.createCell(13);
			cell.setCellStyle(style);
			cell.setCellValue(upsStatus.getElectricQuantity()+"%");
			cell = row.createCell(14);
			cell.setCellStyle(style);
			Date date = upsStatus.getCollectTime();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

           

			cell.setCellValue(sdf.format(date));
			rownum++;
		}
		String sFileName = title + ".xls";
		try {
			response.setHeader("Content-Disposition", "attachment;filename="
					.concat(String.valueOf(URLEncoder
							.encode(sFileName, "UTF-8"))));
			response.setHeader("Connection", "close");
			response.setHeader("Content-Type", "application/vnd.ms-excel");

			workbook.write(response.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void exportThStatus(String title, String[] headers,
			List<ThStatus> dataset, String creator, HttpServletRequest request,
			HttpServletResponse response) {
		SimpleDateFormat formater = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分");
		String create_time = formater.format(new Date());

		// 声明一个工作薄

		HSSFWorkbook workbook = new HSSFWorkbook();

		JsGridReportBase jsGridReportBase = new JsGridReportBase();
		HashMap<String, HSSFCellStyle> styles = jsGridReportBase
				.initStyles(workbook);

		// 生成一个表格

		HSSFSheet sheet = workbook.createSheet(title);
		sheet.setDisplayGridlines(false);// 设置表标题是否有表格边框
		sheet.setDefaultColumnWidth(15);
		// 创建标题
		HSSFRow row = sheet.createRow(0);// 创建新行
		HSSFCell cell = row.createCell(0);// 创建新列

		cell.setCellValue(new HSSFRichTextString(title));
		HSSFCellStyle style = styles.get("TITLE");// 设置标题样式
		if (style != null) {
			cell.setCellStyle(style);
		}
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, headers.length - 1));// 合并标题行：起始行号，终止行号，
																					// 起始列号，终止列号
		// 创建副标题
		row = sheet.createRow(1);
		cell = row.createCell(0);
		cell.setCellValue(new HSSFRichTextString("创建人:"));
		style = styles.get("SUB_TITLE");
		if (style != null)
			cell.setCellStyle(style);

		cell = row.createCell(1);
		cell.setCellValue(new HSSFRichTextString(creator));
		style = styles.get("SUB_TITLE2");
		if (style != null) {
			cell.setCellStyle(style);
		}

		cell = row.createCell(2);
		cell.setCellValue(new HSSFRichTextString("创建时间:"));
		style = styles.get("SUB_TITLE");
		if (style != null) {
			cell.setCellStyle(style);
		}

		cell = row.createCell(3);
		style = styles.get("SUB_TITLE2");
		cell.setCellValue(new HSSFRichTextString(create_time));
		if (style != null) {
			cell.setCellStyle(style);
		}

		int rownum = 3;// 如果rownum = 1，则去掉创建人、创建时间等副标题；如果rownum = 0， 则把标题也去掉

		HSSFCellStyle headerstyle = styles.get("TABLE_HEADER");

		row = sheet.createRow(2);

		for (int i = 0; i < headers.length; i++) {

			cell = row.createCell(i);

			cell.setCellStyle(headerstyle);

			HSSFRichTextString text = new HSSFRichTextString(headers[i]);

			cell.setCellValue(text);

		}
		for (ThStatus thStatus:dataset) {
			 row = sheet.createRow(rownum);
			style = styles.get("STRING");
			if (rownum % 2 != 0) {
				style = styles.get("STRING_C");
				
			}
			cell = row.createCell(0);
			cell.setCellStyle(style);
		
			
			
			
			cell = row.createCell(0);
			cell.setCellStyle(style);
			cell.setCellValue(thStatus.getTemperature()+"℃");
			cell = row.createCell(1);
			cell.setCellStyle(style);
			cell.setCellValue(thStatus.getHumidity()+"%");
			cell = row.createCell(2);
			cell.setCellStyle(style);
			Date date = thStatus.getCollectTime();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

           

			cell.setCellValue(sdf.format(date));
			rownum++;
		}
		String sFileName = title + ".xls";
		try {
			response.setHeader("Content-Disposition", "attachment;filename="
					.concat(String.valueOf(URLEncoder
							.encode(sFileName, "UTF-8"))));
			response.setHeader("Connection", "close");
			response.setHeader("Content-Type", "application/vnd.ms-excel");

			workbook.write(response.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
