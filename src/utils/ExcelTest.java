package utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;

import common.helper.StringUtil;

public class ExcelTest {

	public static void main(String[] args) throws Exception {
		// excel();
		putIn();
	}

	public static void putIn() throws Exception {
		String filePath = "/Users/jinx/Downloads/NCL_TRANSPORTATION_222.xlsx";// 文件路径
		InputStream in = new FileInputStream(filePath);
		Workbook work = getWorkbook(in);
		if (null == work) {
			throw new Exception("xx");
		}
		Sheet sheet = work.getSheetAt(0);
		System.out.println(sheet.getRow(84).getCell(7).getCellStyle().getDataFormatString()+"_"+sheet.getRow(84).getCell(7).getCellType()+"_"+getCellValue(sheet.getRow(84).getCell(7)));
		System.out.println(getCellValue(sheet.getRow(5).getCell(1)));
//		for(int i = 0;i<sheet.getLastRowNum();i++){
//			
//		}
	}
	
	private static String getCellValue(Cell cell) {
		if (cell == null) {
			return "";
		} else {
			String value = null;
			DecimalFormat df = new DecimalFormat("0"); // 格式化number String字符
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm"); // 日期格式化
			SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm"); // 日期格式化
			DecimalFormat df2 = new DecimalFormat("0.00"); // 格式化数字

			switch (cell.getCellType()) {
			case Cell.CELL_TYPE_STRING:
				value = cell.getRichStringCellValue().getString();
				break;
			case Cell.CELL_TYPE_NUMERIC:
				if ("General".equals(cell.getCellStyle().getDataFormatString())) {
					value = df.format(cell.getNumericCellValue());
				} else if ("m/d/yy h:mm".equals(cell.getCellStyle().getDataFormatString())) {
					value = sdf.format(cell.getDateCellValue());
				}else if ("h:mm".equals(cell.getCellStyle().getDataFormatString())) {
					value = sdf2.format(cell.getDateCellValue());
				} else {
					value = df2.format(cell.getNumericCellValue());
				}
				break;
			case Cell.CELL_TYPE_BOOLEAN:
				value = String.valueOf(cell.getBooleanCellValue());
				break;
			case Cell.CELL_TYPE_BLANK:
				value = "";
				break;
			default:
				break;
			}
			return value;
		}

	}

	public static Workbook getWorkbook(InputStream inStr) throws Exception {
		Workbook wb = null;
		wb = WorkbookFactory.create(inStr);
		return wb;
	}

	@SuppressWarnings("resource")
	public static void excel() throws Exception {
		String filePath = "/Users/jinx/Downloads/NCL TRANSPORTATION.xls";// 文件路径
		FileOutputStream out = new FileOutputStream(filePath);

		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("NCL TRANSPORTATION");
		HSSFRow row = null;
		HSSFCell cell = null;
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		sheet.setColumnWidth(0, 256 * 20);
		sheet.setColumnWidth(1, 256 * 20);
		sheet.setColumnWidth(2, 256 * 40);
		sheet.setColumnWidth(3, 256 * 20);
		sheet.setColumnWidth(4, 256 * 20);
		sheet.setColumnWidth(5, 256 * 20);
		sheet.setColumnWidth(6, 256 * 20);
		sheet.setColumnWidth(7, 256 * 20);
		sheet.setColumnWidth(8, 256 * 20);
		sheet.setColumnWidth(9, 256 * 20);
		for (int i = 0; i < 100; i++) {
			row = sheet.createRow(i);
			cell = row.createCell(0);
			cell.setCellValue("");
			cell.setCellStyle(style);
			cell = row.createCell(1);
			cell.setCellValue("");
			cell.setCellStyle(style);
			cell = row.createCell(2);
			cell.setCellValue("");
			cell.setCellStyle(style);
			cell = row.createCell(3);
			cell.setCellValue("");
			cell.setCellStyle(style);
			cell = row.createCell(4);
			cell.setCellValue("L");
			cell.setCellStyle(style);
			cell = row.createCell(5);
			cell.setCellValue("");
			cell.setCellStyle(style);
			cell = row.createCell(6);
			cell.setCellValue("");
			cell.setCellStyle(style);
			cell = row.createCell(7);
			cell.setCellValue("");
			cell.setCellStyle(style);
			cell = row.createCell(8);
			cell.setCellValue("");
			cell.setCellStyle(style);
			cell = row.createCell(9);
			cell.setCellValue("");
			cell.setCellStyle(style);
		}

		row = sheet.getRow(1);
		row.setHeightInPoints(60);
		cell = row.getCell(1);
		cell.setCellValue("M/V:  NORWEGIAN JOY       靠港： 吴淞邮轮码头       EM CREW MT LIST FROM :2018-07-06 15:25\n"
				+ "                DEM CREW MT LIST FROM :2018-07-06 14:58\n" + "2018.7.9");
		for (int i = 1; i < 9; i++) {
			cell = row.getCell(i);
			style = wb.createCellStyle();
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			style.setBorderBottom((short) 2);
			style.setBorderLeft((short) 2);
			style.setBorderTop((short) 2);
			style.setBorderRight((short) 2);
			style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
			cell.setCellStyle(style);
		}
		CellRangeAddress region = new CellRangeAddress(1, 1, 1, 8);
		sheet.addMergedRegion(region);
		HSSFFont font = wb.createFont();
		font.setFontName("Arial");// 设置字体名称
		font.setFontHeightInPoints((short) 11);// 设置字号
		font.setColor(HSSFColor.BLACK.index);// 设置字体颜色
		style.setFont(font);
		cell.setCellStyle(style);

		row = sheet.getRow(2);
		cell = row.getCell(2);
		cell.setCellValue("2018.07.08");

		wb.write(out);
		out.close();
	}

}
