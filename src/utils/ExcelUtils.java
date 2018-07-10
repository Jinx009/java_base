package utils;

//import java.io.FileOutputStream;
//import java.util.ArrayList;
//import java.util.List;
//
//import org.apache.poi.hssf.usermodel.HSSFCell;
//import org.apache.poi.hssf.usermodel.HSSFCellStyle;
//import org.apache.poi.hssf.usermodel.HSSFRow;
//import org.apache.poi.hssf.usermodel.HSSFSheet;
//import org.apache.poi.hssf.usermodel.HSSFWorkbook;


public class ExcelUtils {

//	@SuppressWarnings("deprecation")
//	public static void main(String[] args) throws Exception {
//
//		HSSFWorkbook wb = new HSSFWorkbook();
//		HSSFSheet sheet = wb.createSheet("导师表一");
//		HSSFRow row = sheet.createRow((int) 0);
//		HSSFCellStyle style = wb.createCellStyle();
//
//		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
//
//		HSSFCell cell = row.createCell((short) 0);
//		cell.setCellValue("姓名");
//		cell.setCellStyle(style);
//		cell = row.createCell((short) 1);
//		cell.setCellValue("银行名称");
//		cell.setCellStyle(style);
//		cell = row.createCell((short) 2);
//
//		List<User> list = getTutor();
//
//		for (int i = 0; i < list.size(); i++) {
//			row = sheet.createRow((int) i + 1);
//			User tutor = list.get(i);
//			row.createCell((short) 0).setCellValue(tutor.getRealName());
//			row.createCell((short) 1).setCellValue("");
//		}
//		try {
//			FileOutputStream fout = new FileOutputStream("E:/tutor.xls");
//			wb.write(fout);
//			fout.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

}
