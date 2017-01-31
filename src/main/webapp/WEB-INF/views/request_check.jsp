<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page session="false" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Request Check</title>
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
	<h3>Request Check</h3>
	<form:form method="post" modelAttribute="requestCheckForm">
		<table>
			<tr>
				<td>Customer UserName:</td>
				<td>
					<span style = "font-size:large">${requestCheckForm.userName}</span>
					<input type="hidden" name="userName" value="${requestCheckForm.userName}">
				</td>
				<td><form:errors path="userName" cssClass="error"/></td>
			</tr>

			<tr>
				<td>Available Amount:</td>
				<td><span>${requestCheckForm.available}</span> </td>
				<td><input type="hidden" name="available" value="${requestCheckForm.available}"></td>
				<td><form:errors path="available" cssClass="error"/></td>
			</tr>

			<tr>
				<td>Amount:</td>
				<td><input type="number" step="0.01" name="amount"
						   value="${requestCheckForm.amount}"/></td>
				<td><form:errors path="amount" cssClass="error"/></td>
			</tr>

			<tr>
				<th colspan="2">
					<input type="submit" name="button" value="Request"/>
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
