package timeTracker;

import java.sql.Timestamp;
import java.util.Calendar;

public class stamp{
/*
 * Class is only for making timestamps.
 */
	private String timestamp;
	
	// eg. 2018-04-04 15:55:55
	public stamp(){
		Calendar calendar = Calendar.getInstance();
		Timestamp currentTimestamp = new java.sql.Timestamp(calendar.getTime().getTime());
		this.timestamp = currentTimestamp.toString();
	}
	
	public String getTimeStamp(){
		return this.timestamp;
	}
}
