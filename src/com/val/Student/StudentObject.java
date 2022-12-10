package com.val.Student;

import java.sql.SQLException;

import com.val.ConnectionManager.ConnectionManager;

public class StudentObject {
	/* --- Object Variables --- */
	private StudentProperties properties = new StudentProperties();
	private StudentDataAccess dataAccess = null;
	
	/* --- Constructors --- */
	public StudentObject(ConnectionManager connectionManager) throws SQLException {
		dataAccess = new StudentDataAccess(connectionManager); // Initialize a Data Access object w/ current DB connection
	}
	
	public StudentObject(ConnectionManager connectionManager, Integer p_id) throws ClassNotFoundException, SQLException {
		dataAccess = new StudentDataAccess(connectionManager); // Initialize a Data Access object w/ current DB connection
		StudentProperties propertyKeys = new StudentProperties(); // Initialize new StudentProperties object based on KEY
		propertyKeys.setId(p_id); // Sets property KEY to ID
		properties = dataAccess.Select(propertyKeys); // With current DataAccess, select based on KEY
	}
	
	protected StudentObject(ConnectionManager connectionManagerBR, StudentProperties properties) throws ClassNotFoundException, SQLException {
		dataAccess = new StudentDataAccess(connectionManagerBR); // Initialize a Data Access object w/ current DB connection
		this.properties = properties;
	}
	
	public StudentProperties Properties() { return properties; }
	
	public boolean Save() throws ClassNotFoundException, SQLException {
		boolean saved = false;
		if(!saved) saved = dataAccess.Insert(properties);
		return saved;
	}
	
	public boolean Delete() throws ClassNotFoundException, SQLException {
		boolean deleted = dataAccess.Delete(properties);
		if(deleted) properties = new StudentProperties();
		return deleted;
	}
}
