package com.val.WebServlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UpdateStudent extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public UpdateStudent() { super(); }
	public void destroy() { super.destroy(); }
	public void init() throws ServletException {}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			
		} catch(Exception e) { response.sendRedirect("/update-student?error"); }
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}
}
