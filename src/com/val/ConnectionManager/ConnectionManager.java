package com.val.ConnectionManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
	/* --- Connection Variables --- */
	private String key;
	private String driver;
	private String url;
	private String user;
	private String password;
	private String email;
	private String temp;
	private String mailserver;
	
	/* --- Connection Variable --- */
	private Connection connection = null;
	
	/* --- Getters/Setters --- */
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	
	public String getDriver() {
		return driver;
	}
	public void setDriver(String driver) {
		this.driver = driver;
	}
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getTemporaryFilePath() {
		return temp;
	}
	public void setTemporaryFilePath(String temp) {
		this.temp = temp;
	}
	
	public String getMailServer() {
		return mailserver;
	}
	public void setMailServer(String mailserver) {
		this.mailserver = mailserver;
	}
	
	public Connection Connection() {
		return connection;
	}
	
	/* --- Connection Types --- */
	public enum ConnectionType {
		DEV,
		PROD
	}
	
	/* --- Constructor --- */
	public ConnectionManager(ConnectionType connectionType) {
		switch(connectionType) {
			case DEV:
				setKey("Development");
				setDriver("com.mysql.cj.jdbc.Driver");
				setUrl("jdbc:mysql://localhost:3306/test?useSSL=false&amp;serverTimezone=UTC");
				setUser("DeveloperTest");
				setPassword("Test123!");
				setEmail("@valenterprises.org");
				setTemporaryFilePath("\\tmp\\");
				setMailServer("smtp.gmail.com");
				break;
			case PROD:
				setKey("Production");
				setDriver("com.mysql.cj.jdbc.Driver");
				setUrl("jdbc:mysql://localhost:3306/test?useSSL=false&amp;serverTimezone=UTC");
				setUser("DeveloperTest");
				setPassword("Test123!");
				setEmail("@valenterprises.org");
				setTemporaryFilePath("\\tmp\\");
				setMailServer("smtp.gmail.com");
				break;
			default:
				break;
		}
	}
	
	/* 
	 * Method: OpenConnection
	 * Access: Public
	 * Description: When called, opens connection to the database
	 */
	public void OpenConnection() throws ClassNotFoundException, SQLException {
		Class.forName(getDriver());
		
		if(getUser().trim().length() == 0) // If lacking user information, attempt to open a connection using URL data
			connection = DriverManager.getConnection(getUrl()); // User information probably in URL, open connection
		else
			connection = DriverManager.getConnection(getUrl(), getUser(), getPassword()); // Establish connection w/ DB using URL, User, and Password variables
	}
	
	/* 
	 * Method: CloseConnection
	 * Access: Public
	 * Description: When called, closes connection to the database
	 */
	public void CloseConnection() throws SQLException {
		if(connection != null)
			connection.close();
	}
}
