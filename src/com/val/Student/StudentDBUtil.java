package com.val.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
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
	
	public StudentProperties getStudent(String studentId) throws Exception {
		StudentProperties student = null;
		int studId;
		
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		
		try {
			// Convert studentId to int
			studId = Integer.parseInt(studentId);
			
			// Get connection to database
			connection = dataSource.getConnection();
			
			// Create SQL to get selected student
			String sql = "select * from student where id=?";
			
			// Create prepared statement
			statement = connection.prepareStatement(sql);
			
			// Set params
			statement.setInt(1, studId);
			
			// Execute statement
			resultSet = statement.executeQuery();
			
			// Retrieve data from ResultSet
			if(resultSet.next()) {
				String firstName = resultSet.getString("firstName");
				String lastName = resultSet.getString("lastName");
				String email = resultSet.getString("email");
				
				// Use studentId during construction
				student = new StudentProperties(studId, firstName, lastName, email);
			}
			else {
				throw new Exception("Could not find a student with id: " + studId);
			}
			
			return student;
		} finally {
			// Clean up JDBC objects
			CloseConnection(connection, statement, resultSet);
		}
	}
	
	public void addStudent(StudentProperties student) throws Exception {
		 // Create SQL for insert
		Connection connection = null;
		PreparedStatement statement = null;
		
		try {
			// Get DB connection
			connection = dataSource.getConnection();
			
			// Set param values for student
			String sql = "insert into student " + "(firstName, lastName, email) " + "values (?, ?, ?)";
			statement = connection.prepareStatement(sql);
			
			statement.setString(1, student.getFirstName());
			statement.setString(2, student.getLastName());
			statement.setString(3, student.getEmail());
			
			// Execute SQL insert
			statement.execute();
		} finally {
			// Clean up JDBC objects
			CloseConnection(connection, statement, null);
		}
	}
	
	public void updateStudent(StudentProperties student) throws Exception {
		Connection connection = null;
		PreparedStatement statement = null;
		
		try {
			// Get DB Connection
			connection = dataSource.getConnection();
			
			// Create SQL update statement
			String sql = "update student " + "set firstName=?, lastName=?, email=? " + "where id=?";
			
			// Prepare statement
			statement = connection.prepareStatement(sql);
			
			// Set params
			statement.setString(1, student.getFirstName());
			statement.setString(2, student.getLastName());
			statement.setString(3, student.getEmail());
			statement.setInt(4, student.getId());
			
			// Execute statement
			statement.execute();
		} finally {
			CloseConnection(connection, statement, null);
		}
	}
	
	public void deleteStudent(String studentId) throws Exception {
		Connection connection = null;
		PreparedStatement statement = null;
		
		try {
			// Convert student id to int
			int studId = Integer.parseInt(studentId);
			
			// Get connection to database
			connection = dataSource.getConnection();
			
			// Create SQL to delete student
			String sql = "delete from student where id=?";
			
			// Prepare statement
			statement = connection.prepareStatement(sql);
			
			// Set params
			statement.setInt(1, studId);
			
			// Execute SQL statement
			statement.execute();
		} finally {
			CloseConnection(connection, statement, null);
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
