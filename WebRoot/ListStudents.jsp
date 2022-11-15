<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
	<title>Student Database App</title>
	
	<link type="text/css" rel="stylesheet" href="css/style.css"/>
</head>
<body>
	<div id="wrapper">
		<div id="header">
			<h2>Random University</h2>
		</div>
	</div>
	<div id="container">
		<div id="content">
			<input type="button" value="Add Student" onclick="window.location.href='AddStudentForm.jsp'; return false" class="add-student-button"/>
			<table>
				<tr>
					<th>First Name</th>
					<th>Last Name</th>
					<th>Email</th>
					<th>Action</th>
				</tr>
				<c:forEach var="tempStudent" items="${studentList}">
				<c:url var="tempLink" value="StudentControllerServlet">
					<c:param name="command" value="loadStudents"/>
					<c:param name="studentId" value="${tempStudent.id}"/>
				</c:url>
				<c:url var="deleteLink" value="StudentControllerServlet">
					<c:param name="command" value="deleteStudent"/>
					<c:param name="studentId" value="${tempStudent.id}"/>
				</c:url>
					<tr>
						<td>${tempStudent.firstName}</td>
						<td>${tempStudent.lastName}</td>
						<td>${tempStudent.email}</td>
						<td>
							<a href="${tempLink}">Update</a> |
							<a href="${deleteLink}" onclick="if(!(confirm('Are you sure you want to delete this student'))) return false">Delete</a>
						</td>
					</tr>
				</c:forEach>
			</table>
		</div>
	</div>
</body>
</html>