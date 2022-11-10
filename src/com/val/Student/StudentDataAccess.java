package com.val.Student;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import com.val.ConnectionManager.ConnectionManager;

public class StudentDataAccess {
	ConnectionManager connectionManager;
	CallableStatement callableStatement;
	ResultSet resultSet;
	private ArrayList<HashMap<String, String>> arraylist;
	
	
	
	protected ArrayList<HashMap<String, String>> SelectList() throws ClassNotFoundException, SQLException {
		callableStatement = connectionManager.Connection().prepareCall(SELECTLIST); // Establish connection w/ DB and call stored DB procedure
		resultSet = callableStatement.executeQuery();
	}
}
