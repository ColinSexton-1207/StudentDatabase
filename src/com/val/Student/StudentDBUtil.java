package com.val.Student;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.sql.DataSource;

public class StudentDBUtil {
	private DataSource dataSource;
	
	public StudentDBUtil(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public ArrayList<StudentProperties> getStudents() throws Exception {
		ArrayList<StudentProperties> students = new ArrayList<>();
		
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		
		try {
			// Get Connection
			connection = dataSource.getConnection();
			
			// Create SQL Statement
			String sql = "select * from student order by lastName";
			statement = connection.createStatement();
			
			// Execute Query
			resultSet = statement.executeQuery(sql);
			
			// Process ResultSet
			while(resultSet.next()) {
				// Retrieve data from ResultSet row
				int id = resultSet.getInt("id");
				String firstName = resultSet.getString("firstName");
				String lastName = resultSet.getString("lastName");
				String email = resultSet.getString("email");
				
				// Create a new Student object
				StudentProperties tempStudent = new StudentProperties(id, firstName, lastName, email);
				
				// Add it to list of students
				students.add(tempStudent);
			}
		
			return students;
		} finally {
			// Close JDBC objects
			CloseConnection(connection, statement, resultSet);
		}
	}
	
	private void CloseConnection(Connection connection, Statement statement, ResultSet resultSet) {
		try {
			if(resultSet != null) 
				resultSet.close();
			if(statement != null)
				statement.close();
			if(connection != null)
				connection.close(); // Doesn't actually close connection, just opens up pool
		} catch(Exception e) { e.printStackTrace(); }
	}
}
