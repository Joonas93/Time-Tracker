package timeTracker;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.sql.Timestamp;

public class projectDB {

	/*
	 * Class is for all DB activities used in this project
	 * 
	 */
	
	
	private final String URL = "jdbc:mysql://eu-cdbr-azure-west-b.cloudapp.net:3306/eskonheimo_joonas";
	private final String USERNAME = "bc80c3f3885768";
	private final String PASSWORD= "25e13b1f";
	
	private Connection connection = null;
	private PreparedStatement selectAllProjects = null;
	private PreparedStatement selectAllTasks = null;
	private PreparedStatement addProject = null;
	private PreparedStatement delProject = null;
	private PreparedStatement addTask = null;
	private PreparedStatement delTask = null;
	private PreparedStatement getTaskStatus = null;
	private PreparedStatement finishTask = null;
	private PreparedStatement pauseTask = null;
	private PreparedStatement startTask = null;
	private PreparedStatement continueTask = null;
	private PreparedStatement updateWorkTime = null;
	private PreparedStatement getTasksWorkTime = null;
	private PreparedStatement updateProjectWorkTime = null;
	private PreparedStatement getUser = null;
	private PreparedStatement getAllUsers = null;
	private PreparedStatement addUserDB = null;
	private PreparedStatement delUserDB = null;
	private PreparedStatement finishProjectDB = null;
	
	
	
	
	private static int workTime = 0;
	private static String sql;
	
	 
	
	//Constructor for DB-connection
	public projectDB() {
		
		try {
		
		connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		
		}
		catch (SQLException sqlException)
		{
			sqlException.printStackTrace();
			System.exit(1);
		}
	}
	//Method gets all projects from DB
	public ArrayList<Project> getProjects(int userID) {
		ArrayList<Project> result = null;
		ResultSet resultSet = null;
	
		try {
			sql = "SELECT * FROM project WHERE userID = ?";
			selectAllProjects = connection.prepareStatement(sql);
			selectAllProjects.setInt(1, userID);
			resultSet = selectAllProjects.executeQuery();
			result = new ArrayList<Project>();
			
			while (resultSet.next()) {
				
				result.add(new Project(
					resultSet.getInt("projectID"),
					resultSet.getString("projectName"),
					resultSet.getString("endTime"),
					resultSet.getString("startTime"),
					resultSet.getString("workTime"),
					resultSet.getInt("onPause"),
					resultSet.getInt("finished"),
					resultSet.getInt("userID")
						
						
						));
			}
			
		} catch (SQLException sqlException)
		{
			sqlException.printStackTrace();
		}
		finally
		{
			try
			{
				resultSet.close();
			}
			catch (SQLException sqlException)
			{
				sqlException.printStackTrace();
			}
		}
		return result;
		
	}
	//Method gets all tasks from specific project 
	public ArrayList<Task> getTasks(String projectID) {
		
		ArrayList<Task> result = null;
		ResultSet resultSet = null;
		
		try {
			sql = "SELECT * FROM task WHERE projectID =?";
			selectAllTasks = connection.prepareStatement(sql);
			selectAllTasks.setString(1, projectID);
			resultSet = selectAllTasks.executeQuery();
			result = new ArrayList<Task>();
			
			while (resultSet.next()) {
				
				result.add(new Task(
						
						resultSet.getInt("taskID"),
						resultSet.getInt("projectID"),
						resultSet.getString("startTime"),
						resultSet.getString("endTime"),
						resultSet.getString("pauseTime"),
						resultSet.getString("continueTime"),
						resultSet.getString("workTime"),
						resultSet.getString("taskName"),
						resultSet.getString("finished")
					));
				
			}
		} catch (SQLException sqlException)
		{
			sqlException.printStackTrace();
		}
		finally
		{
			try
			{
				resultSet.close();
			}
			catch (SQLException sqlException)
			{
				sqlException.printStackTrace();
			}
		}
		return result;
		
	}
	//Method adds project to DB
	public void addProjectDB(String projectName, int ID) {
		// TODO Auto-generated method stub
		sql = "INSERT INTO project (projectName, userID, startTime) VALUES(?, ?, ?)";
		stamp a = new stamp();
		
		try {
			addProject = connection.prepareStatement(sql);
			addProject.setString(1, projectName);
			addProject.setInt(2, ID);
			addProject.setString(3, a.getTimeStamp());
			addProject.executeUpdate();
		
		}
		catch (SQLException sqlException)
		{
			sqlException.printStackTrace();
		}
		

	}


	//Deletes  project from DB
	public void deleteProjectDB(String iD) {
		sql = "DELETE FROM task WHERE projectID =?";
		
		try {
			delProject = connection.prepareStatement(sql);
			delProject.setString(1, iD);
			delProject.executeUpdate();

			sql = "DELETE FROM project WHERE projectID =?";
			delProject = connection.prepareStatement(sql);
			delProject.setString(1, iD);
			delProject.executeUpdate();
		
		}
		catch (SQLException sqlException)
		{
			sqlException.printStackTrace();
		}
		
	}
	//Adds task to DB
	public void addTaskDB(String taskName,String projectID){
		
		sql = "INSERT INTO task (projectID, taskName) VALUES(?, ?)";
		
			try {
			addTask = connection.prepareStatement(sql);
			addTask.setString(1, projectID);
			addTask.setString(2, taskName);
			addTask.executeUpdate();
		
			}
			catch (SQLException sqlException)
			{
			sqlException.printStackTrace();
			}
		
	}
	//Deletes task from DB
	public void delTaskDB(String taskID) {
		
		sql = "DELETE FROM task WHERE taskID =?";
		
		try {
			delTask = connection.prepareStatement(sql);
			delTask.setString(1, taskID);
			delTask.executeUpdate();
		}
		catch (SQLException s) {
			s.printStackTrace();
		}
				
	
	}
	//Gets project tasks worktimes
	public void getTasksWorkTime(String ID) {
		sql = "SELECT workTime FROM task WHERE projectID =?";
		ResultSet resultSet = null;
		ArrayList<Integer> times = null;
		int total=0;
		
		try {
			times = new ArrayList<Integer>();
			getTasksWorkTime = connection.prepareStatement(sql);
			getTasksWorkTime.setString(1, ID);
			resultSet = getTasksWorkTime.executeQuery();
			
			while(resultSet.next()) {
				times.add(resultSet.getInt("workTime"));
			}
			//Calculate all the worktimes
			for (int x = 0; x<times.size(); x++) {
				
				total += times.get(x);
				
				
			}
			
			workTime = total;

			//Adds them to project worktime in DB
			updateTimeDB(ID,5);
			workTime = 0;
			
		}
		catch (SQLException sqlException)
		{
			sqlException.printStackTrace();
		}
		finally
		{
			try
			{
				resultSet.close();
			}
			catch (SQLException sqlException)
			{
				sqlException.printStackTrace();
			}
		}
	}
	
	

	//Calculate time spent working for single task
	public void updateWorkTime(String ID){
		sql = "SELECT workTime, continueTime FROM task WHERE taskID=?";
		
		ResultSet resultSet = null;
		String continueTime ="";
		stamp a = new stamp();
		
		try {
		getTaskStatus = connection.prepareStatement(sql);
		getTaskStatus.setString(1, ID);
		
		resultSet = getTaskStatus.executeQuery();
		
		while (resultSet.next()){
		continueTime = resultSet.getString("continueTime");
		
		workTime = resultSet.getInt("workTime");
		
		}
		//gets timestamp from start time of continueing working
		Timestamp cT = Timestamp.valueOf(continueTime);
		//current time
		Timestamp now = Timestamp.valueOf(a.getTimeStamp());

		//Convert them to milliseconds and calculate differece
		long milliseconds1 = cT.getTime();
		long milliseconds2 = now.getTime();

		long diff = milliseconds2 - milliseconds1;
		//parse them to minutes
		long diffMinutes = diff/60000;
		int tmpWorkTime = (int) diffMinutes;
		
		workTime = workTime + tmpWorkTime;
		//Add working time back to DB
		updateTimeDB(ID,4);
		workTime = 0;
		
		
		
		}catch (SQLException sqlException)
		{
			sqlException.printStackTrace();
		}
		finally
		{
			try
			{
				resultSet.close();
			}
			catch (SQLException sqlException)
			{
				sqlException.printStackTrace();
			}
		}
		
	}
	
	// method for updating different times etc to DB. Not so developer friendly... 
	public void updateTimeDB(String ID, int x) {
		
		stamp a = new stamp();
		
		try {
		
		
		switch (x) {
		//Second parameter is zero if want to finish task
		case 0: 
			updateWorkTime(ID);
			sql = "UPDATE task SET finished=1, endTime=? WHERE taskID=?";
			finishTask = connection.prepareStatement(sql);
			finishTask.setString(1, a.getTimeStamp());
			finishTask.setString(2, ID);
			finishTask.executeUpdate();
			
			break;
		// Second parameter is 1 if want to pause task
		case 1:
			updateWorkTime(ID);
			sql = "UPDATE task SET continueTime = null WHERE taskID=?";
			pauseTask = connection.prepareStatement(sql);
			pauseTask.setString(1, ID);
			pauseTask.executeUpdate();
			
			break;
		// Second parameter  is 2 if want to start task
		case 2:
			sql = "UPDATE task SET startTime=?, continueTime=? WHERE taskID=?";
			startTask = connection.prepareStatement(sql);
			startTask.setString(1, a.getTimeStamp());
			startTask.setString(2, a.getTimeStamp());
			startTask.setString(3, ID);
			startTask.executeUpdate();
			
			break;
		//Second parameter is 3 if want to continue task
		case 3:
			sql = "UPDATE task SET continueTime=? WHERE taskID=?";
			continueTask = connection.prepareStatement(sql);
			continueTask.setString(1, a.getTimeStamp());
			continueTask.setString(2, ID);
			continueTask.executeUpdate();
			
			break;
		// Second parameter is 4 if want to update working time to DB
		case 4: 
			sql = "UPDATE task SET workTime=? WHERE taskID=?";
			updateWorkTime = connection.prepareStatement(sql);
			updateWorkTime.setInt(1, workTime);
			updateWorkTime.setString(2, ID);
			updateWorkTime.executeUpdate();
			
			
		case 5:
		// Second parameter is 5 if want to update working time for project
			sql = "UPDATE project SET workTime=? WHERE projectID=?";
			updateProjectWorkTime = connection.prepareStatement(sql);
			updateProjectWorkTime.setInt(1, workTime);
			updateProjectWorkTime.setString(2, ID);
			updateProjectWorkTime.executeUpdate();
			
		}	
	} catch (SQLException sqlException)
	{
		sqlException.printStackTrace();
	}
	}
	// Gets all users from DB. Used for admintable
	public ArrayList<User> getAllUsers(){
		ArrayList<User> users = null;
		ResultSet resultSet = null;
		try {
		sql = "SELECT * FROM user";
		getAllUsers = connection.prepareStatement(sql);
		resultSet = getAllUsers.executeQuery();
		users = new ArrayList<User>();
		
		while (resultSet.next()) {
			
				users.add( new User(
						resultSet.getInt("UserID"),
						resultSet.getString("firstName"),
						resultSet.getString("lastName")
					));
		}
		
		} catch (SQLException sqlException)
		{
			sqlException.printStackTrace();
		}
		finally
		{
			try
			{
				resultSet.close();
			}
			catch (SQLException sqlException)
			{
				sqlException.printStackTrace();
			}
		}
		
		return users;
	}
	//Gets single user from DB. For now used only in logging
	public User getUser(int ID) {
		
		User user = null;
		ResultSet resultSet = null;
		try {
		sql = "SELECT * FROM user WHERE UserID=?";
		getUser = connection.prepareStatement(sql);
		getUser.setInt(1, ID);
		resultSet = getUser.executeQuery();
		
		if (resultSet.next()) {
			
				user = new User(
						resultSet.getInt("UserID"),
						resultSet.getString("firstName"),
						resultSet.getString("lastName")
					);	
		}
		
		} catch (SQLException sqlException)
		{
			sqlException.printStackTrace();
		}
		finally
		{
			try
			{
				resultSet.close();
			}
			catch (SQLException sqlException)
			{
				sqlException.printStackTrace();
			}
		}
		
		return user;
	}
	//Adds user to DB
	public void addUserDB (User user){
		User tmp;
		tmp = getUser(user.getUserID());
		//if user is not found it will add user
		if (tmp == null) {
			sql = "INSERT INTO user (UserID, firstName, lastName) VALUES (?,?,?)";
			
			try {
				addUserDB = connection.prepareStatement(sql);
				addUserDB.setInt(1, user.getUserID());
				addUserDB.setString(2, user.getFirstName());
				addUserDB.setString(3, user.getLastName());
				addUserDB.executeUpdate();
			
				}
				catch (SQLException sqlException)
				{
				sqlException.printStackTrace();
				}
		}
		else {
			//If user exist it will show dialog
			JFrame frame = new JFrame();
			JOptionPane.showMessageDialog(frame, "User ID already exist!");
		}
	}
	//Delete user from DB
	public void deleteUserDB(String ID){
		
		sql = "DELETE FROM user WHERE UserID=?";
		
		try {
			delUserDB = connection.prepareStatement(sql);
			delUserDB.setString(1, ID);
			delUserDB.executeUpdate();
		}
		catch (SQLException s) {
			s.printStackTrace();
		}
	}
	//Set project status to finished in DB. Also does it to all tasks in that project
	//For now it doesn't still update all ongoing tasks worktimes but maybe someday.
	public void finishProjectDB(String ID) {
		
		sql = "UPDATE project SET finished = 1 WHERE projectID=?";
		String sql2 = "UPDATE task SET finished = 1, endTime=? WHERE projectID=? AND NOT finished =1";
		stamp a = new stamp();
		int x;
		try {
			finishProjectDB = connection.prepareStatement(sql);
			finishProjectDB.setString(1, ID);
			finishProjectDB.executeUpdate();
			finishProjectDB.close();
			finishProjectDB = connection.prepareStatement(sql2);
			finishProjectDB.setString(1, a.getTimeStamp());
			finishProjectDB.setString(2, ID);
			finishProjectDB.executeUpdate();
		}
		catch (SQLException s) {
			s.printStackTrace();
		}
	}
}
		
	

