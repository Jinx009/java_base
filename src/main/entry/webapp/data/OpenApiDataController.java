package main.entry.webapp.data;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

import common.helper.StringUtil;
import database.models.project.ProIoTOrder;
import main.entry.webapp.BaseController;
import service.basicFunctions.project.ProIoTOrderService;
import utils.HttpData;
import utils.HttpUtils;
import utils.MofangSignUtils;
import utils.Resp;
import utils.UUIDUtils;

@Controller
public class OpenApiDataController extends BaseController{
	
	private static final Logger log = LoggerFactory.getLogger(OpenApiDataController.class);

	
	@Autowired
	private ProIoTOrderService proIoTOrderService;
	
	@RequestMapping(value = "/openApi/rfid/push")
	@ResponseBody
	public Resp<?> setCarNum(String mac,String carNum){
		Resp<?> resp = new Resp<>(false);
		try {
			log.warn("mac:{},carNum:{}",mac,carNum);
			ProIoTOrder proIoTOrder = proIoTOrderService.findByMacNear(mac);
			if(proIoTOrder!=null&&proIoTOrder.getEndTime()==null){
				proIoTOrder.setCarNum(carNum);
				proIoTOrderService.update(proIoTOrder);
			}
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}
	
	@RequestMapping(value = "/openApi/status/push")
	@ResponseBody
	public Resp<?> getOrder(String mac,String logId,String changeTime,Integer status,String desc){
		Resp<?> resp = new Resp<>(false);
		try {
			log.warn("mac:{},logId:{},status:{},changeTime:{},desc:{}",mac,logId,status,changeTime,desc);
			Long time = Long.valueOf(changeTime);
			String uuid = StringUtil.add(mac,"_",logId);
			if(1==status){
				ProIoTOrder proIoTOrder = new ProIoTOrder();
				proIoTOrder.setCreateTime(new Date());
				proIoTOrder.setDescription(desc);
				proIoTOrder.setLogId(logId);
				proIoTOrder.setMac(mac);
				proIoTOrder.setStartTime(new Date(time));
				proIoTOrder.setStartUuid(uuid);
				proIoTOrder.setStatus("0");
				proIoTOrderService.save(proIoTOrder);
			}else if(0==status){
				ProIoTOrder proIoTOrder = proIoTOrderService.findByMacNear(mac);
				if(proIoTOrder!=null&&proIoTOrder.getEndTime()==null){
					proIoTOrder.setEndUuid(uuid);
					proIoTOrder.setEndTime(new Date(time));
					proIoTOrder.setStatus("1");
					proIoTOrderService.update(proIoTOrder);
				}else{
					proIoTOrder = new ProIoTOrder();
					proIoTOrder.setCreateTime(new Date());
					proIoTOrder.setDescription(desc);
					proIoTOrder.setEndUuid(uuid);
					proIoTOrder.setLogId(logId);
					proIoTOrder.setMac(mac);
					proIoTOrder.setEndTime(new Date(time));
					proIoTOrder.setStatus("3");
					proIoTOrderService.save(proIoTOrder);
				}
			}
			return new Resp<>(true);
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}

	
	@RequestMapping(value = "/mofang/notice")
	@ResponseBody
	 public String sendNotice(String status,String mac,String companyOrganId,String storeOrganId){
	        try {
	        	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        	Date date = new Date();
	        	String uuid = UUIDUtils.random();
	            Map<String,String> map = new HashMap<String, String>();
	            map.put("magneticStripleId",mac);
	            map.put("status",status);
	            map.put("occurrenceTimeString",sdf.format(date));
	            map.put("companyOrganId",companyOrganId);
	            map.put("storeOrganId",storeOrganId);
	            map.put("eventId",String.valueOf(date.getTime()/1000));
	            String jsonStr = JSON.toJSONString(map);
	            map.put("path", "/park/magnetic_striple_event");
				map.put("X-POS-REQUEST-ID",uuid);
				map.put("X-POS-REQUEST-TIME", sdf.format(date));
				map.put("X-POS-ACCESS-KEY", HttpData.MOFANG_AK);
				String sign = MofangSignUtils.encrypt(HttpData.MOFANG_SK, MofangSignUtils.getDataString(map));
				map.put("X-POS-REQUEST-SIGN", sign);
	            return HttpUtils.postJsonMofangV2("/park/magnetic_striple_event", map, jsonStr);
	        }catch (Exception e){
	            log.error("error:{}",e);
	        }
	        return null;
	    }
}
