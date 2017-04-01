package service.common;

public interface ScheduledTaskInterface {
	
	public boolean execute();
	public boolean registerTask();
	public boolean unregisterTask();
}
