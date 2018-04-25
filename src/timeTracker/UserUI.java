package timeTracker;

import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.CardLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JOptionPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ListSelectionModel;

public class UserUI extends JFrame {
	/* 
	Code is super messy. Before main method is only Swing things.
	I didn't have time to complete whole project as I would like it and few things is made
	stupid and code is barely reusable. But it still works as long as you finish tasks before finishing project 
	so it will calculate work time. 
	
	This class is for UI and contains main method + few other method used in UI. 
	
	*/

	
	private JPanel adminUI, loginUI ;
	private JPanel basicUIprojectView;
	private JButton btnAddTask1;
	private JPanel basicUIFinishedProjects;
	private JButton btnBackToMenu;
	private JTable taskTable;
	private JTable projectTable;
	DefaultTableModel projectModel, taskModel, finishedModel, adminModel;
	private JScrollPane scrollPane;
	private JScrollPane scrollPane_2;
	private JLabel lblProjects;
	private JLabel lblTasks;
	private JButton btnAddProject;
	private JButton btnDeleteProject;
	private ArrayList<Project> allProjects;
	private ArrayList<Task> allTasks;
	private JButton btnDeleteTask;
	private JButton btnStartTask;
	private JTextField loginField;
	private JLabel lblUserId;
	private JButton btnLogin;
	private JTable adminTable;
	private JScrollPane scrollPane_1;
	private JButton btnAddUser;
	private JButton btnDeleteUser;
	private JButton btnFinishProject;
	
	private projectDB projectDB;
	private static User user = null;
	private static int userID;
	
	private static final long serialVersionUID = 1L;
	private static final int NAME_COL = 0;
	private static final int STARTTIME_COL = 1;
	private static final int WORKTIME_COL = 2;
	private static final int STATUS_COL = 3;
	private static final int ID_COL = 4;
	private JButton btnLogOut;
	
	public UserUI() {
		super("TimeTracker");
		
		setResizable(false);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1080, 654);
		getContentPane().setLayout(new CardLayout(0, 0));
		loginUI = new JPanel();
		loginUI.setPreferredSize(new Dimension(20, 20));
		loginUI.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(loginUI, "name_2758751978364470");
		loginUI.setLayout(null);
		
		loginField = new JTextField();
		loginField.setFont(new Font("Tahoma", Font.PLAIN, 21));
		loginField.setBounds(394, 200, 278, 46);
		loginUI.add(loginField);
		loginField.setColumns(10);
		
		lblUserId = new JLabel("User ID:");
		lblUserId.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblUserId.setBounds(288, 201, 109, 34);
		loginUI.add(lblUserId);
		
		btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Initiate User-object from DB to check is there user with inputted ID
				user = projectDB.getUser(Integer.parseInt(loginField.getText()));
				
				//if User-object is null DB didn't contain userID
				if (user == null) {
					JFrame tmp = new JFrame();
					JOptionPane.showMessageDialog(tmp, "Wrong user ID");
					}
				//if user is admin
				else if(user.getUserID() == 1) {
					adminUI.setVisible(true);
					loginUI.setVisible(false);
					updateAdminTable();
					
				}
				else {
					userID = user.getUserID();
					basicUIprojectView.setVisible(true);
					loginUI.setVisible(false);
					updateProjectTable();
				}
			
			
			}
		});
		btnLogin.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnLogin.setBounds(458, 307, 149, 51);
		loginUI.add(btnLogin);
		
		btnBackToMenu = new JButton("Back");
		btnBackToMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Goes back to login
				basicUIFinishedProjects.setVisible(false);
				loginUI.setVisible(true);
			}
		});
		btnBackToMenu.setBounds(51, 509, 97, 25);

		adminUI = new JPanel();
		adminUI.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(adminUI, "name_2758751990741331");
		adminUI.setLayout(null);
		
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(195, 86, 605, 415);
		adminUI.add(scrollPane_1);
		
		adminTable = new JTable();
		adminModel = new DefaultTableModel(
			new Object[0][3] 
			,
			new String[] {
				"User ID", "First name", "Last name"
			}
		);
		adminTable.setModel(adminModel);
		scrollPane_1.setViewportView(adminTable);
		
		btnAddUser = new JButton("Add user");
		btnAddUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/*
				 * this is for making dialog for adding new user
				 */
				JTextField userID = new JTextField(10);
				JTextField firstName = new JTextField(15);
				JTextField lastName = new JTextField(15);
				JPanel panel = new JPanel();
				
				panel.add(new JLabel("Enter user ID: "));
				panel.add(userID);
				panel.add(new JLabel("Enter first name: "));
				panel.add(firstName);
				panel.add(new JLabel("Enter last name: "));
				panel.add(lastName);
				
				int result = JOptionPane.showConfirmDialog(null, panel,"Add new user", JOptionPane.OK_CANCEL_OPTION);
				// If user click ok new User-object is made and passed as parameter.
				if (result == 0){
					try {
						User user = new User(Integer.parseInt(userID.getText()), firstName.getText(), lastName.getText());
						projectDB.addUserDB(user);
						updateAdminTable();
					}
					catch (NumberFormatException t) {
						JFrame frame = new JFrame();
						JOptionPane.showMessageDialog(frame, "OOPS! Try again. UserID needs to be integer");
					}
				}
			}
		});
		btnAddUser.setBounds(203, 566, 97, 25);
		adminUI.add(btnAddUser);
		
		btnDeleteUser = new JButton("Delete user");
		btnDeleteUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Selected row ID column value
				try {
				String x = adminTable.getModel().getValueAt(adminTable.getSelectedRow(),0 ).toString();
				projectDB.deleteUserDB(x);
				updateAdminTable();
				}
				catch (ArrayIndexOutOfBoundsException t) {
					JFrame tmp = new JFrame();
					JOptionPane.showMessageDialog(tmp, "No user is selected!");
				}
			}
		});
		btnDeleteUser.setBounds(376, 566, 110, 25);
		adminUI.add(btnDeleteUser);
		
		btnLogOut = new JButton("Log out");
		btnLogOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adminUI.setVisible(false);
				loginUI.setVisible(true);
			}
		});
		btnLogOut.setRolloverEnabled(false);
		btnLogOut.setBounds(708, 566, 97, 25);
		adminUI.add(btnLogOut);
		
		basicUIprojectView = new JPanel();
		getContentPane().add(basicUIprojectView, "name_2762409856754223");
		basicUIprojectView.setLayout(null);
		//Goes back to login
		JButton btnLogOut1 = new JButton("Log out");
		btnLogOut1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				basicUIprojectView.setVisible(false);
				loginUI.setVisible(true);
			}
		});
		btnLogOut1.setBounds(946, 575, 97, 25);
		basicUIprojectView.add(btnLogOut1);
		
		JButton btnContinueTask = new JButton("Continue task");
		btnContinueTask.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					//If task is paused it will allow to continue it
					if (getTaskStatus().equals("Paused")) {
					projectDB.updateTimeDB(getTaskID(), 3);
					updateTaskTable(getProjID());
					}
					else {
					JFrame tmp = new JFrame();
					JOptionPane.showMessageDialog(tmp, "Task is not paused.");
					}
				}
				catch (ArrayIndexOutOfBoundsException t) {
					JFrame tmp = new JFrame();
					JOptionPane.showMessageDialog(tmp, "No task is selected!");
				}
				
				
			}
		});
		btnContinueTask.setBounds(927, 148, 116, 25);
		basicUIprojectView.add(btnContinueTask);
		
		JButton btnFinishTask = new JButton("Finish task");
		btnFinishTask.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					if (getTaskStatus().equals("Finished")) {
						JFrame tmp = new JFrame();
						JOptionPane.showMessageDialog(tmp, "Task is already finished.");
					}
					//If task isn't finished it will finish it
					else {
						projectDB.updateTimeDB(getTaskID(), 0);
						updateTaskTable(getProjID());
					}
				}
				catch (ArrayIndexOutOfBoundsException t) {
					JFrame tmp = new JFrame();
					JOptionPane.showMessageDialog(tmp, "No task is selected!");
				}
			}
		});
		btnFinishTask.setBounds(927, 111, 116, 25);
		basicUIprojectView.add(btnFinishTask);
		
		JButton btnPauseTask = new JButton("Pause task");
		btnPauseTask.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (getTaskStatus().equals("Paused") ||getTaskStatus().equals("Finished") ) {
						JFrame tmp = new JFrame();
						JOptionPane.showMessageDialog(tmp, "Task is already paused or finished.");
					}
					//If task isn't paused or finished it will pause it
					else {
						projectDB.updateTimeDB(getTaskID(), 1);
						projectDB.getTasksWorkTime(getProjID());
						updateTaskTable(getProjID());
						updateProjectTable();
					}
				}
				catch (ArrayIndexOutOfBoundsException t) {
					JFrame tmp = new JFrame();
					JOptionPane.showMessageDialog(tmp, "No task is selected!");
					}
			}
		});
		btnPauseTask.setBounds(927, 186, 116, 25);
		basicUIprojectView.add(btnPauseTask);
		
		btnAddTask1 = new JButton("Add task");
		btnAddTask1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				/*
				 * Makes dialog for adding new task
				 */
				JTextField taskName = new JTextField(20);
				JPanel addTask = new JPanel();
				
				addTask.add(new JLabel("Task name: "));
				addTask.add(taskName);
				int result = JOptionPane.showConfirmDialog(null, addTask,"Add new task", JOptionPane.OK_CANCEL_OPTION);
				// if user click OK
				if (result == 0){
				  projectDB.addTaskDB(taskName.getText(), getProjID());
				  updateTaskTable(getProjID());
				}
			}
		});
		btnAddTask1.setBounds(650, 575, 116, 25);
		basicUIprojectView.add(btnAddTask1);
		
		scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(477, 80, 421, 378);
		basicUIprojectView.add(scrollPane_2);
		
		taskTable = new JTable();
		taskTable.setRowHeight(20);
		taskModel = new DefaultTableModel(
				new Object[0][5] 
				,
				new String[] {"Task", "Started", "Worktime", "Status", "ID"
				}
			);
		taskTable.setModel(taskModel);
		taskTable.removeColumn(taskTable.getColumnModel().getColumn(ID_COL));
		scrollPane_2.setViewportView(taskTable);
	
		scrollPane = new JScrollPane();
		scrollPane.setBounds(24, 80, 406, 378);
		basicUIprojectView.add(scrollPane);
		
		projectTable = new JTable();
		projectTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		projectTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				updateTaskTable(getProjID());
			}
		});
		projectTable.setRowHeight(20);
		projectModel = new DefaultTableModel(
			new Object[10][5] 
			,
			new String[] {"Project", "Started", "Worktime", "Status", "ID"
			}
		);
		projectTable.setModel(projectModel);
		projectTable.removeColumn(projectTable.getColumnModel().getColumn(ID_COL));
		scrollPane.setViewportView(projectTable);
		
		
		lblProjects = new JLabel("Projects:");
		lblProjects.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblProjects.setBounds(202, 55, 77, 16);
		basicUIprojectView.add(lblProjects);
		
		lblTasks = new JLabel("Tasks:");
		lblTasks.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblTasks.setBounds(670, 55, 56, 16);
		basicUIprojectView.add(lblTasks);
		
		btnAddProject = new JButton("Add project");
		btnAddProject.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				/*
				 * Makes dialog for adding new project
				 */
				JTextField projectName = new JTextField(20);
				JPanel addProject = new JPanel();
				
				addProject.add(new JLabel("Project name: "));
				addProject.add(projectName);
				int result = JOptionPane.showConfirmDialog(null, addProject,"Add new project", JOptionPane.OK_CANCEL_OPTION);
				// if user click OK
				if (result == 0){
				 projectDB.addProjectDB(projectName.getText(), userID);
				 updateProjectTable();
				}
			}
		});
		btnAddProject.setBounds(42, 575, 106, 25);
		basicUIprojectView.add(btnAddProject);
		
		btnDeleteProject = new JButton("Delete project");
		btnDeleteProject.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					projectDB.deleteProjectDB(getProjID());
					updateProjectTable();
				}
				catch (ArrayIndexOutOfBoundsException t) {
					JFrame tmp = new JFrame();
					JOptionPane.showMessageDialog(tmp, "No project is selected!");
				}
			}
		});
		btnDeleteProject.setBounds(170, 575, 116, 25);
		basicUIprojectView.add(btnDeleteProject);
		
		btnDeleteTask = new JButton("Delete task");
		btnDeleteTask.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					projectDB.delTaskDB(getTaskID());
					updateTaskTable(getProjID());
				}
				catch (ArrayIndexOutOfBoundsException t) {
					JFrame tmp = new JFrame();
					JOptionPane.showMessageDialog(tmp, "No task is selected!");
				}
			}
		});
		btnDeleteTask.setBounds(782, 575, 116, 25);
		basicUIprojectView.add(btnDeleteTask);
		
		btnStartTask = new JButton("Start task");
		btnStartTask.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					//It will start task if it isn't started yet
					if (getTaskStatus().equals("Not started")) {
						projectDB.updateTimeDB(getTaskID(), 2);
						updateTaskTable(getProjID());
					}
					else if (getTaskStatus().equals("Finished")) {
						JFrame tmp = new JFrame();
						JOptionPane.showMessageDialog(tmp, "Task is already finished.");
					}
					else {
						JFrame tmp = new JFrame();
						JOptionPane.showMessageDialog(tmp, "Task is already started.");
					}
				}
				catch (ArrayIndexOutOfBoundsException t) {
					JFrame tmp = new JFrame();
					JOptionPane.showMessageDialog(tmp, "No task is selected!");
				}
			}
		});
		btnStartTask.setBounds(927, 223, 116, 25);
		basicUIprojectView.add(btnStartTask);
		
		btnFinishProject = new JButton("Finish project");
		btnFinishProject.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					projectDB.finishProjectDB(getProjID());
					updateProjectTable();
				}
				catch (ArrayIndexOutOfBoundsException t) {
					JFrame tmp = new JFrame();
					JOptionPane.showMessageDialog(tmp, "No task is selected!");
				}
			}
		}
		);
		btnFinishProject.setBounds(307, 575, 123, 25);
		basicUIprojectView.add(btnFinishProject);
		
		projectDB = new projectDB(); //fires up database connection
	}


public static void main(String [] args)
{
	UserUI frame = new UserUI();
	frame.setLocationRelativeTo(null);
	frame.setVisible(true);
	
}
// This method is for populating table in Admins view
public void updateAdminTable() {
	
	User user;
	ArrayList<User> allUsers = projectDB.getAllUsers();
	
	adminModel.setRowCount(allUsers.size()-1);
	System.out.println(allUsers.size());
	for (int row = 0; row < allUsers.size(); row++) {
		user = allUsers.get(row);
		//removes admin-user from users
		if (user.getUserID() == 1) {
			allUsers.remove(row);
			row--;
		}
		else {
		adminTable.getModel().setValueAt(user.getUserID(), row, 0);
		adminTable.getModel().setValueAt(user.getFirstName(), row, 1);
		adminTable.getModel().setValueAt(user.getLastName(), row, 2);
		}
	}
	
	
	
	
}

//This method populates project table in users view
public void updateProjectTable () {
	
	Project project;
	allProjects = projectDB.getProjects(userID);
	
	projectModel.setRowCount(allProjects.size());
	
	for (int row = 0; row < allProjects.size(); row++) {
	project = allProjects.get(row);
	
	projectTable.getModel().setValueAt(project.getProjectName(), row, NAME_COL);
	projectTable.getModel().setValueAt(project.getStartTime(), row, STARTTIME_COL);
	projectTable.getModel().setValueAt(project.getWorkTime(), row, WORKTIME_COL);
	projectTable.getModel().setValueAt(project.getStatus(), row, STATUS_COL);
	projectTable.getModel().setValueAt(project.getProjectID(), row, ID_COL);
	}
	
}
//this table populates task table in user view
public void updateTaskTable(String projectID) {
	
	boolean started = true;
	Task task;

	allTasks = projectDB.getTasks(projectID);

	
	taskModel.setRowCount(allTasks.size());
	for (int row = 0; row < allTasks.size(); row++) {
		task = allTasks.get(row);
		taskTable.getModel().setValueAt(task.getTaskName(), row, NAME_COL);
		taskTable.getModel().setValueAt(task.getStartTime(), row, STARTTIME_COL);
		taskTable.getModel().setValueAt(task.getWorkTime(), row, WORKTIME_COL);
		taskTable.getModel().setValueAt(task.getTaskID(), row, ID_COL);
		

		/*
		 * messy.. should have put this in task class
		 */
		//If task isn't started status will be "Not Started"
		if (task.getStartTime() == null){
			taskTable.getModel().setValueAt("Not started", row, STATUS_COL);
			started = false;
			
		}// if it is started it and paused then status will be "Paused"
		if (started) {
			if(task.isPaused()){ 
				taskTable.getModel().setValueAt("Paused", row, STATUS_COL);
			}
			//If task is finished status will be "Finished"
			if (task.isFinished()) {
				taskTable.getModel().setValueAt("Finished", row, STATUS_COL);
			}// If task isn't paused status will be "In progress".
			else if (!task.isPaused()) {
				taskTable.getModel().setValueAt("In progress", row, STATUS_COL);
				
			}
		}
	}
	
	
}

	
//Gets tasks status from table
public String getTaskStatus() {
	
	return taskTable.getModel().getValueAt(taskTable.getSelectedRow(),STATUS_COL ).toString();
}
//Gets tasks ID from table
public String getTaskID() {
	return taskTable.getModel().getValueAt(taskTable.getSelectedRow(),ID_COL ).toString();
	
}
//Gets projects ID from table
public String getProjID() {
	return projectTable.getModel().getValueAt(projectTable.getSelectedRow(),ID_COL ).toString();
}

}
