package main.entry.webapp.task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
@Lazy(value=false)
public class GuodongSensorTask {

	private static final Logger logger = LoggerFactory.getLogger(GuodongSensorTask.class);
	
	
	@Scheduled(fixedRate = 1000 * 10,initialDelay = 1000)
	public void init(){
		logger.warn("start job ... ... ");
     }
	
}
