<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<title>Transition Day</title>
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
	<h1>Transition Day</h1>
	<label> Current Date is: </label>
	<%
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		Date date = new Date();
		out.println(df.format(date));
	%>
	<form:form method="post" modelAttribute="transitionForm">
		<label> Enter Date(dd/MM/yyyy): </label>
		<input type="text" name="date" placeholder="dd/MM/yyyy"><br>


		<p>
		<div class="form">
			<table>
				<thead>
				<tr>
					<td><b>FundId</b></td>
					<td><b>Fund</b></td>
					<td><b>Fund Symbol</b></td>
					<td><b>Current Price ($)</b></td>
					<td><b>Enter New Price</b></td>
				</tr>
				</thead>
				<tbody>
				<c:forEach var="listFund" items="${listFund}">
					<tr>
						<td>${listFund.getFundId()}</td>
						<td>${listFund.getName()}</td>
						<td>${listFund.getSymbol()}</td>
						<td>${listFund.getPrice()}</td>
						<td><input type="text" name="newPrice"/></td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
		</div>
		</p>
	</form:form>

	<button type="submit" class="btn btn-default">Submit</button>
</div>
<div>
	<c:import url="bottom.jsp"/>
</div>

</body>
</html>

