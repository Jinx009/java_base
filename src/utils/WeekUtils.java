package utils;

import java.util.Calendar;
import java.util.Date;

public class WeekUtils {

	public static int getWeek(Date date) {
		int[] weeks = { 0, 1, 2, 3, 4, 5, 6 };
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int week_index = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (week_index < 0) {
			week_index = 0;
		}
		return weeks[week_index];
	}

	public static void main(String[] args) {
		getWeek(new Date());
	}
	
}
