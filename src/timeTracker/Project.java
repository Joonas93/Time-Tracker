package timeTracker;

public class Project {
	/*Class is for containing project-info
	 * few useless instance variables there because 
	 * idea was to make more functions to program
	 * but time run out.
	 * 
	 */
	
private int projectID, userID, onPause, finished;
private String projectName, startTime, endTime, workTime;


public Project (int projectID, String projectName, String endTime, String startTime, String workTime, int onPause, int finished, int userID){
	this.projectID = projectID;
	this.projectName = projectName;
	this.endTime = endTime;
	this.workTime = workTime;
	this.userID = userID;
	this.startTime = startTime;
	this.onPause = onPause;
	this.finished = finished;
	
	
}

public String getProjectName() {
	return this.projectName;
}

public int getProjectID () {
	return this.projectID;
}

public String getStartTime() {
	return this.startTime;
}

//Method returns parsed worktime. eg 1 h 40 min
public String getWorkTime() {
	int h, m, total, sum;
	total = Integer.parseInt(this.workTime);
	sum = Integer.parseInt(this.workTime);
			
    m = total % 60;
    total -= m;
    h = total / 60;
    
    if (sum<60) {
    	return sum + " min";
    }
    else {
    	return h + " h " + m + " min";
    }
}

public String getStatus() {
	
	if (this.finished == 0) {
		return "Ongoing";
	}
	else {
		return "Finished";
	}
	
}


}
