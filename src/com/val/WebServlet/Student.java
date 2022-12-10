package com.val.WebServlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.val.ConnectionManager.ConnectionManager;
import com.val.ConnectionManager.ConnectionManager.ConnectionType;
import com.val.Student.StudentFactory;
import com.val.Student.StudentObject;

public class Student extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public Student() { super(); }
	public void destroy() { super.destroy(); }
	public void init() throws ServletException {}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			ConnectionManager connectionManager = new ConnectionManager(ConnectionType.DEV);
			connectionManager.OpenConnection();
			
			StudentFactory studentFactory = new StudentFactory(connectionManager);
			studentFactory.LoadStudentList();
			request.setAttribute("studentList",  studentFactory.StudentList());
			
			connectionManager.CloseConnection();
			
			RequestDispatcher disp = getServletContext().getRequestDispatcher("/students");
			disp.include(request,  response);
		} catch(Exception e) { response.sendRedirect("/students?error"); }
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			ConnectionManager connectionManager = new ConnectionManager(ConnectionType.DEV);
			
			if(request.getParameter("addStudent") != null && request.getParameter("addStudent").compareTo("addStudent") == 0) {
				response.sendRedirect("/add-student");
			}
			if(request.getParameter("command") != null && request.getParameter("command").compareTo("loadStudent") == 0) {
				response.sendRedirect("/update-student");
			}
			if(request.getParameter("command") != null && request.getParameter("command").compareTo("deleteStudent") == 0) {
				connectionManager.OpenConnection();
				StudentObject studentObject = new StudentObject(connectionManager);
				studentObject.Properties().setId	(Integer.valueOf(request.getParameter("p_id")));
				studentObject.Delete();
				connectionManager.CloseConnection();
			}
			response.sendRedirect(request.getRequestURL().toString());
		} catch(Exception e) { response.sendRedirect("/students?error"); }
	}
	
	public void DeleteStudent(HttpServletRequest request, HttpServletResponse response) {
		String studentId = request.getParameter("p_id");
		
		
	}
}
