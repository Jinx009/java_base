package main.entry.webapp.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import service.basicFunctions.job.CommonJobService;

@Component
@Lazy(value=false)
public class JobTask {
		private static final Logger logger = LoggerFactory.getLogger(JobTask.class);
		
		@Autowired
		private CommonJobService commonJobService;
		
		
		@Scheduled(fixedRate = 1000 * 120,initialDelay = 1000)
		public void init(){
			logger.warn("start job job... ... ");
			commonJobService.update();
	     }
		
	
}
