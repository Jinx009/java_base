package main.entry.webapp.data.project.home;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import common.helper.StringUtil;
import database.common.PageDataList;
import database.models.project.ProTask;
import database.models.project.ProTaskTitle;
import main.entry.webapp.BaseController;
import service.basicFunctions.project.ProTaskService;
import service.basicFunctions.project.ProTaskTitleService;
import utils.Resp;

@Controller
@RequestMapping(value = "/home/d/pro_task")
public class ProTaskHomeDataController extends BaseController {

	private static final Logger log = LoggerFactory.getLogger(ProTaskHomeDataController.class);

	@Autowired
	private ProTaskService proTaskService;
	@Autowired
	private ProTaskTitleService proTaskTitleService;

	@RequestMapping(path = "/list")
	@ResponseBody
	public Resp<?> list(Integer p, Integer status, String driverName, Integer taskTitleId) {
		Resp<?> resp = new Resp<>(false);
		try {
			PageDataList<ProTask> list = proTaskService.homeList(p, status, driverName, taskTitleId);
			return new Resp<>(list);
		} catch (Exception e) {
			log.error("error:{}", e);
		}
		return resp;
	}

	@RequestMapping(path = "/save")
	@ResponseBody
	public Resp<?> save(String noId, String name, String dep, String description, String flight, String pickTime,
			String driverName, String driverMobile, Integer taskTitleId, String dateStr) {
		Resp<?> resp = new Resp<>(false);
		try {
			proTaskService.save(noId, name, dep, description, flight, pickTime, driverName, driverMobile, taskTitleId,
					dateStr);
			return new Resp<>(true);
		} catch (Exception e) {
			log.error("error:{}", e);
		}
		return resp;
	}

	@RequestMapping(path = "/update")
	@ResponseBody
	public Resp<?> update(String mobilePhone, String dep, String description, String name, String number,
			String pickDate, String pickTime, String flight, String driverName, String driverMobile, Integer id) {
		Resp<?> resp = new Resp<>(false);
		try {
			proTaskService.update(mobilePhone, dep, description, name, number, pickDate, pickTime, flight, driverName,
					driverMobile, id);
			return new Resp<>(true);
		} catch (Exception e) {
			log.error("error:{}", e);
		}
		return resp;
	}

	@RequestMapping(value = "/uploadExcel")
	@ResponseBody
	public Resp<?> uploadFileHandler(@RequestParam("file") MultipartFile file, HttpServletRequest request,
			HttpServletResponse response) {
		Resp<?> resp = new Resp<>(false);
		resp.setMsg("文件不合法！");
		InputStream in = null;
		try {

			if (!file.isEmpty()) {
				String fileName = file.getOriginalFilename();
				String suffix = fileName.split("\\.")[1];
				if (suffix.indexOf("xls") < 0 && suffix.indexOf("xlsx") < 0) {
					return resp;
				}
				in = file.getInputStream();
				SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				String res = inputExcel(in, fileName.split("\\.")[0]+"_"+sd.format(new Date())+suffix);
				if (!"success".equals(res)) {
					resp.setMsg(res);
					return resp;
				}
				in.close();
				resp = new Resp<>(true);
				resp.setMsg("导入成功！");
			}
		} catch (Exception e) {
			log.error("upload error:{}", e);
		} finally {
			try {
				if (in != null) {
					in.close();
					in = null;
				}
			} catch (IOException e) {
				log.error("close error:{}", e);
			}
		}
		return resp;
	}

	private static Workbook getWorkbook(InputStream inStr) throws Exception {
		Workbook wb = null;
		wb = WorkbookFactory.create(inStr);
		return wb;
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
				value = cell.getStringCellValue();
				break;
			case Cell.CELL_TYPE_NUMERIC:
				if ("General".equals(cell.getCellStyle().getDataFormatString())) {
					value = df.format(cell.getNumericCellValue());
				} else if ("m/d/yy h:mm".equals(cell.getCellStyle().getDataFormatString())) {
					value = sdf.format(cell.getDateCellValue());
				} else if ("h:mm".equals(cell.getCellStyle().getDataFormatString())) {
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

	private String inputExcel(InputStream in, String fileName) {
		try {
			Workbook work = getWorkbook(in);

			if (null == work) {
				return "文件内容错误！";
			}
			Sheet sheet = work.getSheetAt(0);
			ProTaskTitle proTaskTitle = new ProTaskTitle();
			int currentStatus = 0;
			String currentDate = "";
			String noId = "1";
			for (int i = 1; i <= sheet.getLastRowNum(); i++) {
				try {
					Row row = sheet.getRow(i);
					if (i == 1) {
						Cell cell = row.getCell(1);
						proTaskTitle.setCreateTime(new Date());
						proTaskTitle.setName(fileName);
						if (cell != null && cell.getStringCellValue() != null) {
							proTaskTitle.setTitle(cell.getStringCellValue());
						}
						proTaskTitle = proTaskTitleService.save(proTaskTitle);
					} else {
						Cell cell = row.getCell(1);
						Cell cell2 = row.getCell(2);
						Cell cell3 = row.getCell(3);
						if (StringUtil.isBlank(getCellValue(cell))&& StringUtil.isNotBlank(getCellValue(cell2))&&StringUtil.isBlank(getCellValue(cell3))) {
							currentDate = getCellValue(cell2);
							currentStatus = 0;
						} else {
							if (currentStatus == 0) {
								currentStatus = 1;
								continue;
							} else {
								String cell_1 = getCellValue(row.getCell(1));
								String cell_2 = getCellValue(row.getCell(2));
								String cell_3 = getCellValue(row.getCell(3));
								String cell_4 = getCellValue(row.getCell(4));
								String cell_5 = getCellValue(row.getCell(5));
								String cell_6 = getCellValue(row.getCell(6));
								String cell_7 = getCellValue(row.getCell(7));
								String cell_8 = getCellValue(row.getCell(8));
								ProTask proTask = new ProTask();
								proTask.setCreateTime(new Date());
								proTask.setDateStr(currentDate);
								proTask.setStatus(0);
								proTask.setTaskTitleId(proTaskTitle.getId());
								proTask.setTaskTitle(proTaskTitle.getName());
								proTask.setDriverMobile("0");
								proTask.setDriverName("抢单模式");
								if (StringUtil.isNotBlank(cell_1)) {
									noId = cell_1;
								} else {
									cell_1 = noId;
								}
								proTask.setNoId(noId);
								proTask.setName(cell_2);
								proTask.setDep(cell_3);
								proTask.setDescription(cell_4);
								proTask.setFlight(cell_5);
								proTask.setPickTime(cell_6);
								proTask.setPickedTime(cell_7);
								proTask.setMailTime(cell_8);
								proTaskService.save(proTask);
							}
						}
					}
				} catch (Exception e) {
					log.error("input error:{}", e);
				}
			}
		} catch (Exception e) {
			log.error("erro:{}",e);
		}
//		System.out.println(sheet.getRow(2).getCell(2).getStringCellValue());

		return "success";
	}

	@SuppressWarnings("resource")
	@RequestMapping(value = "/excel")
	public void excel(HttpServletRequest request, HttpServletResponse response, Integer titleId) {
		ProTaskTitle taskTitle = proTaskTitleService.findById(titleId);
		List<ProTask> list = proTaskService.excelList(titleId);
		String fileName = taskTitle.getName();
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("Sheet1");
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		HSSFFont font = wb.createFont();
		font.setFontName("宋体");// 设置字体名称
		font.setFontHeightInPoints((short) 9);// 设置字号
		font.setColor(HSSFColor.BLACK.index);// 设置字体颜色
		style.setFont(font);
		sheet.setDefaultRowHeight((short) 280);
		sheet.setColumnWidth(0, 640);
		sheet.setColumnWidth(1, 1109);
		sheet.setColumnWidth(2, 7082);
		sheet.setColumnWidth(3, 3157);
		sheet.setColumnWidth(4, 4394);
		sheet.setColumnWidth(5, 1706);
		sheet.setColumnWidth(6, 3797);
		sheet.setColumnWidth(7, 3114);
		sheet.setColumnWidth(8, 4608);
		sheet.setColumnWidth(9, 554);
		sheet.setColumnWidth(10, 2901);
		HSSFRow row = null;
		HSSFCell cell = null;
		for(int i = 0;i<list.size()+20;i++){
			row = sheet.createRow(i);
			row.setRowStyle(style);
			for(int j = 0;j<=10;j++){
				cell = row.createCell(j);
				cell.setCellStyle(style);
			}
		}
		CellRangeAddress region = new CellRangeAddress(1, 1, 1, 8);
		sheet.addMergedRegion(region);
		//黑标题
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
		style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		font = wb.createFont();
		font.setFontName("Arial");// 设置字体名称
		font.setFontHeightInPoints((short) 11);// 设置字号
		font.setColor(HSSFColor.BLACK.index);// 设置字体颜色
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);//粗体
		style.setFont(font);
		style.setWrapText(true);
		style.setVerticalAlignment( HSSFCellStyle.VERTICAL_JUSTIFY);
		row = sheet.getRow(1);
		row.setHeight((short) 870);
		cell = row.getCell(1);
		cell.setCellValue(taskTitle.getTitle());
		
		//正文开始
		String num = null;
		int numStart = 0;
		String dateStr = null;
		int rowNum = 2;
		int status = 0;
		int start = 0;
		for(int j = 0;j<list.size();j++){
			rowNum = rowNum + j;
			if(status==0){
				row = sheet.createRow(rowNum);
				style = wb.createCellStyle();
				font = wb.createFont();
				font.setBoldweight(Font.BOLDWEIGHT_BOLD);//粗体
				style.setFont(font);
				cell = row.getCell(2);
				dateStr = list.get(j).getDateStr();
				cell.setCellValue(dateStr);
				cell.setCellStyle(style);
				num = list.get(j).getNoId();
				status = 1;
			}else if(status == 1){
				start = rowNum; //计算边框开始时间
				row = sheet.createRow(rowNum);
				style = wb.createCellStyle();
				font = wb.createFont();
				font.setBoldweight(Font.BOLDWEIGHT_BOLD);
				font.setFontName("Arial");
				font.setFontHeightInPoints((short) 11);
				style.setFont(font);
				for(int i = 0;i<9;i++){
					row.getCell(i).setCellStyle(style);
				}
				row.getCell(1).setCellValue("No.");
				row.getCell(2).setCellValue("Name");
				row.getCell(3).setCellValue("Dep");
				row.getCell(4).setCellValue("Descriptation");
				row.getCell(5).setCellValue("Flight");
				row.getCell(6).setCellValue("F/T,SHG(参考）");
				row.getCell(7).setCellValue("pick up time");
				row.getCell(8).setCellValue("mail time");
			}else{
				row = sheet.getRow(rowNum);
				ProTask proTask = list.get(j);
				if(num==null){
					num = proTask.getNoId();
				}
				cell = row.getCell(2);
				cell.setCellValue(proTask.getName());
				cell.setCellType(Cell.CELL_TYPE_STRING);
				cell = row.getCell(3);
				cell.setCellValue(proTask.getDep());
				cell.setCellType(Cell.CELL_TYPE_STRING);
				cell = row.getCell(4);
				cell.setCellValue(proTask.getDescription());
				cell.setCellType(Cell.CELL_TYPE_STRING);
				cell = row.getCell(5);
				cell.setCellValue(proTask.getFlight());
				cell.setCellType(Cell.CELL_TYPE_STRING);
				String pickTime = proTask.getPickTime();
				cell = row.getCell(6);
				cell.setCellValue(pickTime);
				cell.setCellType(Cell.CELL_TYPE_STRING);
				cell = row.getCell(7);
				cell.setCellValue(proTask.getPickedTime());
				cell.setCellType(Cell.CELL_TYPE_STRING);
				cell = row.getCell(8);
				cell.setCellValue(proTask.getPickedTime());
				cell.setCellType(Cell.CELL_TYPE_STRING);
			}
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
