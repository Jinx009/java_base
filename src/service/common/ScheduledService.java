package service.common;


public interface ScheduledService {
	public void  putNewScheduleTask(ScheduledTaskInterface sti, Long taskIntervalTime);
	public void  removeScheduleTask(ScheduledTaskInterface sti);
}
