package main.entry.webapp.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

import database.models.parking.ParkingArea;
import main.entry.webapp.BaseController;

@Controller
@RequestMapping(value = "/home/d")
public class ExcelController extends BaseController{
	
	private static final Logger logger = LoggerFactory.getLogger(ExcelController.class);

	private static List<ParkingArea> getArea() {
		List<ParkingArea> list = new ArrayList<ParkingArea>();
		ParkingArea parkingArea = new ParkingArea();
		parkingArea.setCreateTime(new Date());
		parkingArea.setDesc("描述");
		parkingArea.setName("名称");
		parkingArea.setShowStatus(1);
		parkingArea.setStatus(1);
		list.add(parkingArea);
		return list;
	}

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
			cell.setCellValue("名称");
			cell.setCellStyle(style);
			cell = row.createCell((short) 1);
			cell.setCellValue("描述");
			cell.setCellStyle(style);
			cell = row.createCell((short) 2);
			cell.setCellValue("创建时间");
			cell.setCellStyle(style);
			cell = row.createCell((short) 3);
	
			List<ParkingArea> list = getArea();
			for (int i = 0; i < list.size(); i++) {
				row = sheet.createRow((int) i + 1);
				ParkingArea parkingArea = list.get(i);
				row.createCell((short) 0).setCellValue(parkingArea.getName());
				row.createCell((short) 1).setCellValue(parkingArea.getDesc());
				row.createCell((short) 2).setCellValue(parkingArea.getCreateTime().toLocaleString());
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

	
}
