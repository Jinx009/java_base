package task;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
@Lazy(value=false)
public class GuodongSensorTask {

	
	
	@Scheduled(fixedRate = 20*1000)//每一分钟执行一次
	public void init(){
		
     }
	
}
