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

import database.models.project.ProOrder;
import main.entry.webapp.BaseController;
import service.basicFunctions.HttpService;
import service.basicFunctions.project.ProOrderService;
import utils.BaseConstant;
import utils.HttpData;


@Component
@Lazy(value=false)
public class JobTask extends BaseController{
		private static final Logger logger = LoggerFactory.getLogger(JobTask.class);
		
		@Autowired
		private ProOrderService proOrderService;
		@Autowired
		private HttpService httpService;
		
		
//		@Scheduled(fixedRate = 1000 * 120,initialDelay = 1000)
		@Scheduled(cron = "0 */50 * * * ?")//50分钟处理一次
		public void init(){
			logger.warn("start job job... ... ");
			getOrder(1);
	     }
		
		private void getOrder(Integer p){
			try {
				String r = httpService.getMofang(getMofangSessionId(),HttpData.mofang_get_order(BaseConstant.BASE_COMPANY_ID,p));
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
		
	
}
