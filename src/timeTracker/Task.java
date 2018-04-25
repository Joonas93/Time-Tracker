package timeTracker;

public class Task {
/*
 * Class contains tasks info.
 * pauseTime is not in use for now
 */
	private int taskID, projectID;
	private String startTime, endTime, pauseTime, continueTime, workTime, taskName;
	private boolean paused = false;
	private boolean finished = false;

	
	public Task(int taskID, int projectID, String startTime, String endTime, String pauseTime, String continueTime, String workTime, String taskName, String finished) {
		
		this.taskID = taskID;
		this.projectID = projectID;
		this.startTime = startTime;
		this.endTime = endTime;
		this.pauseTime = pauseTime;
		this.continueTime = continueTime;
		this.workTime = workTime;
		this.taskName = taskName;
		
		if (continueTime == null) {
			this.paused = true;
		}
		
		if (finished.equals("1")){
			this.finished = true;
			
		}
		
	}
	
	public int getTaskID(){
		return this.taskID;
	}
	public int getProjectID(){
		return this.projectID;
	}
	public String getStartTime(){
		return this.startTime;
	}
	public String getEndTime(){
		return this.endTime;
	}
	public String getPauseTime(){
		return this.pauseTime;
	}
	public String getContinueTime(){
		return this.continueTime;
	}
	//Method returns parsed worktime. eg 1 h 40 min
	public String getWorkTime(){
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
	public String getTaskName(){
		return this.taskName;
	}
	
	public boolean isPaused() {
		return this.paused;
	}
	
	public boolean isFinished() {
		return this.finished;
	}
	
	public void finish() {
		finished = true;
	}
	
	public void pause(){
		if (paused) {
			paused = false;
		}
		else if (!paused) {
			paused = true;
		}
	}
	
}

