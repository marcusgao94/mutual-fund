<%--
  Created by Eclipse IDEA.
  User: jev
  Date: 17/1/23
  Time: 9:32
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<title>Customer transaction history</title>
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

	<h3>Customer Transaction History</h3>
<h4> Pending </h4>
<table>
	<thead>
		<tr>
			<th>ID</th>
			<th>Name</th>
			<th>Shares</th>
			<th>Amount</th>
          	<th>Operations</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach var="customer_pendingtransaction" items="${customer_pendingTransaction}">
			<tr>
				<td>
	 				${customer_pendingtransaction.id}
				</td>
				<td>
	 				${customer_pendingtransaction.fund.name}
				</td>
				<td>
	 				${customer_pendingtransaction.shares}
				</td>
				<td>
	 				${customer_pendingtransaction.amount}
				</td>
				<td>
	 				${customer_pendingtransaction.type}
				</td>
			</tr>
		</c:forEach>
	<tbody>
</table>

<h4> Finished </h4>
<table>
	<thead>
		<tr>
			<th>ID</th>
			<th>Name</th>
			<th>Shares</th>
			<th>Amount</th>
          	<th>Operations</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach var="customer_finishtransaction" items="${customer_finishTransaction}">
			<tr>
				<td>
	 				${customer_finishtransaction.id}
				</td>
				<td>
	 				${customer_finishtransaction.fund.name}
				</td>
				<td>
	 				${customer_finishtransaction.shares}
				</td>
				<td>
	 				${customer_finishtransaction.amount}
				</td>
				<td>
	 				${customer_finishtransaction.type}
				</td>
			</tr>
		</c:forEach>
	<tbody>
</table>


</div>
<div>
	<c:import url="bottom.jsp"/>
</div>

</body>
</html>
