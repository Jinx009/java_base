package main.entry.webapp.data;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import main.entry.webapp.BaseController;

@Controller
@RequestMapping(value = "/home/d")
public class ExcelController extends BaseController{
	
	private static final Logger logger = LoggerFactory.getLogger(ExcelController.class);


	@SuppressWarnings("deprecation")
	@RequestMapping(path = "/excelTest")
	public void excelTest(HttpServletRequest req, HttpServletResponse res) {
		try {
			String fileName = "测试表";
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet = wb.createSheet("区域表");
			HSSFRow row = sheet.createRow((int) 0);
			HSSFCellStyle style = wb.createCellStyle();
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			HSSFCell cell = row.createCell((short) 0);
			cell.setCellValue("磁传感");
			cell.setCellStyle(style);
			cell = row.createCell((short) 1);
			cell.setCellValue("152");
			cell.setCellStyle(style);
			cell = row.createCell((short) 2);
			cell.setCellValue("2530");
			cell.setCellStyle(style);
			cell = row.createCell((short) 3);
			cell.setCellValue("联网");
			cell.setCellStyle(style);
			cell = row.createCell((short) 4);
			cell.setCellValue("信号");
			cell.setCellStyle(style);
			cell = row.createCell((short) 5);
			cell.setCellValue("电池");
			cell.setCellStyle(style);
	
			for (int i = 0; i < 1000; i++) {
				row = sheet.createRow((int) i + 1);
				row.createCell((short) 0).setCellValue("三轴OK");
				row.createCell((short) 1).setCellValue("已烧写");
				row.createCell((short) 2).setCellValue("已烧写");
				row.createCell((short) 3).setCellValue("OK");
				row.createCell((short) 4).setCellValue(getI());
				row.createCell((short) 5).setCellValue(getI2());
			}
			String excelName = new String(fileName.getBytes("utf-8"), "iso8859-1") + ".xls";
			res.setContentType("application/x-download");
			res.setCharacterEncoding("utf-8");
			res.setHeader("Content-Disposition","attachment;filename=" + excelName);
			ServletOutputStream out = res.getOutputStream();
			wb.write(out);
			out.flush();
			out.close();
		} catch (Exception e) {
			logger.error("test excel : {}",e);
		}
	}
	
	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws IOException {
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("区域表");
		HSSFRow row = sheet.createRow((int) 0);
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		HSSFCell cell = row.createCell((short) 0);
		cell.setCellValue("磁传感");
		cell.setCellStyle(style);
		cell = row.createCell((short) 1);
		cell.setCellValue("152");
		cell.setCellStyle(style);
		cell = row.createCell((short) 2);
		cell.setCellValue("2530");
		cell.setCellStyle(style);
		cell = row.createCell((short) 3);
		cell.setCellValue("联网");
		cell.setCellStyle(style);
		cell = row.createCell((short) 4);
		cell.setCellValue("信号");
		cell.setCellStyle(style);
		cell = row.createCell((short) 5);
		cell.setCellValue("电池");
		cell.setCellStyle(style);
		cell = row.createCell((short) 6);
		cell.setCellValue("序号");
		cell.setCellStyle(style);

		for (int i = 0; i < 1000; i++) {
			row = sheet.createRow((int) i + 1);
			row.createCell((short) 0).setCellValue("三轴OK");
			row.createCell((short) 1).setCellValue("已烧写");
			row.createCell((short) 2).setCellValue("已烧写");
			row.createCell((short) 3).setCellValue("OK");
			row.createCell((short) 4).setCellValue(getI());
			row.createCell((short) 5).setCellValue("3.5"+getI2()+"V");
			row.createCell((short) 6).setCellValue(String.valueOf(i+1));
		}
		FileOutputStream fout;
		try {
			fout = new FileOutputStream("/Users/jinx/Downloads/1.xlsx");
			wb.write(fout);
			fout.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	public static String getI(){
		int[] i = new int[]{25,26,27,28,29,30,31,32,33};
		int random=(int)(Math.random()*8);
		return String.valueOf(i[random]);
	}

	public static String getI2(){
		int[] i = new int[]{0,1,2,3,4,5,6,7,8,9};
		int random=(int)(Math.random()*9);
		return String.valueOf(i[random]);
	}
	
}
