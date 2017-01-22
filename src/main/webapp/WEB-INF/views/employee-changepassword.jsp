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

	<h5>Change Password</h5>
	<table>

		<tr>
			<td>Old Password:</td>
			<td><input type="password" name="old-password" value=""
				autofocus /></td>
		</tr>

		<tr>
			<td>New Password:</td>
			<td><input type="password" name="new-password" value=""
				autofocus /></td>
		</tr>

		<tr>
			<td>Confirm Password:</td>
			<td><input type="password" name="confirm-password" value="" /></td>
		</tr>


		<tr>
			<th colspan="2"><input type="submit" name="button"
				value="Change Password" /></th>
		</tr>

	</table>
	<div>
		<c:import url="bottom.jsp" />
	</div>
</body>

</html>