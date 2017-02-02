<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page session="false" %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Change Password</title>
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

<h5>Reset Customer Password</h5>
<form:form modelAttribute="changePasswordForm">
	<table class="table">
		<tr>
			<td>Customer User Name:</td>
			<td><select name="userName" class="form-control" id="sel1">
			<option value="">Please select a name
			</option>
			<c:forEach var="customer" items="${customerList}">
				<option>${customer.userName}</option>
			</c:forEach>
		</select>
		<form:errors path="userName" cssClass="error"/>
		</td>
		</tr>

		<tr>
			<td>New Password:</td>
			<td><input type="password" name="newPassword" value=""/></td>
			<td><form:errors path="newPassword" cssClass="error" /></td>
		</tr>

		<tr>
			<td>Confirm Password:</td>
			<td><input type="password" name="confirmNewPassword" value=""/></td>
			<td><form:errors path="confirmNewPassword" cssClass="error" /></td>
		</tr>

		<tr>
			<td>
				<input type="submit" value="Reset Password"/>
			</td>
			<td>
				<input type="hidden" name="fast" value="fast" />
			</td>
		</tr>
	</table>
</form:form>
<div>
	<c:import url="bottom.jsp"/>
</div>
</body>

</html>
