package service.common;

import java.util.ArrayList;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;


@Service("scheduledService")
public class ScheduledServiceImpl implements ScheduledService{

	static Long times = 1l;
	List<ScheduledTaskInterface> run001SecondList = new ArrayList<ScheduledTaskInterface>();
	
	@Scheduled( fixedDelay= 5000 )
	public void run001Second() {
		//System.out.println("times " + times++);
		for(int i = 0 ; i < run001SecondList.size() ; i++){
			run001SecondList.get(i).execute();
		}
	}

	@Override
	public void putNewScheduleTask(ScheduledTaskInterface sti, Long taskIntervalTime) {
		if( ScheduledTaskTime.RUN_0001_SEC.equals(taskIntervalTime) )
			run001SecondList.add(sti);
	}

	@Override
	public void removeScheduleTask(ScheduledTaskInterface sti) {
		run001SecondList.remove(sti);
	}

}
