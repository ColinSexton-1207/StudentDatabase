package com.val.Student;

import java.sql.SQLException;

import com.val.ConnectionManager.ConnectionManager;

public class StudentObject {
	/* --- Object Variables --- */
	private StudentProperties properties = new StudentProperties();
	private StudentDataAccess dataAccess = null;
	
	/* --- Constructors --- */
	public StudentObject(ConnectionManager connectionManager) throws SQLException {
		dataAccess = new StudentDataAccess(connectionManager);
	}
	
	public StudentObject(ConnectionManager connectionManager, Integer p_id) throws ClassNotFoundException, SQLException {
		dataAccess = new StudentDataAccess(connectionManager);
		StudentProperties propertyKeys = new StudentProperties();
		propertyKeys.setId(p_id);
		properties = dataAccess.Select(propertyKeys);
	}
	
	protected StudentObject(ConnectionManager connectionManagerBR, StudentProperties properties) throws ClassNotFoundException, SQLException {
		dataAccess = new StudentDataAccess(connectionManagerBR);
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
