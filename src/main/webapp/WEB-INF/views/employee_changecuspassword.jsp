<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page session="false"%>

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
		<c:import url="/header" />
	</div>

	
	
<div class="container">
<h5>Reset Customer Password</h5>
<form:form modelAttribute="changePasswordForm">
<table>
		<tr>
			<td>Customer Username: </td>
			<td><input type="text" name="customer-name" value="" autofocus />
			</td>
		</tr>


		<tr>
			<td>New Password:</td>
			<td><input type="password" name="new-password" value="" /></td>
		</tr>

		<tr>
			<td>Confirm Password:</td>
			<td><input type="password" name="confirm-password" value="" /></td>
		</tr>
		
		<tr>
			<th><br/></th>
		</tr>
		<tr>
			<th><br/></th>
		</tr>
		
		<tr>
			<th colspan="2"><input type="submit" name="button"
				value="Change Password" /></th>
		</tr>

	</table>
	</form:form>
	</div>
	
	
	
	<div>
		<c:import url="bottom.jsp" />
	</div>
</body>

</html>
