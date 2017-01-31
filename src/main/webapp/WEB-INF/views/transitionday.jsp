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
	<label> Last Transition Date is: ${lastTransitionday}</label>
	<form:form method="post" modelAttribute="transitionForm">
		<label> Enter new Transition Date: </label>
		<input type="text" name="newDate" placeholder="dd/MM/yyyy"><br>
		<div class="form">
			<table class="table">
				<thead>
				<tr>
					<td>Fund Ticker</td>
					<td>Fund Name</td>
					<td>Current Price ($)</td>
					<td>Enter New Price</td>
				</tr>
				</thead>
				<tbody>
				<c:forEach var="listFund" items="${transitionForm.fundList}" varStatus="status">
					<tr>
						<td>${listFund.fund.ticker}</td>
						<td>${listFund.fund.name}</td>
						<td>${listFund.lastPrice}</td>
						<td>
							<form:input path="listFund[${status.index}].newPrice" type="number"
										step="0.01"
										value="${listFund.lastPrice}"/>
						</td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
		</div>
	</form:form>

	<button type="submit" class="btn btn-default">Submit</button>
</div>
<div>
	<c:import url="bottom.jsp"/>
</div>

</body>
</html>

