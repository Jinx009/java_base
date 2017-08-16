package main.entry.webapp.data;

import java.io.IOException;
import java.util.Date;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

import database.models.active.Active;
import database.models.active.ActiveUser;
import service.basicFunctions.active.ActiveService;
import service.basicFunctions.active.ActiveUserService;
import utils.Resp;
import utils.RespData;

@Controller
@RequestMapping(value = "/back/d")
public class HomeDataController {

	private static final Logger logger = LoggerFactory.getLogger(HomeDataController.class);
	
	@Autowired
	private ActiveUserService activeUserService;
	@Autowired
	private ActiveService activeService;
	
	@RequestMapping(path = "/saveActive")
	@ResponseBody
	public Resp<?> saveUser(@RequestParam(value = "name",required = false)String name,
						    @RequestParam(value = "keyword",required = false)String keyword,
						    @RequestParam(value = "desc",required = false)String desc){
		Resp<?> resp = new Resp<>(false);
		try {
			Active active = activeService.getByKey(keyword);
			if(active!=null){
				resp.setMsg("该链接参数已经存在！");
			}else{
				active = new Active();
				active.setCreateTime(new Date());
				active.setDesc(desc);
				active.setKeyword(keyword);
				active.setName(name);
				activeService.save(active);
				resp = new Resp<>(RespData.OK_CODE,RespData.OK_MSG,"");
				return resp;
			}
		} catch (Exception e) {
			logger.error("save error :{}",e);
		}
		
		return resp;
	}
	
	@RequestMapping(path = "/activeList")
	@ResponseBody
	public Resp<?> allActive(){
		Resp<?> resp = new Resp<>(false);
		try {
			List<Active> list = activeService.findAll();
			resp = new Resp<>(RespData.OK_CODE,RespData.OK_MSG,list);
			return resp;
		} catch (Exception e) {
			logger.error("save error :{}",e);
		}
		
		return resp;
	}
	
	
	@RequestMapping(path = "/activeUserList")
	@ResponseBody
	public Resp<?> userList(@RequestParam(value = "activeId",required = false)Integer activeId){
		Resp<?> resp = new Resp<>(false);
		try {
			List<ActiveUser> list = activeUserService.getByActiveId(activeId);
			resp = new Resp<>(RespData.OK_CODE,RespData.OK_MSG,list);
			return resp;
		} catch (Exception e) {
			logger.error("save error :{}",e);
		}
		
		return resp;
	}
	
	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/exportUsers")
	public void addOrUpdate(HttpServletRequest req, HttpServletResponse res, Integer activeId) throws IOException {
		List<ActiveUser> peoples = activeUserService.getByActiveId(activeId);
		logger.warn(JSON.toJSONString(peoples));
		String fileName = "users_"+new Date().getTime();
		String sheetName = "users_"+new Date().getTime();
		if (peoples != null && peoples.size() > 0) {
			sheetName = activeId + "_" + sheetName;
			fileName = activeId + "_" + fileName;
		}
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet(sheetName);
		sheet.setColumnWidth(0, 256*20);
		sheet.setColumnWidth(1, 256*20);
		sheet.setColumnWidth(2, 256*40);
		sheet.setColumnWidth(3, 256*20);
		sheet.setColumnWidth(4, 256*20);
		
		HSSFRow row = sheet.createRow((int) 0);
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);

		// set date format
		HSSFCellStyle dateFormatCellStyle = wb.createCellStyle();
		HSSFDataFormat format = wb.createDataFormat();
		dateFormatCellStyle.setDataFormat(format.getFormat("yyyy年m月d日  HH点mm分ss秒"));
		dateFormatCellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);

		HSSFCell cell = row.createCell((short) 0);
		cell.setCellValue("姓名");
		cell.setCellStyle(style);
		cell = row.createCell((short) 1);
		cell.setCellValue("手机号");
		cell.setCellStyle(style);
		cell = row.createCell((short) 2);
		cell.setCellValue("邮箱");
		cell.setCellStyle(style);
		cell = row.createCell((short) 3);
		cell.setCellValue("地址");
		cell.setCellStyle(style);
		cell = row.createCell((short) 4);
		cell.setCellValue("同行人数");
		cell.setCellStyle(style);

		for (int i = 0; i < peoples.size(); i++) {
			row = sheet.createRow((int) i + 1);
			ActiveUser people = peoples.get(i);
			cell=row.createCell((short) 0);
			cell.setCellValue(people.getName());
			cell.setCellStyle(style);
			cell=row.createCell((short) 1);
			cell.setCellValue(people.getMobilePhone());
			cell.setCellStyle(style);
			cell = row.createCell((short) 2);
			cell.setCellValue(people.getEmail());
			cell.setCellStyle(dateFormatCellStyle);
			cell=row.createCell((short) 3);
			cell.setCellValue(people.getAddress());
			cell.setCellStyle(style);
			cell=row.createCell((short) 4);
			cell.setCellValue(people.getWithNum());
			cell.setCellStyle(style);
		}
		// 输出数据流
		try {
			res.setContentType("application/x-download");
			res.setCharacterEncoding("utf-8");
			res.setHeader("Content-Disposition",
					"attachment;filename=" + new String(fileName.getBytes("gbk"), "iso8859-1") + ".xls");
			ServletOutputStream out = res.getOutputStream();
			wb.write(out);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
