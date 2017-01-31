<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page session="false" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Buy Fund</title>
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
	<h3>Buy Fund</h3>
	<form:form method="post" modelAttribute="buyFundForm">
		<table>
			<tr>
				<td> <form:errors path="" cssClass="error" /> </td>
			</tr>
			<tr>
				<td>Fund Ticker:</td>
				<td><input type="text" name="fundTicker"
						   value="${buyFundForm.fundTicker}" autofocus/>
				</td>
				<td><form:errors path="fundTicker" cssClass="error"/></td>
			</tr>

			<tr>
				<td>Available Amount:</td>
				<td>${buyFundForm.available}</td>
					<td><input type="hidden" step="0.01" name="Available"
						   value="${buyFundForm.available}"/>
				<td><form:errors path="available" cssClass="error"/></td>
			</tr>

			<tr>
				<td>Amount:</td>
				<td><input type="number" step="0.01" name="Amount"
						   value="${buyFundForm.amount}"/></td>
				<td><form:errors path="amount" cssClass="error"/></td>
			</tr>

			<tr>
				<th colspan="2">
					<input type="submit" name="button" value="Buy"/>
				</th>
			</tr>
			<tr>
				<th>
					<br/>
				</th>
			</tr>

		</table>
	</form:form>

				<h3>My Funds</h3>

<div class="row">
		<div class="col-md-6">
			<table class="table">
				<thead>
				<tr>
					<th>Fund Ticker</th>
					<th>Shares</th>
					<th>Price</th>
					<th>Value</th>
				</tr>
				</thead>
				<tbody>

				<c:forEach var="customer_pv" items="${customerPosition}">

					<tr>
						<td>
								${customer_pv.fund.ticker}
						</td>
						<td>
								${customer_pv.shares}
						</td>
						<td>
								${customer_pv.price}
						</td>
						<td>
								${customer_pv.value}
						</td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
	
	<h3>Available Funds</h3>
	
	<div class="row">
		<div class="col-md-6">
			<table class="table">
				<thead>
				<tr>
					<th>Fund Ticker</th>
					<th>Fund Name</th>
				</tr>
				</thead>
				<tbody>

				<c:forEach var="fund" items="${fundList}">

					<tr>
						<td>
								${fund.ticker}
						</td>
						<td>
								${fund.name}
						</td>

					</tr>
				</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
	
	
	
	
	
	</div>




<div>
	<c:import url="bottom.jsp"/>
</div>

</body>
</html>
