package com.val.Student;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import com.val.ConnectionManager.ConnectionManager;

public class StudentFactory {
	StudentDataAccess studentDataAccess = null; // Initialize StudentDataAccess object
	private ArrayList<HashMap<String, String>> studentList = new ArrayList<HashMap<String, String>>(); // Create and initialize list of students
	
	/* --- Constructor --- */
	public StudentFactory(ConnectionManager connectionManager) throws ClassNotFoundException, SQLException {
		studentDataAccess = new StudentDataAccess(connectionManager); // Connect to StudentDataAccess with current connection type
	}
	
	/* 
	 * Method: LoadStudentList
	 * Access: Public
	 * Description: Loads the studentList arraylist with input data from database using SelectList stored procedure
	 */
	public void LoadStudentList() throws ClassNotFoundException, SQLException {
		studentList = studentDataAccess.SelectList(); // Selects list of students from table in DB and loads it into an ArrayList
	}
	
	/* 
	 * Method: StudentList
	 * Access: Public
	 * Description: Returns the current list of students
	 */
	public ArrayList<HashMap<String, String>> StudentList() { return studentList; }
}
