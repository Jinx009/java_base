package main.entry.webapp.data.project.home;


import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import database.common.PageDataList;
import database.models.project.ProTask;
import main.entry.webapp.BaseController;
import service.basicFunctions.project.ProTaskService;
import utils.Resp;

@Controller
@RequestMapping(value = "/home/d/pro_task")
public class ProTaskHomeDataController extends BaseController{

	private static final Logger log = LoggerFactory.getLogger(ProTaskHomeDataController.class);
	
	@Autowired
	private ProTaskService proTaskService;
	
	@RequestMapping(path = "/list")
	@ResponseBody
	public Resp<?> list(Integer p,Integer status,String driverName,String fromDate,String endDate){
		Resp<?> resp = new Resp<>(false);
		try {
			PageDataList<ProTask> list = proTaskService.homeList(p, status, driverName, fromDate, endDate);
			return new Resp<>(list);
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}
	
	@RequestMapping(path = "/save")
	@ResponseBody
	public Resp<?> save(String mobilePhone,String dep,String description,String name,String number,
			String pickDate,String pickTime,String flight,String driverName,String driverMobile){
		Resp<?> resp = new Resp<>(false);
		try {
			proTaskService.save(mobilePhone,dep,description,name,number,pickDate,pickTime,flight,driverName,driverMobile);
			return new Resp<>(true);
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}
	
	@RequestMapping(path = "/update")
	@ResponseBody
	public Resp<?> update(String mobilePhone,String dep,String description,String name,String number,
			String pickDate,String pickTime,String flight,String driverName,String driverMobile,Integer id){
		Resp<?> resp = new Resp<>(false);
		try {
			proTaskService.update(mobilePhone,dep,description,name,number,pickDate,pickTime,flight,driverName,driverMobile,id);
			return new Resp<>(true);
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}
	
	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/excel")
	public void excel(HttpServletRequest request,HttpServletResponse response,Integer status,String driverName,String fromDate,String endDate){
		List<ProTask> tasks = proTaskService.excelList(status, driverName, fromDate, endDate);
		String fileName = fromDate+"_"+endDate;
		String sheetName = fromDate+"_"+endDate;
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet(sheetName);
		sheet.setColumnWidth(0, 256*20);
		sheet.setColumnWidth(1, 256*20);
		sheet.setColumnWidth(2, 256*40);
		sheet.setColumnWidth(3, 256*20);
		sheet.setColumnWidth(4, 256*20);
		sheet.setColumnWidth(5, 256*20);
		sheet.setColumnWidth(6, 256*20);
		sheet.setColumnWidth(7, 256*20);
		sheet.setColumnWidth(8, 256*20);
		sheet.setColumnWidth(9, 256*20);
		
		HSSFRow row = sheet.createRow((int) 0);
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);

		HSSFCellStyle dateFormatCellStyle = wb.createCellStyle();
		HSSFDataFormat format = wb.createDataFormat();
		dateFormatCellStyle.setDataFormat(format.getFormat("yyyy年MM月dd日  HH点mm分ss秒"));
		dateFormatCellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);

		HSSFCell cell = row.createCell((short) 0);
		cell.setCellValue("Name");
		cell.setCellStyle(style);
		cell = row.createCell((short) 1);
		cell.setCellValue("Number");
		cell.setCellStyle(style);
		cell = row.createCell((short) 2);
		cell.setCellValue("MobilePhone");
		cell.setCellStyle(style);
		cell = row.createCell((short) 3);
		cell.setCellValue("PickUpTime");
		cell.setCellStyle(style);
		cell = row.createCell((short) 4);
		cell.setCellValue("DriverName");
		cell.setCellStyle(style);
		cell = row.createCell((short) 5);
		cell.setCellValue("Flight");
		cell.setCellStyle(style);
		cell = row.createCell((short) 6);
		cell.setCellValue("Dep");
		cell.setCellStyle(style);
		cell = row.createCell((short) 7);
		cell.setCellValue("Description");
		cell.setCellStyle(style);
		cell = row.createCell((short) 8);
		cell.setCellValue("PickedTime");
		cell.setCellStyle(style);
		cell = row.createCell((short) 9);
		cell.setCellValue("Status");
		cell.setCellStyle(style);

		for (int i = 0; i < tasks.size(); i++) {
			row = sheet.createRow((int) i + 1);
			ProTask task = tasks.get(i);
			cell=row.createCell((short) 0);
			cell.setCellValue(task.getName());
			cell.setCellStyle(style);
			cell=row.createCell((short) 1);
			cell.setCellValue(task.getNumber());
			cell.setCellStyle(style);
			cell = row.createCell((short) 2);
			cell.setCellValue(task.getMobilePhone());
			cell.setCellStyle(dateFormatCellStyle);
			cell=row.createCell((short) 3);
			cell.setCellValue(task.getPickTime());
			cell.setCellStyle(style);
			cell=row.createCell((short) 4);
			cell.setCellValue(task.getDriverName());
			cell.setCellStyle(style);
			cell=row.createCell((short) 5);
			cell.setCellValue(task.getFlight());
			cell.setCellStyle(style);
			cell=row.createCell((short) 6);
			cell.setCellValue(task.getDep());
			cell.setCellStyle(style);
			cell=row.createCell((short) 7);
			cell.setCellValue(task.getDescription());
			cell.setCellStyle(style);
			cell=row.createCell((short) 8);
			cell.setCellValue(task.getPickedTime());
			cell.setCellStyle(style);
			cell=row.createCell((short) 9);
			if(1==task.getStatus()){
				cell.setCellValue("Picked");
			}else{
				cell.setCellValue("Picking");
			}
			cell.setCellStyle(style);
		}
		// 输出数据流
		try {
			response.setContentType("application/x-download");
			response.setCharacterEncoding("utf-8");
			response.setHeader("Content-Disposition",
					"attachment;filename=" + new String(fileName.getBytes("gbk"), "iso8859-1") + ".xls");
			ServletOutputStream out = response.getOutputStream();
			wb.write(out);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
