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
</head>
<body>
<h5>Employee Register</h5>

<form:form method="POST" modelAttribute="employee">
	<table>
		<c:if test="${not empty message}">
			<tr>
				<td style="color: red">
						${message}
				</td>
			</tr>
		</c:if>

		<tr>
			<td>First Name:</td>
			<td>
				<input type="text" name="firstName" value="" autofocus/>
			</td>
		</tr>

		<tr>
			<td>Last Name:</td>
			<td>
				<input type="text" name="lastName" value=""/>
			</td>
		</tr>

		<tr>
			<td>User Name:</td>
			<td>
				<input type="text" name="userName" value=""/>
			</td>
		</tr>

		<tr>
			<td>Password:</td>
			<td>
				<input type="password" name="password" value=""/>
			</td>
		</tr>

		<tr>
			<td>Confirm Password:</td>
			<td>
				<input type="password" name="confirmpassword" value=""/>
			</td>
		</tr>

		<tr>
			<th colspan="2">
				<input type="submit" name="button" value="register"/>
			</th>
		</tr>
	</table>
</form:form>


</body>
</html>
