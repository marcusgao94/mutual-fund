<%--
  Created by IntelliJ IDEA.
  User: marcusgao
  Date: 17/1/19
  Time: 下午9:32
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<title>Employee register</title>
	<style>
		.error {
			color: red;
		}
	</style>
</head>
<body>
<div>
	<c:import url="/header"/>
</div>
<div class="container">

	<h3>Employee Register</h3>

	<form:form method="POST" modelAttribute="employeeForm">
		<table>


			<tr>
				<td>User Name:</td>
				<td>
					<input type="text" name="userName"
						   value="${employeeForm.getUserName()}"/>
				</td>
				<td>
					<form:errors path="userName" cssClass="error"/>
				</td>
			</tr>

			<tr>
				<td>Password:</td>
				<td>
					<input type="password" name="password" value=""/>
				</td>
				<td>
					<form:errors path="password" cssClass="error"/>
				</td>
			</tr>

			<tr>
				<td>Confirm Password:</td>
				<td>
					<input type="password" name="confirmPassword" value=""/>
				</td>
				<td>
					<form:errors path="confirmPassword" cssClass="error"/>
				</td>
			</tr>

			<tr>
				<td>First Name:</td>
				<td>
					<input type="text" name="firstName"
						   value="${employeeForm.getFirstName()}" autofocus/>
				</td>
				<td>
					<form:errors path="firstName" cssClass="error"/>
				</td>
			</tr>

			<tr>
				<td>Last Name:</td>
				<td>
					<input type="text" name="lastName"
						   value="${employeeForm.getLastName()}"/>
				</td>
				<td>
					<form:errors path="lastName" cssClass="error"/>
				</td>
			</tr>

			<tr>
				<th colspan="2">
					<input type="submit" name="button" value="register"/>
				</th>
			</tr>
		</table>
	</form:form>
</div>
<div>
	<c:import url="bottom.jsp"/>
</div>

</body>
</html>
