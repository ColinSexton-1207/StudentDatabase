<html>
<head>
	<title>Add Student Form</title>
	
	<link type="text/css" rel="stylesheet" href="css/style.css">
	<link type="text/css" rel="stylesheet" href="css/addStudentStyle.css">
</head>
<body>
	<div id="wrapper">
		<div id="header">
			<h2>Random University</h2>
		</div>
	</div>
	<div id="container">
		<h3>Update Student</h3>
		<form action="StudentControllerServlet" method="GET">
			 <input type="hidden" name="command" value="updateStudent"/>
			 <input type="hidden" name="studentId" value="${student.id}"/>
			 <table>
			 	<tbody>
			 		<tr>
			 			<td><label>First Name:</label></td>
			 			<td><input type="text" name="firstName" value="${student.firstName}"/></td> <!--  student comes from servlet in LoadStudent method setAttribute('student', student) -->
			 		</tr>
			 		<tr>
			 			<td><label>Last Name:</label></td>
			 			<td><input type="text" name="lastName" value="${student.lastName}"/></td>
			 		</tr>
			 		<tr>
			 			<td><label>Email:</label></td>
			 			<td><input type="text" name="email" value="${student.email}"/></td>
			 		</tr>
			 		<tr>
			 			<td><label></label></td>
			 			<td><input type="submit" value="Save" class="save"/></td>
			 		</tr>
			 	</tbody>
			 </table>
		</form>
		<div style="clear: both;"></div>
		<p>
			<a href="StudentControllerServlet">Back to Student List</a>
		</p>
	</div>
</body>
</html>