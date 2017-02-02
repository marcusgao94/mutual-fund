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
	<title>Employee transaction history</title>
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

	<h3>Transaction History</h3>
	
	
<h4> Pending </h4>

	<div class="row">
		<div class="col-md-6">
			<table class="table">
	<thead>
		<tr>
					<th>Transaction ID</th>
					<th>Fund Name</th>
					<th>Price</th>
					<th>Quantity of Shares</th>
					<th>Total Amount</th>
          			<th>Operations</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach var="employee_pendingtransaction" items="${employee_pendingTransaction}">
			<tr>
<td>
							${employee_pendingtransaction.id}
					</td>
					<td>
							${employee_pendingtransaction.fund.name}
					</td>
					<td>
	 						${employee_finishtransaction.price}
					</td>
					<td class="shares">
							${employee_pendingtransaction.shares}
					</td>
					<td class="price">
							${employee_pendingtransaction.amount}
					</td>
					<td>
							${employee_pendingtransaction.type}
					</td>
			</tr>
		</c:forEach>
	<tbody>
</table>
</div>
</div>

<h4> Finished </h4>
	<div class="row">
		<div class="col-md-6">
			<table class="table">
	<thead>
		<tr>
					<th>Transition Date</th>
					<th>ID</th>
					<th>Fund Name</th>
					<th>Price</th>
					<th>Quantity of Shares</th>
					<th>Total Amount</th>
          			<th>Operations</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach var="employee_finishtransaction" items="${employee_finishTransaction}">
			<tr>
					<td>
							${employee_finishtransaction.executeDate}
					</td>
					<td>
	 						${employee_finishtransaction.id}
					</td>
					<td>
							${employee_finishtransaction.fund.name}
					</td>
					<td>
	 						${employee_finishtransaction.price}
					</td>
					<td class="shares">
							${employee_finishtransaction.shares}
					</td>
					<td class="price">
							${employee_finishtransaction.amount}
					</td>
					<td>
							${employee_finishtransaction.type}
					</td>
			</tr>
		</c:forEach>
	<tbody>
</table>
</div>
</div>

</div>
<div>
	<c:import url="bottom.jsp"/>
</div>

</body>
</html>
