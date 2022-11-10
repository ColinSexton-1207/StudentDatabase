package com.val.Web.JDBC;

import java.io.IOException;
import java.util.ArrayList;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import com.val.Student.StudentDBUtil;
import com.val.Student.StudentProperties;

@WebServlet("/StudentController")
public class StudentControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Resource(name="jdbc/test")
	private DataSource dataSource;
	
	private StudentDBUtil studentDBUtil;

    public StudentControllerServlet() { super(); }
    public void destroy() { super.destroy(); }
    
    @Override
    public void init() throws ServletException {
    	super.init();
    	
    	// Create Student DB Util - Pass in connection pool/DS
    	try {
    		studentDBUtil = new StudentDBUtil(dataSource);
    	} catch(Exception e) { throw new ServletException(e); }
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			// List students (MVC fashion)
			ListStudents(request, response);
		} catch(Exception e) { throw new ServletException(e); }
	}
	
	private void ListStudents(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// Get students from DBUtil
		ArrayList<StudentProperties> students = studentDBUtil.getStudents();
		
		// Add students to request object
		request.setAttribute("studentList", students);
		
		// Send to JSP page (view)
		RequestDispatcher disp = request.getRequestDispatcher("/StudentList.jsp");
		disp.forward(request, response);
	}
}
