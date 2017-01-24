<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page session="false" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Sell Fund</title>
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
	<h3>Sell Fund</h3>
	<form:form method="post" modelAttribute="sellFundForm">
		<table>
			<tr>
				<td> <form:errors path="" cssClass="error" /> </td>
			</tr>
			<tr>
				<td>Fund ticker:</td>
				<td><input type="text" name="fundTicker"
						   value="${sellFundForm.fundTicker}" autofocus/>
				</td>
				<td><form:errors path="fundTicker" cssClass="error"/></td>
			</tr>

			<tr>
				<td>share:</td>
				<td><input type="number" step="0.001" name="share"
						   value="${sellFundForm.share}"/></td>
				<td><form:errors path="share" cssClass="error"/></td>
			</tr>

			<tr>
				<th colspan="2">
					<input type="submit" name="button" value="buy"/>
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
