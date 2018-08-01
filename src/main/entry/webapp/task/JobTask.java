package main.entry.webapp.task;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import database.models.project.ProIoTOrder;
import database.models.project.ProOrder;
import database.models.project.ProSign;
import main.entry.webapp.BaseController;
import service.basicFunctions.HttpService;
import service.basicFunctions.project.ProGatewayAccessControlOldLogService;
import service.basicFunctions.project.ProIoTOrderService;
import service.basicFunctions.project.ProOrderService;
import service.basicFunctions.project.ProSignService;
import utils.BaseConstant;
import utils.HttpData;


@Component
@Lazy(value=false)
public class JobTask extends BaseController{
		private static final Logger logger = LoggerFactory.getLogger(JobTask.class);
		
		@Autowired
		private ProOrderService proOrderService;
		@Autowired
		private ProSignService proSignService;
		@Autowired
		private HttpService httpService;
		@Autowired
		private ProIoTOrderService proIoTOrderService;
		@Autowired
		private ProGatewayAccessControlOldLogService proGatewayAccessControlOldLogService;
		
		
		@Scheduled(fixedRate = 1000 * 1200,initialDelay = 2000)
		public void accessControl(){
			proGatewayAccessControlOldLogService.random();
	     }
		
		
//		@Scheduled(fixedRate = 1000 * 120,initialDelay = 1000)
		@Scheduled(cron = "0 59 23 * * ?")//每天凌晨0点0分
		public void init(){
			logger.warn("start job job... ... ");
//			getOrder(1);
//			getLog(1);
//			getBaseOrder();
	     }
		
		/**
		 * 同步IoT订单
		 * @param p
		 */
		private void getBaseOrder(){
			try {
				List<ProIoTOrder> list = proIoTOrderService.findByStatus("1");
				if(list!=null&&!list.isEmpty()){
					for(ProIoTOrder proIoTOrder : list){
						String eventId = proIoTOrder.getStartUuid();
						String r = httpService.getMofang(getMofangSessionId(),HttpData.mofang_get_order(BaseConstant.BASE_COMPANY_ID,1,eventId));
						 JSONObject  j = JSON.parseObject(r);
						 JSONObject j2 = j.getJSONObject("data");
						 logger.warn("{}",j2.toJSONString());
						 List<ProOrder> list1 = JSONArray.parseArray(j2.getString("products"), ProOrder.class);
						 if(list1!=null&&!list1.isEmpty()){
							 ProOrder proOrder = list1.get(0);
							 proIoTOrder.setCarNum(proOrder.getPlateNumber());
							 proIoTOrder.setPayTime(proOrder.getEndTime());
							 proIoTOrder.setPrice(proOrder.getPrice());
							 proIoTOrder.setRealPrice(proOrder.getRealPrice());
							 proIoTOrder.setRemark(proOrder.getRemark());
							 proIoTOrder.setStatus(proOrder.getStatus());
							 proIoTOrderService.update(proIoTOrder);
						 }
					}
				}
			} catch (Exception e) {
				logger.error("error:{}",e);
			}
		}
		
		/**
		 * 同步订单
		 * @param p
		 */
		private void getOrder(Integer p){
			try {
				String r = httpService.getMofang(getMofangSessionId(),HttpData.mofang_get_order(BaseConstant.BASE_COMPANY_ID,p,""));
				 JSONObject  j = JSON.parseObject(r);
				 JSONObject j2 = j.getJSONObject("data");
				 logger.warn("{}",j2.toJSONString());
				 List<ProOrder> list = JSONArray.parseArray(j2.getString("products"), ProOrder.class);
				 boolean res = proOrderService.save(list);
				 logger.warn("b : {}",res);
				 if(res){
					 p++;
					 getOrder(p);
				 }
			} catch (Exception e) {
				logger.error("error:{}",e);
			}
		}
		
		/**
		 * 同步考勤
		 * @param p
		 */
		private void getLog(Integer p){
			try {
				JSONObject  j = HttpData.mofang_get_sign(getMofangSessionId(),BaseConstant.BASE_COMPANY_ID,BaseConstant.BASE_STORE_ID,null,p);
				 JSONObject j2 = j.getJSONObject("data");
				 logger.warn("{}",j2.toJSONString());
				 List<ProSign> list = JSONArray.parseArray(j2.getString("operationLogs"), ProSign.class);
				 boolean res = proSignService.save(list);
				 logger.warn("b : {}",res);
				 if(res){
					 p++;
					 getLog(p);
				 }
			} catch (Exception e) {
				logger.error("error:{}",e);
			}
		}
		
	
}
