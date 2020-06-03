package main.entry.webapp.task;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import database.models.project.ProGoods;
import database.models.project.ProOrder;
import service.basicFunctions.project.ProGoodsService;
import service.basicFunctions.project.ProOrderService;


@Component
@Lazy(value=false)
public class JobTask {
	
		private static final Logger logger = LoggerFactory.getLogger(JobTask.class);
		
		@Autowired
		private ProGoodsService proGoodsService;
		@Autowired
		private ProOrderService proOrderService;
		
		@Scheduled(fixedRate = 1000 * 120,initialDelay = 1000)
		public void init(){
			List<ProOrder> list = proOrderService.findByStatus(0);
			if(list!=null&&!list.isEmpty()){
				for(ProOrder proOrder : list){
					Date date = new Date();
					if((date.getTime()-proOrder.getCreateTime().getTime())>(1000*60*15)){
						logger.warn("order id :{}",proOrder.getId());
						proOrder.setStatus(2);
						proOrderService.update(proOrder);
						ProGoods proGoods = proGoodsService.findByTimeDateAbc(proOrder.getTime(), proOrder.getType(), proOrder.getDate());
						proGoods.setType(0);
						proGoodsService.update(proGoods);
					}
				}
			}
	     }
		
	
}
