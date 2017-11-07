package main.entry.webapp.task;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
@Lazy(value=false)
public class GuodongSensorTask {

	
	
	@Scheduled(fixedRate = 1000 * 10,initialDelay = 1000)
	public void init(){
		System.out.println("start job ... ... ");
     }
	
}
