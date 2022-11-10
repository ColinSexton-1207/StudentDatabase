package com.val.Web.JDBC;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

@WebServlet("/TestServlet")
public class TestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	// Define datasource/connection pool for Resource Injection
	@Resource(name="jdbc/test")
	private DataSource dataSource;
       
    public TestServlet() { super(); }
    public void destroy() { super.destroy(); }
    public void init() throws ServletException {}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Set content type
		response.setContentType("text/plain");
		
		// Setup PrintWriter
		PrintWriter pOut = response.getWriter();
		
		// Get connection to database
		Connection conn = null;
		Statement stmnt = null;
		ResultSet rs = null;
		
		try {
			// Open Connection
			conn = dataSource.getConnection();
			
			// Create an SQL statement
			String sql = "select * from Student";
			stmnt = conn.createStatement();
			
			// Execute SQL query
			rs = stmnt.executeQuery(sql);
			
			// Process the ResultSet
			while(rs.next()) {
				String email = rs.getString("email");
				
				pOut.println(email); // Test output
			}
		} catch(Exception e) { e.printStackTrace(); }
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}
