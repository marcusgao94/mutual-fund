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
	<title>Customer register</title>
	<style>
		.error {
			color: red;
		}
	</style>
</head>
<body>
<div>
	<jsp:include page="/header"/>
</div>


<div class="container">

	<h3>Customer Register</h3>

	<form:form method="POST" modelAttribute="customerForm">
		<table>


			<tr>
				<td>User Name:</td>
				<td>
					<input type="text" name="userName"
						   value="${customerForm.getUserName()}"/>
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
				<td><input type="password" name="confirmPassword" value=""/>
				</td>
				<td>
					<form:errors path="confirmPassword" cssClass="error"/>
				</td>
			</tr>

			<tr>
				<td>First Name:</td>
				<td>
					<input type="text" name="firstName"
						   value="${customerForm.getFirstName()}" autofocus/>
				</td>
				<td>
					<form:errors path="firstName" cssClass="error"/>
				</td>
			</tr>

			<tr>
				<td>Last Name:</td>
				<td>
					<input type="text" name="lastName"
						   value="${customerForm.getLastName()}"/>
				</td>
				<td>
					<form:errors path="lastName" cssClass="error"/>
				</td>
			</tr>

			<tr>
				<td>Address Line1:</td>
				<td>
					<input type="text" name="addr_line1"
						   value="${customerForm.getAddr_line1()}"/>
				</td>
			</tr>

			<tr>
				<td>Address Line2:</td>
				<td>
					<input type="text" name="addr_line2"
						   value="${customerForm.getAddr_line2()}"/>
				</td>
			</tr>

			<tr>
				<td>City:</td>
				<td>
					<input type="text" name="city"
						   value="${customerForm.getCity()}"/>
				</td>
				<td>State:</td>
				<td>
					<input type="text" name="state"
						   value="${customerForm.getState()}"/>
				</td>
				<td>Zipcode:</td>
				<td>
					<input type="number" name="zip"
						   value="${customerForm.getZip()}"/>
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
	<jsp:include page="bottom.jsp"/>
</div>

</body>
</html>
