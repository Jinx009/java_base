package main.entry.webapp.data.parking;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

import main.entry.webapp.BaseController;
import utils.BaseConstant;
import utils.HttpData;
import utils.HttpUtils;
import utils.MofangSignUtils;
import utils.Resp;
import utils.UUIDUtils;

/**
 * 考勤记录管理
 * @author jinx
 *
 */
@Controller
@RequestMapping(value = "/home/d")
public class ParkingAttendanceRecordDataController extends BaseController{
	
	private static final Logger logger = LoggerFactory.getLogger(ParkingAttendanceRecordDataController.class);

	
	/**
	 * 获取所有考勤记录
	 * @return
	 */
	@RequestMapping(path = "/attendanceRecord")
	@ResponseBody
	public Resp<?> findAll(){
		Resp<?> resp = new Resp<>(false);
		try {
			Map<String, String> map = new HashMap<String,String>();
			String uuid = UUIDUtils.random();
			Date date = new Date();
			String url = "/core/operation_log?pageSize=25&pageNum=1&storeOrganId="+BaseConstant.BASE_STORE_ID+"&companyOrganId="+BaseConstant.BASE_COMPANY_ID;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			map.put("pageNum","1");
			map.put("pageSize","25");
			map.put("storeOrganId", BaseConstant.BASE_STORE_ID);
			map.put("companyOrganId",BaseConstant.BASE_COMPANY_ID);
			map.put("path", "/core/operation_log");
			map.put("X-POS-REQUEST-ID",uuid);
			map.put("X-POS-REQUEST-TIME", sdf.format(date));
			map.put("X-POS-ACCESS-KEY", HttpData.MOFANG_AK);
			String sign = MofangSignUtils.encrypt(HttpData.MOFANG_SK, MofangSignUtils.getDataString(map));
			map.put("X-POS-REQUEST-SIGN", sign);
			return new Resp<>(JSON.parseObject(HttpUtils.getMofangV2(url,map)));
		} catch (Exception e) {
			logger.error("error:{}",e);
		}
		return resp;
	}
	
}
