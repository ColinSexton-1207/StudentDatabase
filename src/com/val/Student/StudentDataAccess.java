package com.val.Student;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;

import com.val.ConnectionManager.ConnectionManager;

public class StudentDataAccess {
	/* --- Stored Procedures --- */
	private final String INSERT		= "{call DeveloperTest.Student_Insert(?, ?, ?, ?)}";
	private final String UPDATE		= "{call DeveloperTest.Student_Insert(?, ?, ?, ?)}";
	private final String DELETE		= "{call DeveloperTest.Student_Insert(?)}";
	private final String SELECT		= "{call DeveloperTest.Student_Insert(?)}";
	private final String SELECTLIST	= "{call DeveloperTest.Student_SelectList()}";
	
	/* --- JDBC Variables --- */
	ConnectionManager connectionManager;
	CallableStatement callableStatement;
	ResultSet resultSet;
	
	private ArrayList<HashMap<String, String>> arraylist;
	
	/* --- Constructor --- */
	protected StudentDataAccess(ConnectionManager connectionManager) { this.connectionManager = connectionManager; }
	
	/* 
	 * Method: BuildArrayList
	 * Access: Protected
	 * Description: Creates an ArrayList with nested HashMap of data within the database
	 */
	protected void BuildArrayList() throws ClassNotFoundException, SQLException {
		arraylist = new ArrayList<HashMap<String, String>>(); // Initialize the arraylist of rows
		while(resultSet.next()) { // While there is something to execute...
			HashMap<String, String> colArrayList = new HashMap<String, String>();
			ResultSetMetaData rsmd = resultSet.getMetaData(); // Get column information
			for(int i = 1; i <= rsmd.getColumnCount(); i++) // Start at column 1, run until last column is reached
				// Add to HashMap<columnName, ternary decision if 
				colArrayList.put(rsmd.getColumnName(i), (resultSet.getObject(rsmd.getColumnName(i)) == null ? null : resultSet.getString(rsmd.getColumnName(i))));
			arraylist.add(colArrayList); // Add data to arraylist
		}
	}
	
	/* 
	 * Method: CreateProperties
	 * Access: Private
	 * Description: Creates properties for the Student object based on ResultSet data from the table
	 */
	private StudentProperties CreateProperties() throws SQLException {
		StudentProperties properties = new StudentProperties();
		
		// Create properties for each variable column
		// SetProperty if with ResultSet input if not null
		properties.setId			(resultSet.getObject("id") 			== null ? null : resultSet.getInt	("id"));
		properties.setFirstName		(resultSet.getObject("firstName") 	== null ? null : resultSet.getString("firstName"));
		properties.setLastName		(resultSet.getObject("lastName")	== null ? null : resultSet.getString("lastName"));
		properties.setEmail			(resultSet.getObject("email")		== null ? null : resultSet.getString("email"));
		
		return properties;
	}
	
	/* 
	 * Method: SetParameterKeys
	 * Access: Private
	 * Description: Sets the KEY for an entry to the table
	 */
	private void SetParameterKeys(StudentProperties properties) throws SQLException {
		// If input data doesn't exist, set variable to null
		// Else set the data as (type) and read in data value
		if(properties.getId()			== null) callableStatement.setNull("p_id"		, Types.INTEGER	); else callableStatement.setInt("p_id"				, properties.getId());
	}
	
	/* 
	 * Method: SetParameters
	 * Access: Private
	 * Description: Sets the VALUES for an entry to the table
	 */
	private void SetParameters(StudentProperties properties) throws SQLException {
		// If input data doesn't exist, set variable to null
		// Else set the data as (type) and read in data value
		if(properties.getFirstName()	== null) callableStatement.setNull("p_firstName", Types.VARCHAR	); else callableStatement.setString("p_firstName"	, properties.getFirstName());
		if(properties.getLastName()		== null) callableStatement.setNull("p_lastName"	, Types.VARCHAR	); else callableStatement.setString("p_lastName"	, properties.getLastName());
		if(properties.getEmail()		== null) callableStatement.setNull("p_email"	, Types.VARCHAR	); else callableStatement.setString("p_email"		, properties.getEmail());
	}

	/* 
	 * JDBC Function: Insert
	 * Access: Protected
	 * Description: Calls stored INSERT procedure in the database to insert a new entry into specified table
	 */
	protected boolean Insert(StudentProperties properties) throws ClassNotFoundException, SQLException {
		callableStatement = connectionManager.Connection().prepareCall(INSERT); // Establish connection w/ DB and call stored procedure
		SetParameterKeys(properties); // Setup insertion point with data key
		SetParameters(properties); // Setup entry with data for each column
		int retval = callableStatement.executeUpdate(); // Execute statement, store status
		callableStatement.close(); // Close statement
		if(retval != 0) return true; // Statement executed successfully
		return false; // Statement failed to execute
	}
	
	/* 
	 * JDBC Function: Update
	 * Access: Protected
	 * Description: Calls stored UPDATE procedure in the database to update an existing entry in the specified table
	 */
	protected boolean Update(StudentProperties properties) throws ClassNotFoundException, SQLException {
		callableStatement = connectionManager.Connection().prepareCall(UPDATE); // Establish connection w/ DB and call stored procedure
		SetParameterKeys(properties); // Setup insertion point with data key
		SetParameters(properties); // Setup entry with data for each column
		int retval = callableStatement.executeUpdate(); // Execute statement, store status
		callableStatement.close(); // Close statement
		if(retval != 0) return true; // Statement executed successfully
		return false; // Statement failed to execute
	}
	
	/* 
	 * JDBC Function: Delete
	 * Access: Protected
	 * Description: Calls stored DELETE procedure in the database to delete an existing entry in the specified table
	 */
	protected boolean Delete(StudentProperties properties) throws ClassNotFoundException, SQLException {
		callableStatement = connectionManager.Connection().prepareCall(DELETE); // Establish connection w/ DB and call stored procedure
		SetParameterKeys(properties); // Setup insertion point with data key
		int retval = callableStatement.executeUpdate(); // Execute statement, store status
		callableStatement.close(); // Close statement
		if(retval != 0) return true; // Statement executed successfully
		return false; // Statement failed to execute
	}
	
	/* 
	 * JDBC Function: Select
	 * Access: Protected
	 * Description: Calls stored SELECT procedure in the database that will select an existing entry in the specified table by an ID
	 */
	protected StudentProperties Select(StudentProperties properties) throws ClassNotFoundException, SQLException {
		callableStatement = connectionManager.Connection().prepareCall(SELECT); // Establish connection w/ DB and call stored procedure
		SetParameterKeys(properties); // Setup insertion point with data key
		properties = new StudentProperties(); // Initialize a new Student object
		resultSet = callableStatement.executeQuery(); // Execute statement
		while(resultSet.next()) { properties = CreateProperties(); } // While data exists, fill in new Student object with selected properties (grabs student w/o modifying table entry)
		callableStatement.close(); // Close statement
		return properties; // Return properties of selected student
	}
	
	/* 
	 * JDBC Function: SelectList
	 * Access: Protected
	 * Description: Calls stored SELECTLIST procedure in the database that selects entries from specified table
	 */
	protected ArrayList<HashMap<String, String>> SelectList() throws ClassNotFoundException, SQLException {
		callableStatement = connectionManager.Connection().prepareCall(SELECTLIST); // Establish connection w/ DB and call stored procedure
		resultSet = callableStatement.executeQuery(); // Execute statement
		BuildArrayList(); // Builds arraylist of selected values
		callableStatement.close(); // Close statement
		return arraylist; // Return list of entries from the table
	}
}
