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

@WebServlet("/StudentControllerServlet")
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
			// Read command parameter
			String command = request.getParameter("command");
			
			// If command is missing, default to listing students
			if(command == null) command = "listStudents";
			
			// Route to appropriate method
			switch(command) {
				case "listStudents":
					ListStudents(request, response);
					break;
				case "addStudent":
					AddStudent(request, response);
					break;
				case "loadStudents":
					LoadStudent(request, response);
					break;
				case "updateStudent":
					UpdateStudent(request, response);
					break;
				case "deleteStudent":
					DeleteStudent(request, response);
					break;
				default:
					ListStudents(request, response);
					break;
			}
			
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
		RequestDispatcher disp = request.getRequestDispatcher("/ListStudents.jsp");
		disp.forward(request, response);
	}
	
	private void AddStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		 // Read student info from form data
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email"); 
		
		// Create new student object;
		StudentProperties student = new StudentProperties(firstName, lastName, email);
		
		// Add student to database
		studentDBUtil.addStudent(student);
		
		// Send back to main page (ListStudents.jsp)
		ListStudents(request, response);
	}
	
	private void LoadStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// Read student id from form data
		String studentId = request.getParameter("studentId");
		
		// Get student from database (DBUtil)
		StudentProperties student = studentDBUtil.getStudent(studentId);
		
		// Place student in the request attribute
		request.setAttribute("student", student);
		
		// Send to JSP page: UpdateStudentForm.jsp
		RequestDispatcher disp = request.getRequestDispatcher("/UpdateStudentForm.jsp");
		disp.forward(request, response);
	}
	
	private void UpdateStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// Read Student info from form data
		int id = Integer.parseInt(request.getParameter("studentId"));
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		
		// Create a new student object
		StudentProperties student = new StudentProperties(id, firstName, lastName, email);
		
		// Perform update on database
		studentDBUtil.updateStudent(student);
		
		// Send them back to ListStudents page
		ListStudents(request, response);
	}
	
	private void DeleteStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// Read student id from form data
		String studentId = request.getParameter("studentId");
		
		// Delete student from database
		studentDBUtil.deleteStudent(studentId);
		
		// Send user back to list students page
		ListStudents(request, response);
	}
}
